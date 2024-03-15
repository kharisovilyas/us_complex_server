package ru.spiiran.us_complex.model.entitys.modelsat.techparam;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name = "ms_list_of_param")
public class msListOfParam implements IEntity {
    @Id
    @Column(name = "prm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prmID;
    @ManyToOne
    @JoinColumn(name = "tech_param_id")
    private msTechParamEntity msTechParamEntity;
    @Column(name = "parameter_1")
    private String parameter1;
    @Column(name = "parameter_2")
    private String parameter2;
    @Column(name = "parameter_3")
    private String parameter3;
    @Column(name = "parameter_4")
    private String parameter4;
    @Column(name = "parameter_5")
    private String parameter5;
    @Column(name = "parameter_6")
    private String parameter6;
    @Column(name = "parameter_7")
    private String parameter7;
    @Column(name = "parameter_8")
    private String parameter8;
    @Column(name = "parameter_9")
    private String parameter9;
    @Column(name = "parameter_10")
    private String parameter10;

    public String getParameter1() {
        return parameter1;
    }

    public String getParameter2() {
        return parameter2;
    }

    public String getParameter3() {
        return parameter3;
    }

    public String getParameter4() {
        return parameter4;
    }

    public String getParameter5() {
        return parameter5;
    }

    public String getParameter6() {
        return parameter6;
    }

    public String getParameter7() {
        return parameter7;
    }

    public String getParameter8() {
        return parameter8;
    }

    public String getParameter9() {
        return parameter9;
    }

    public String getParameter10() {
        return parameter10;
    }

    @Override
    public Long getID() {
        return prmID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
