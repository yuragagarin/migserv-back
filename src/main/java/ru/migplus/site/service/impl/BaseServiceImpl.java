package ru.migplus.site.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.migplus.site.dao.main.repo.UserRepository;
import ru.migplus.site.domain.User;
import ru.migplus.site.exceptions.UserNotFoundException;
import ru.migplus.site.service.BaseService;

import java.util.Optional;

@Slf4j
@Service
public class BaseServiceImpl implements BaseService {

    private final UserRepository userRepo;

    public BaseServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void checkUserExistsById(long id) {

        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()) throw new UserNotFoundException();

    }
}
