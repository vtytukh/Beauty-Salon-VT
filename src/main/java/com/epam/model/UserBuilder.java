package com.epam.model;

public class UserBuilder {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

    public UserBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setUserType(Role role) {
        this.role = role;
        return this;
    }


    public User build() {
        return new User(firstName, lastName, email, password, role, id);
    }
}
