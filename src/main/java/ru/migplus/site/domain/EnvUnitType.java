package ru.migplus.site.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "env_unit_types")
public class EnvUnitType {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @Column(name = "category", columnDefinition = "varchar(30)")
    private String category;

    /*@Column(name="category", columnDefinition = "varchar(30)")
    @Convert(converter = EnvUnitCategoryConverter.class)
    private EnvUnitCategory category;*/
}
