package ru.spiiran.us_complex.model.dto.modelling.response.pro42;

import com.google.gson.Gson;
import ru.spiiran.us_complex.model.dto.IDTOEntity;

public class dtoAssessment implements IDTOEntity {

    private String goalLabel;
    private String scLabel;
    private Long begin;
    private Long end;

    public dtoAssessment() {}

    public dtoAssessment(String jsonResponse){
        Gson gson = new Gson();
        dtoAssessment response = gson.fromJson(jsonResponse, dtoAssessment.class);
        this.begin = response.getBegin();
        this.end = response.getEnd();
        this.scLabel = response.getScLabel();
        this.goalLabel = response.getGoalLabel();
    }

    public String getGoalLabel() {
        return goalLabel;
    }

    public void setGoalLabel(String goalLabel) {
        this.goalLabel = goalLabel;
    }

    public String getScLabel() {
        return scLabel;
    }

    public void setScLabel(String scLabel) {
        this.scLabel = scLabel;
    }

    public Long getBegin() {
        return begin;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}
