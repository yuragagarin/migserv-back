package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "work_acts")
public class WorkAct {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num", nullable = false, columnDefinition = "varchar(10)")
    private String num;

    @JsonManagedReference
    @OneToMany(mappedBy = "workAct")
    private List<WorkActOper> workActOpers;

}
