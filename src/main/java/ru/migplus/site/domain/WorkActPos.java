package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "work_act_poss")
public class WorkActPos extends BaseEntityId {

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "work_act_id", nullable = false, foreignKey = @ForeignKey(name = "fk_work_act"))
    private WorkAct workAct;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_work_act_pos_type"))
    private WorkActPosType type;

    @Column(name = "unit_id", nullable = false, columnDefinition = "bigint")
    private Long unitId;

    @Column(name = "oper_id", nullable = false, columnDefinition = "bigint")
    private Long operId;

}
