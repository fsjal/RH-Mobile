package com.project.rhmobile.entities;

public class User {

    private String name;
    private String prename;
    private String email;
    private String password;
    private String phone;

    public User(String name, String prename, String email, String password, String phone) {
        this.name = name;
        this.prename = prename;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User(String name, String prename, String email, String password) {
        this.name = name;
        this.prename = prename;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
