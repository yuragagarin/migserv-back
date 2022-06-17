package ru.migplus.site.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "work_act_pos_types")
public class WorkActPosType {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(10)")
    private String code;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(50)")
    private String name;

}
