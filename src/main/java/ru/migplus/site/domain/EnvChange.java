package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "env_changes")
@Builder(toBuilder = true)
public class EnvChange extends BaseEntity {

    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "env_unit_id", nullable = false, foreignKey = @ForeignKey(name = "fk_env_unit"))
    private EnvUnit envUnit;

    @Column(name = "place", columnDefinition = "varchar(200)")
    private String place;

    @Column(name = "install_date", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date installDate;

    @Column(name = "expiration_date", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date expirationDate;

    @Column(name = "change_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date changeDate;

    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "consumer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_consumer"))
    private Consumer consumer;
}
