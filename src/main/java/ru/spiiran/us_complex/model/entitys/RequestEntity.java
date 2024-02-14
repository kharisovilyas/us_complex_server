package ru.spiiran.us_complex.model.entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "request")
public class RequestEntity {

    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(name = "request_info")
    private String requestInfo;

}
