package com.z.medicinedispensary.models;

public final class NewUser {
    public String name;
    public String password;
    public String role;
    public boolean active;

    @Override
    public String toString() {
        return "NewUser{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", active=" + active +
                '}';
    }
}
