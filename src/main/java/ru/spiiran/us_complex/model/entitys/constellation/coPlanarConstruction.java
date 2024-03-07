package ru.spiiran.us_complex.model.entitys.constellation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "co_planar_construction")
public class coPlanarConstruction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "table_id")
    @JsonIgnore
    private ConstellationEntity constellation;

}
