package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "equip_servs")
public class EquipServ extends BaseEntityId {

    @Column(name = "date_st", columnDefinition = "timestamp with time zone")
    private LocalDateTime dateSt;

    @Column(name = "date_fn", columnDefinition = "timestamp with time zone")
    private LocalDateTime dateFn;

    @Column(name = "next_date", columnDefinition = "timestamp with time zone")
    private LocalDateTime nextDate;

    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "equip_oper_id", nullable = false, foreignKey = @ForeignKey(name = "fk_equipoper"))
    private EquipOper equipOper;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user"))
    private User user;
}
