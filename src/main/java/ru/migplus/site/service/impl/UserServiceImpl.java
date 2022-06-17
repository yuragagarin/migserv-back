package ru.migplus.site.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.migplus.site.dao.main.filter.UserSpec;
import ru.migplus.site.dao.main.repo.ConsumerRepository;
import ru.migplus.site.dao.main.repo.RoleRepository;
import ru.migplus.site.dao.main.repo.UserPostRepository;
import ru.migplus.site.dao.main.repo.UserRepository;
import ru.migplus.site.domain.*;
import ru.migplus.site.dto.user.UserDto;
import ru.migplus.site.dto.user.UserViewDto;
import ru.migplus.site.exceptions.ConsumerNotFoundException;
import ru.migplus.site.exceptions.UserNotFoundException;
import ru.migplus.site.exceptions.UserPostNotFoundException;
import ru.migplus.site.service.UserService;
import ru.migplus.site.utils.MyBeanUtils;
import ru.migplus.site.utils.StrUtils;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service

public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final UserPostRepository userPostRepo;
    private final ConsumerRepository consumerRepo;
    private final RoleRepository roleRepo;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository repo, UserPostRepository userPostRepo, ConsumerRepository consumerRepo, RoleRepository roleRepo, ModelMapper mapper) {

        this.repo = repo;
        this.userPostRepo = userPostRepo;
        this.consumerRepo = consumerRepo;
        this.roleRepo = roleRepo;
        this.mapper = mapper;

    }

    @Override
    public ResponseEntity<List<UserViewDto>> getAll() {
        return new ResponseEntity<>(repo.getAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserDto>> getAll(UserSpec filter) {

        List<User> items = repo.findAll(filter);

        List<UserDto> dtos = items.stream().map(new Function<User, UserDto>() {
            @Override
            public UserDto apply(User u) {
                return new UserDto(u.getId()
                        , StrUtils.generateFullName(StrUtils.notEmptyOrNull(u.getSurname()) ? u.getSurname() : null
                        , StrUtils.notEmptyOrNull(u.getName()) ? u.getName() : null
                        , StrUtils.notEmptyOrNull(u.getPatronymic()) ? u.getPatronymic() : null), null
                        , null, null, null
                        , null, null, null, null, null, null);
            }
        }).collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    @Override
    public ResponseEntity create(UserDto dto) {

        String[] fio = dto.getFio().split(" ");
        Optional<User> userExists = repo.findFirstByUsernameAndStatus(dto.getUsername(), Status.ACTIVE);
        if (userExists.isPresent())
            return new ResponseEntity(HttpStatus.CONFLICT);

        UserPost userPost = userPostRepo.getOne(dto.getUserPostId());
        if (userPost == null) throw new UserPostNotFoundException();

        Consumer consumer = null;
        if (dto.getConsumerId() != null) {
            consumer = consumerRepo.getOne(dto.getConsumerId());
            if (consumer == null) throw new ConsumerNotFoundException();
        }

        List<Role> roles = roleRepo.getAllById(dto.getRoleId());

        User master = repo.getOne(dto.getUserId());

        User item = new User();
        item.setSurname(fio[0]);
        item.setName(fio[1]);
        item.setPatronymic(fio[2]);
        item.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        item.setUsername(dto.getUsername());
        item.setPost(userPost);
        item.setPhoneNumber(dto.getPhoneNumber());
        item.setEmail(dto.getEmail());
        item.setConsumer(consumer);
        item.setUpdated(ZonedDateTime.now());
        item.setUser(master);
        item.setRoles(roles);

        repo.save(item);

        return new ResponseEntity(HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<UserDto> get(long id) {

        Optional<User> item = repo.findFirstByIdAndStatus(id, Status.ACTIVE);
        if (!item.isPresent())
            throw new UserNotFoundException();
        User user = item.get();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFio(user.getSurname() + " " + item.get().getName() + " " + item.get().getPatronymic());
        userDto.setUsername(user.getUsername());
        userDto.setUserPostId(((user.getPost() == null) ? null : user.getPost().getId()));
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setConsumerId(((user.getConsumer() == null) ? null : user.getConsumer().getId()));
        userDto.setUpdated(user.getUpdated());
        userDto.setUserId(((user.getUser() == null) ? null : user.getUser().getId()));
        userDto.setRoleId((user.getRoles() == null && user.getRoles().isEmpty()) ? null : user.getRoles().get(0).getId());

        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    @Override
    public ResponseEntity update(UserDto dto) {

        String[] fio = dto.getFio().split(" ");

        Optional<User> item = repo.findFirstByIdAndStatus(dto.getId(), Status.ACTIVE);
        if (!item.isPresent())
            throw new UserNotFoundException();

        UserPost userPost = null;
        if (dto.getUserPostId() != null) {
            userPost = userPostRepo.getOne(dto.getUserPostId());
            if (userPost == null) throw new UserPostNotFoundException();
        }

        Consumer consumer = null;
        if (dto.getConsumerId() != null) {
            consumer = consumerRepo.getOne(dto.getConsumerId());
            if (consumer == null) throw new ConsumerNotFoundException();
        }

        List<Role> roles = roleRepo.getAllById(dto.getRoleId());

        User master = repo.getOne(dto.getUserId());

        MyBeanUtils.copyNonNullProperties(dto, item.get());

        roleRepo.deleteRolesByUserId(dto.getChangedUserId());

        User updatedItem = item.get();
        updatedItem.setSurname(fio[0]);
        updatedItem.setName(fio[1]);
        updatedItem.setPatronymic(fio[2]);
        updatedItem.setUsername(dto.getUsername());
        updatedItem.setPost(userPost);
        if (dto.getPassword() != null)
            updatedItem.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        updatedItem.setConsumer(consumer);
        updatedItem.setUpdated(ZonedDateTime.now());
        updatedItem.setUser(master);
        updatedItem.setRoles(roles);
        updatedItem = repo.saveAndFlush(updatedItem);

        return new ResponseEntity(updatedItem, HttpStatus.OK);

    }

    @Override
    public ResponseEntity delete(UserDto dto) {

        User item = repo.getOne(dto.getId());
        if (item == null) throw new UserNotFoundException();
        item.setStatus(Status.DELETED);
        repo.save(item);

        return new ResponseEntity(HttpStatus.OK);

    }


}
