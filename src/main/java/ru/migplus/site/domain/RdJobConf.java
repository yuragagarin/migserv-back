package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.migplus.site.domain.converter.RdJobConfStateConverter;
import ru.migplus.site.domain.converter.RdParamGroupConverter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rd_job_confs")
public class RdJobConf extends BaseEntity {
    @Column(name = "interval", nullable = false)
    private short interval;

    @Column(name = "batch_cnt", nullable = false) //максимальное кол-во записей импорта
    private short batchCnt;

    @Column(name = "state", columnDefinition = "varchar(30)")
    @Convert(converter = RdJobConfStateConverter.class)
    private RdJobConfState state;

    @Column(name = "descr", columnDefinition = "varchar(200)")
    private String descr;

    @Column(name = "type", nullable = false, columnDefinition = "varchar(30)")
    @Convert(converter = RdParamGroupConverter.class)
    private RdJobConfType type;

    @Column(name = "changed")
    private boolean changed;

    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "consumer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_consumer"))
    private Consumer consumer;

    @JsonBackReference
    @OneToMany(mappedBy = "rdJobConf")
    private Set<RdParam> rdParams;
}
