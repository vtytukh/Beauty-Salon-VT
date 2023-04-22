package com.epam.model;

public class Service {
    private Long id;
    private String name;
    private String description;

    public Service(){
    }
    public Service(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Service(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
