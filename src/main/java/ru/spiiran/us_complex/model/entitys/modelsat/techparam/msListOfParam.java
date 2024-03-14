package ru.spiiran.us_complex.model.entitys.modelsat.techparam;

import jakarta.persistence.*;

@Embeddable
public class msListOfParam {
    @Column(name = "parameter_1")
    private final String parameter1 = "Ускорение / замедление КА";
    @Column(name = "parameter_2")
    private final String parameter2 = "Максимальная скорость вращения КА";
    @Column(name = "parameter_3")
    private final String parameter3 = "Время стабилизации";
    @Column(name = "parameter_4")
    private final String parameter4 = "Скорость передачи данных КА - КА";
    @Column(name = "parameter_5")
    private final String parameter5 = "Скорость передачи данных КА - НП";
    @Column(name = "parameter_6")
    private final String parameter6 = "Крен";
    @Column(name = "parameter_7")
    private final String parameter7 = "Тангаж";
    @Column(name = "parameter_8")
    private final String parameter8 = "Емкость";
    @Column(name = "parameter_9")
    private final String parameter9 = "Минимальный уровень заряда";
    @Column(name = "parameter_10")
    private final String parameter10 = "Объем памяти";

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
}
