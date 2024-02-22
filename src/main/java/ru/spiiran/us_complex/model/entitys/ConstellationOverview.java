package ru.spiiran.us_complex.model.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "constellation_detailed")
public class ConstellationOverview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "table_id")
    @JsonIgnore
    private ConstellationEntity constellation;

}
