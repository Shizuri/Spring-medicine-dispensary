package com.z.medicinedispensary.persistencies;

import com.z.medicinedispensary.models.ReceiveMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReceiveMedicineRepository extends JpaRepository<ReceiveMedicine, Long> {

    ReceiveMedicine findReceiveMedicineByMedicineNameAndExpirationDate(String medicineName, LocalDate expirationDate);
}
