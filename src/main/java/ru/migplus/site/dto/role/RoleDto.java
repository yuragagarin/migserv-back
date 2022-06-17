package ru.migplus.site.dto.role;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {
    int id;
    String name;
    List<Integer> permissionsId;
}
