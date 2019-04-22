package com.z.medicinedispensary.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "use_medicine")
public class UseMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private long id;

    @Column(name = "medicine_name")
    @NotNull
    private String medicineName;

    @Column(name = "expiration_date")
    @NotNull
    private LocalDate expirationDate;

    @Column(name = "patient_name")
    @NotNull
    private String patientName;

    public UseMedicine() {
    }

    public UseMedicine(@NotNull String medicineName, @NotNull LocalDate expirationDate, @NotNull String patientName) {
        this.medicineName = medicineName;
        this.expirationDate = expirationDate;
        this.patientName = patientName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Override
    public String toString() {
        return "UseMedicine{" +
                "id=" + id +
                ", medicineName='" + medicineName + '\'' +
                ", expirationDate=" + expirationDate +
                ", patientName='" + patientName + '\'' +
                '}';
    }
}
