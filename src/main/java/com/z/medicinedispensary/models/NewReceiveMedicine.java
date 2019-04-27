package com.z.medicinedispensary.models;

public final class NewReceiveMedicine {
    public long quantity;
    public String medicineName;
    public String expirationDate;

    @Override
    public String toString() {
        return "NewReceiveMedicine{" +
                "quantity=" + quantity +
                ", medicineName='" + medicineName + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }
}
