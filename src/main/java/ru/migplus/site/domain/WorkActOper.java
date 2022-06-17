package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "work_act_opers")
public class WorkActOper {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(20)")
    private String name;

    @JsonBackReference
    @ManyToOne(optional = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "work_act_id", nullable = false, foreignKey = @ForeignKey(name = "fk_work_act"))
    private WorkAct workAct;

    @Column(name = "date_st", nullable = false, columnDefinition = "timestamp with time zone")
    private LocalDateTime dateSt;

    @Column(name = "date_fn", columnDefinition = "timestamp with time zone")
    private LocalDateTime dateFn;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

}
