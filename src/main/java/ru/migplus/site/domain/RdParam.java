package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rd_params")
public class RdParam extends BaseEntity {
    @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @Column(name = "trans_name", nullable = false, columnDefinition = "varchar(100)")
    private String transName;

    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "param_type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_param_type"))
    private ParamType paramType;

    @Column(name = "path_src", nullable = false, columnDefinition = "varchar(250)")
    private String pathSrc;

    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "rd_job_conf_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rd_job_conf"))
    private RdJobConf rdJobConf;
}
