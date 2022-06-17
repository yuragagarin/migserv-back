package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "env_units")
public class EnvUnit implements Serializable {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", columnDefinition = "varchar(30)")
    private String code;

    @Column(name = "number", columnDefinition = "varchar(50)")
    private String number;

    @Column(name = "passport", columnDefinition = "varchar(50)")
    private String passport;

    @Column(name = "serial_num", columnDefinition = "varchar(20)")
    private String serialNum;

    @Column(name = "work_time")
    private Short workTime;

    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "env_unit_type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_envunittype"))
    private EnvUnitType envUnitType;

    @OneToOne
    @JoinColumn(name = "equip_id", foreignKey = @ForeignKey(name = "fk_equip"))
    private Equip equip;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "envUnit")
    private Set<EnvUnitOper> envUnitOpers;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "consumer_id", foreignKey = @ForeignKey(name = "fk_consumer"))
    private Consumer consumer;
}
