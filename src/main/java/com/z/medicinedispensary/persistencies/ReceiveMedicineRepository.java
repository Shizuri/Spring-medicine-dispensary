package com.z.medicinedispensary.persistencies;

import com.z.medicinedispensary.models.ReceiveMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiveMedicineRepository extends JpaRepository<ReceiveMedicine, Long> {
}
