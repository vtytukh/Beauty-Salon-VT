package com.epam.model;

public enum Status {
    PENDING("pending"),
    PAID("paid"),
    ACCEPTED("accepted"),
    FINISHED("finished"),
    CANCELED("canceled");

    private String value;

    public String value() {
        return value;
    }

    Status(String value) {
        this.value = value.intern();
    }
}
