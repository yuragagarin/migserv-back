package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "env_unit_opers")
public class EnvUnitOper {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@Column(name="name", columnDefinition = "varchar(30)")
    @Convert(converter = EnvUnitOperNameConverter.class)
    private EnvUnitOperName_old name;*/

    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "name_id", nullable = false, foreignKey = @ForeignKey(name = "fk_env_unit_oper_names"))
    private EnvUnitOperName name;

    @Column(name = "date_st", nullable = false, columnDefinition = "timestamp with time zone")
    private LocalDateTime dateSt;

    @Column(name = "date_fn", columnDefinition = "timestamp with time zone")
    private LocalDateTime dateFn;

    @Column(name = "expir_date", columnDefinition = "timestamp with time zone")
    private LocalDateTime expirDate;

    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "env_unit_id", nullable = false, foreignKey = @ForeignKey(name = "fk_envunit"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private EnvUnit envUnit;

    @ManyToOne
    @JoinColumn(name = "ref_env_unit_oper_id", foreignKey = @ForeignKey(name = "fk_env_unit_opers"))
    private EnvUnitOper refEnvUnitOper;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
