package ru.migplus.site.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "menu_items")
@Data

@RequiredArgsConstructor
public class MenuItem {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_menu_item"))
    private MenuItem menuItem;

    @Column(name = "level", nullable = false)
    private short level;

    @Column(name = "path", length = 200)
    private String path;

    @Column(name = "icon_name", length = 50)
    private String iconName;

    @Column(name = "ord")
    private int ord;

    @OneToOne
    @JoinColumn(name = "permission_id", foreignKey = @ForeignKey(name = "fk_permission"))
    private Permission permission;
}
