package com.z.medicinedispensary.persistencies;

import com.z.medicinedispensary.models.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Medicine findReceiveMedicineByMedicineNameAndExpirationDate(String medicineName, LocalDate expirationDate);
}
