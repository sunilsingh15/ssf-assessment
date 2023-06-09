package vttp2023.batch3.ssf.frontcontroller.model;

import jakarta.validation.constraints.Size;

public class User {

    @Size(min = 2, message = "Username must have more than 2 characters!")
    private String username;

    @Size(min = 2, message = "Password must have more than 2 characters!")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
