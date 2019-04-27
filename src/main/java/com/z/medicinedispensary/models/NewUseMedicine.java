package com.z.medicinedispensary.models;

public final class NewUseMedicine {
    public String medicineName;
    public String expirationDate;
    public String patientName;
    public String dateOfAdministration;

    @Override
    public String toString() {
        return "NewUseMedicine{" +
                "medicineName='" + medicineName + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", patientName='" + patientName + '\'' +
                ", dateOfAdministration='" + dateOfAdministration + '\'' +
                '}';
    }
}
