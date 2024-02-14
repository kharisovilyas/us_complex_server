package ru.spiiran.us_complex.model.entitys.general;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.EarthPointEntity;

@Entity
@Table(name = "status_general")
public class generalStatusEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "status_id")
    private Long statusId;
    @Column(name = "status_edit")
    private Boolean statusOfEdit;
    @OneToOne(mappedBy = "generalStatusEntity", cascade = CascadeType.ALL)
    private EarthPointEntity earthPointEntity;

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Boolean getStatusOfEdit() {
        return statusOfEdit;
    }

    public void setStatusOfEdit(Boolean statusOfEdit) {
        this.statusOfEdit = statusOfEdit;
    }

    @Override
    public long getID() {
        return 0;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
