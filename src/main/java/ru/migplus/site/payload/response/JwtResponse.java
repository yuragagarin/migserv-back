package ru.migplus.site.payload.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.migplus.site.dto.menu.MenuItemDto;

import java.util.List;

@Data
public class JwtResponse {
    @Getter
    private String tokenType = "Bearer";

    private String accessToken;

    private Long id;

    private String username;

    private String email;

    private String name;

    private String surname;

    private String patronymic;

    private String post;

    @Getter
    private List<String> roles;

    @Getter
    private List<String> permissions;

    @Getter
    private List<MenuItemDto> menu;

    private String version;

    @Builder
    public JwtResponse(String accessToken, Long id, String username, String email, String name, String surname, String patronymic, String post, List<String> roles, List<String> permissions, List<MenuItemDto> menu, String version) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.post = post;
        this.roles = roles;
        this.permissions = permissions;
        this.menu = menu;
        this.version = version;
    }
}
