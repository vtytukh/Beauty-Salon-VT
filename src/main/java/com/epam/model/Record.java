package com.epam.model;

public class Record {
    private Long id;
    private Long user_id;
    private Long master_has_service_id;
    private Long status_id;
    private String time;

    private User user;
    private User userMaster;
    private Master master;
    private Service service;
    private Status status;
    private ServiceMaster serviceMaster;
    private int hour;

    public Record(){

    }
    public Record(int hour){
        this.hour = hour;
    }
    public Record(Long id, Long user_id, Long master_has_service_id, Long status_id, String time) {
        this.id = id;
        this.user_id = user_id;
        this.master_has_service_id = master_has_service_id;
        this.status_id = status_id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getMaster_has_service_id() {
        return master_has_service_id;
    }

    public void setMaster_has_service_id(Long master_has_service_id) {
        this.master_has_service_id = master_has_service_id;
    }

    public Long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Long status_id) {
        this.status_id = status_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserMaster() {
        return userMaster;
    }

    public void setUserMaster(User userMaster) {
        this.userMaster = userMaster;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ServiceMaster getServiceMaster() {
        return serviceMaster;
    }

    public void setServiceMaster(ServiceMaster serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHour(){
        if(this.time != null)
            return Integer.parseInt(this.time.substring(11,13));
        else return hour;
    }
    public void setHour(int hour) { this.hour = hour;}
}
