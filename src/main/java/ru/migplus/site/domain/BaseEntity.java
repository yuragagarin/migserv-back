package ru.migplus.site.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "updated", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @UpdateTimestamp
    private Timestamp updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(10) default 'ACTIVE'")
    private Status status = Status.ACTIVE;

    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "update_user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user"))
    private User user;
}
