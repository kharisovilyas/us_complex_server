package ru.spiiran.us_complex.model.dto.modelling.response.smao;

import com.google.gson.Gson;
import ru.spiiran.us_complex.model.dto.modelling.dto_smao.Order;

import java.util.List;

public class dtoSmaoOrder implements IDTOSMAOResponse{
    private Long time;
    private String type;
    private Integer idReceiver;
    private List<Order> order;

    // Constructor from JSON
    public dtoSmaoOrder(String jsonResponse) {
        Gson gson = new Gson();
        dtoSmaoOrder response = gson.fromJson(jsonResponse, dtoSmaoOrder.class);
        this.time = response.getTime();
        this.type = response.getType();
        this.idReceiver = response.getIdReceiver();
        this.order = response.getOrder();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(Integer idReceiver) {
        this.idReceiver = idReceiver;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }
}
