package com.epam.model;

import java.math.BigDecimal;

public class ServiceMaster {
    private Long id;
    private Long master_id;
    private Long service_id;
    private BigDecimal price;

    private Master master;
    private Service service;
    public ServiceMaster(){

    }
    public ServiceMaster(Long id, Long master_id, Long service_id, BigDecimal price) {
        this.id = id;
        this.master_id = master_id;
        this.service_id = service_id;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaster_id() {
        return master_id;
    }

    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
    }

    public Long getService_id() {
        return service_id;
    }

    public void setService_id(Long service_id) {
        this.service_id = service_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Master getMaster() {
        return master;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setMaster(Master master) {
        this.master = master;
    }
}
