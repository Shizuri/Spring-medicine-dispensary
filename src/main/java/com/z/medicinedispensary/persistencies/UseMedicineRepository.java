package com.z.medicinedispensary.persistencies;

import com.z.medicinedispensary.models.UseMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UseMedicineRepository extends JpaRepository<UseMedicine, Long> {

    UseMedicine findFirstByMedicineNameAndExpirationDateAndPatientNameAndDateOfAdministration(
            String medicineName,
            LocalDate expirationDate,
            String patientName,
            LocalDate dateOfAdministration);

}
