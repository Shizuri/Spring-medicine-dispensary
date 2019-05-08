package com.z.medicinedispensary.models;

public final class NewMedicine {
    public long quantity;
    public String medicineName;
    public String expirationDate;

    @Override
    public String toString() {
        return "NewMedicine{" +
                "quantity=" + quantity +
                ", medicineName='" + medicineName + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }
}
