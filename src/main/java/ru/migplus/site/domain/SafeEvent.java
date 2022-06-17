package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.migplus.site.domain.converter.SafeEventTypeConverter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "safe_events")
public class SafeEvent extends BaseEntityId {

    @Column(name = "name", nullable = false, columnDefinition = "varchar(10)")
    @Convert(converter = SafeEventTypeConverter.class)
    private SafeEventType name;

    @Column(name = "st_date", nullable = false, columnDefinition = "timestamp with time zone")
    private ZonedDateTime stDate;

    @Column(name = "fn_date", columnDefinition = "timestamp with time zone")
    private ZonedDateTime fnDate;

    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "rd_param_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rd_param"))
    private RdParam rdParam;

    @Builder
    public SafeEvent(SafeEventType name, ZonedDateTime stDate, ZonedDateTime fnDate, RdParam rdParam, User user, String comment) {
        this.name = name;
        this.stDate = stDate;
        this.fnDate = fnDate;
        this.rdParam = rdParam;
    }
}
