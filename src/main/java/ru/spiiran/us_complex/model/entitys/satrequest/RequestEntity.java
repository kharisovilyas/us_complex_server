package ru.spiiran.us_complex.model.entitys.satrequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.satrequest.dtoRequest;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "sr_request")
public class RequestEntity implements IEntity {
    @Id
    @Column(name = "id_request")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "id_goal")
    @JsonIgnore
    private CatalogEntity catalogEntity;

    @Column(name = "id_order")
    private Long orderId;

    @Column(name = "priory")
    private Long priory;

    @Column(name = "term")
    private Long term;

    @Column(name = "time")
    private Long time;

    @ManyToOne
    @JoinColumn(name = "id_node")
    @JsonIgnore
    private generalIdNodeEntity generalIdNodeEntity;

    @Column(name = "filter")
    private Boolean filter;

    public RequestEntity(dtoRequest request) {
        this.filter = request.getFilter();
        this.term = request.getTerm();
        this.requestId = request.getRequestId();
        this.time = request.getTime();
        this.orderId = request.getOrderId();
        this.priory = request.getPriory();
    }

    public RequestEntity() {
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public CatalogEntity getCatalogEntity() {
        return catalogEntity;
    }

    public void setCatalogEntity(CatalogEntity catalogEntity) {
        this.catalogEntity = catalogEntity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPriory() {
        return priory;
    }

    public void setPriory(Long priory) {
        this.priory = priory;
    }


    public Long getTerm() {
        return term;
    }

    public void setTerm(Long term) {
        this.term = term;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity getGeneralIdNodeEntity() {
        return generalIdNodeEntity;
    }

    public void setGeneralIdNodeEntity(ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity generalIdNodeEntity) {
        this.generalIdNodeEntity = generalIdNodeEntity;
    }

    public Boolean getFilter() {
        return filter;
    }

    public void setFilter(Boolean filter) {
        this.filter = filter;
    }

    @Override
    public Long getID() {
        return requestId;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
