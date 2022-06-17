package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.migplus.site.domain.converter.PermissionTypeConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "permissions")
public class Permission {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "code", nullable = false, columnDefinition = "varchar(200)")
    private String code;

    @NonNull
    @Column(name = "name", nullable = false, columnDefinition = "varchar(1000)")
    private String name;

    @Column(name = "type", columnDefinition = "varchar(30)")
    @Convert(converter = PermissionTypeConverter.class)
    private PermissionType type;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();
}
