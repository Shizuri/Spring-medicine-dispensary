package com.z.medicinedispensary.services;

import com.z.medicinedispensary.models.NewReceiveMedicine;
import com.z.medicinedispensary.models.ReceiveMedicine;
import com.z.medicinedispensary.persistencies.ReceiveMedicineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class ReceiveMedicineService {

    private final ReceiveMedicineRepository receiveMedicineRepository;

    static final private Logger logger = LoggerFactory.getLogger(ReceiveMedicineService.class);

    public ReceiveMedicineService(ReceiveMedicineRepository receiveMedicineRepository) {
        this.receiveMedicineRepository = receiveMedicineRepository;
    }

    @Transactional
    public ReceiveMedicine receiveMedicine(NewReceiveMedicine newReceiveMedicine) {
        List<ReceiveMedicine> allMedicine = getAllMedicine();
        for (ReceiveMedicine medicine : allMedicine) {
            if (medicine.getMedicineName().equals(newReceiveMedicine.medicineName) && LocalDate.parse(newReceiveMedicine.expirationDate).equals(medicine.getExpirationDate())) {
                logger.info("Found duplicate [{}], will not add new, increasing quantity.", medicine);
                receiveMedicineRepository.findById(medicine.getReceiveId()).map(x -> {
                    x.setQuantity(x.getQuantity() + newReceiveMedicine.quantity);
                    return x;
                });
                return medicine;
            }
        }
        return receiveMedicineRepository.save(new ReceiveMedicine(newReceiveMedicine.quantity, newReceiveMedicine.medicineName, LocalDate.parse(newReceiveMedicine.expirationDate)));
    }


    public List<ReceiveMedicine> getAllMedicine() {
        return receiveMedicineRepository.findAll();
    }
}
