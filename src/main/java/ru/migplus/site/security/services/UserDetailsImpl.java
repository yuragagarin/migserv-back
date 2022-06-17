package ru.migplus.site.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.migplus.site.domain.Role;
import ru.migplus.site.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    private String name;

    private String surname;

    private String patronymic;

    private String post;

    @JsonIgnore
    private String password;

    private List<String> roles;

    private Long consumerId;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String email, String password, String name, String surname, String patronymic, String post, List<String> roles,
                           Collection<? extends GrantedAuthority> authorities, Long consumerId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.post = post;
        this.roles = roles;
        this.authorities = authorities;
        this.consumerId = consumerId;
    }

    public static UserDetailsImpl build(User user, List<Role> roles, List<String> permissions) {

        List<String> sroles = roles.stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());

        List<GrantedAuthority> authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getSurname(),
                user.getPatronymic(),
                user.getPost().getName(),
                sroles,
                authorities,
                Optional.ofNullable(user.getConsumer()).map(e -> e.getId()).orElse(null));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public List<String> getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    public String getName() {
        return name;
    }

    public String getSurName() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPost() {
        return post;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
