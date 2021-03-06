package com.z.medicinedispensary.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "medicine")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "quantity")
    @NotNull
    @Range(min = 0L, max = Long.MAX_VALUE)
    private long quantity;

    @Column(name = "medicine_name")
    @NotNull
    @Length(min = 1)
    private String medicineName;

    @Column(name = "expiration_date")
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate expirationDate;

    public Medicine() {
    }

    public Medicine(@NotNull @Range(min = 0L, max = Long.MAX_VALUE) long quantity, @NotNull String medicineName, @NotNull LocalDate expirationDate) {
        this.quantity = quantity;
        this.medicineName = medicineName;
        this.expirationDate = expirationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", medicineName='" + medicineName + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
