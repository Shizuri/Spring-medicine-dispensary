package com.z.medicinedispensary.persistencies;

import com.z.medicinedispensary.models.UseMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UseMedicineRepository extends JpaRepository<UseMedicine, Long> {
}
