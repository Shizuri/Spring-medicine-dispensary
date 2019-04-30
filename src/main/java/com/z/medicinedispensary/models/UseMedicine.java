package com.z.medicinedispensary.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "use_medicine")
public class UseMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "use_id")
    private long useId;

    @Column(name = "medicine_name")
    @NotNull
    @Length(min = 1)
    private String medicineName;

    @Column(name = "expiration_date")
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate expirationDate;

    @Column(name = "patient_name")
    @NotNull
    @Length(min = 1)
    private String patientName;

    @Column(name = "date_of_administration")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfAdministration;

    public UseMedicine() {
    }

    public UseMedicine(@NotNull String medicineName, @NotNull LocalDate expirationDate, @NotNull String patientName, LocalDate dateOfAdministration) {
        this.medicineName = medicineName;
        this.expirationDate = expirationDate;
        this.patientName = patientName;
        this.dateOfAdministration = dateOfAdministration;
    }

    public UseMedicine(@NotNull String medicineName, @NotNull LocalDate expirationDate, @NotNull String patientName) {
        this.medicineName = medicineName;
        this.expirationDate = expirationDate;
        this.patientName = patientName;
    }

    public long getUseId() {
        return useId;
    }

    public void setUseId(long useId) {
        this.useId = useId;
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

    public LocalDate getDateOfAdministration() {
        return dateOfAdministration;
    }

    public void setDateOfAdministration(LocalDate dateOfAdministration) {
        this.dateOfAdministration = dateOfAdministration;
    }

    @Override
    public String toString() {
        return "UseMedicine{" +
                "useId=" + useId +
                ", medicineName='" + medicineName + '\'' +
                ", expirationDate=" + expirationDate +
                ", patientName='" + patientName + '\'' +
                ", dateOfAdministration=" + dateOfAdministration +
                '}';
    }
}
