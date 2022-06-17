package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "equip_opers")
public class EquipOper extends BaseEntityId {

    @Column(name = "name", nullable = false, columnDefinition = "varchar(20)")
    private String name;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "equip_id", nullable = false, foreignKey = @ForeignKey(name = "fk_equip"))
    private Equip equip;

    @Builder
    public EquipOper(String name, Equip equip) {
        this.name = name;
        this.equip = equip;
    }
}
