package com.z.medicinedispensary.models;

public final class UpdateUser {
    public String name;
    public String role;
    public boolean active;

    @Override
    public String toString() {
        return "UpdateUser{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", active=" + active +
                '}';
    }
}
