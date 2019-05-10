package com.z.medicinedispensary.persistencies;

import com.z.medicinedispensary.models.UseMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface UseMedicineRepository extends JpaRepository<UseMedicine, Long> {

//    UseMedicine findUseMedicineByMedicineNameAndExpirationDate(String medicineName, LocalDate expirationDate);
    UseMedicine findUseMedicineByMedicineNameAndExpirationDateAndPatientNameAndDateOfAdministration(
            String medicineName,
            LocalDate expirationDate,
            String patientName,
            LocalDate dateOfAdministration);


    UseMedicine findFirstByMedicineNameAndExpirationDateAndPatientNameAndDateOfAdministration(
            String medicineName,
            LocalDate expirationDate,
            String patientName,
            LocalDate dateOfAdministration);

//    @Query()
//    UseMedicine deleteFirstUseMedicine();
}
