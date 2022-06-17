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
@Table(name = "equips")
public class Equip {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", columnDefinition = "varchar(30)")
    private String code;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @Column(name = "number", columnDefinition = "varchar(50)")
    private String number;

    @Column(name = "year", columnDefinition = "varchar(4)")
    private String year;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(10) default 'ACTIVE'")
    private Status status = Status.ACTIVE;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "consumer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_consumer"))
    private Consumer consumer;

}
