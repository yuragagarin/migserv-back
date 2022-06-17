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
@Table(name = "consumers")
public class Consumer extends BaseEntity {

    @Column(name = "name", nullable = false, columnDefinition = "varchar(200)")
    private String name;

    @Column(name = "address", columnDefinition = "varchar(250)")
    private String address;

    @Column(name = "designated", columnDefinition = "varchar(100)")
    private String designated;

    @Column(name = "designated_phone", columnDefinition = "varchar(20)")
    private String designatedPhone;

    @Column(name = "rd_dst_name", nullable = false, columnDefinition = "varchar(200)")
    private String rdDstName;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "serviceman_id", foreignKey = @ForeignKey(name = "fk_serviceman"))
    private User serviceman;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "serviceman_gas_id", foreignKey = @ForeignKey(name = "fk_serviceman_gas"))
    private User servicemanGas;
}
