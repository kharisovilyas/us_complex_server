package ru.spiiran.us_complex.model.dto.modelling.dto_pro42;

public class ViewWindow {
    private String goalLabel;
    private String scLabel;
    private long begin;
    private long end;

    public ViewWindow() {}

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

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
