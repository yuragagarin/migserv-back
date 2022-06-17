package ru.migplus.site.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.migplus.site.dao.main.repo.UserPostRepository;
import ru.migplus.site.domain.UserPost;
import ru.migplus.site.dto.user.UserPostDto;
import ru.migplus.site.service.UserPostService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserPostServiceImpl implements UserPostService {

    private final UserPostRepository repo;
    private final ModelMapper mapper;

    @Autowired
    public UserPostServiceImpl(UserPostRepository repo, ModelMapper mapper) {

        this.repo = repo;
        this.mapper = mapper;

    }

    @Override
    public ResponseEntity<List<UserPostDto>> getAll() {

        List<UserPost> items = repo.findAll();
        List<UserPostDto> dtos = items
                .stream()
                .map(item -> mapper.map(item, UserPostDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }
}
