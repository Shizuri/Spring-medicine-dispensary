package com.z.medicinedispensary.models;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "receive_medicine")
public class ReceiveMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private long id;

    @Column(name = "quantity")
    @NotNull
    @Range(min = 0L, max = Long.MAX_VALUE)
    private long quantity;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "expiration_date")
    @NotNull
    private LocalDate expirationDate;

    public ReceiveMedicine() {
    }

    public ReceiveMedicine(@NotNull @Range(min = 0L, max = Long.MAX_VALUE) long quantity, @NotNull String name, @NotNull LocalDate expirationDate) {
        this.quantity = quantity;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "ReceiveMedicine{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
