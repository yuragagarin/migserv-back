package ru.migplus.site.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.migplus.site.dao.main.repo.PermissionRepository;
import ru.migplus.site.dao.main.repo.RoleRepository;
import ru.migplus.site.dao.main.repo.UserRepository;
import ru.migplus.site.domain.Role;
import ru.migplus.site.domain.User;

import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    PermissionRepository permissionRepo;

    @Autowired
    RoleRepository rolesRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        List<Role> roles = user.getRoles();
        List<String> permissions = permissionRepo.getByUserId(user.getId());

        return UserDetailsImpl.build(user, roles, permissions);
    }
}
