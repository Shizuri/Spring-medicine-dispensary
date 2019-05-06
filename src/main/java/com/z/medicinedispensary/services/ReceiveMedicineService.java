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
        // find the medicine in the database
        ReceiveMedicine medicine = receiveMedicineRepository.findReceiveMedicineByMedicineNameAndExpirationDate
                (newReceiveMedicine.medicineName, LocalDate.parse(newReceiveMedicine.expirationDate));
        // if you do find it, increase its quantity
        if(medicine != null){
            logger.info("Found duplicate [{}], will not add new, increasing quantity.", medicine);
            receiveMedicineRepository.findById(medicine.getReceiveId()).map(x -> {
                x.setQuantity(x.getQuantity() + newReceiveMedicine.quantity);
                return x;
            });
            return medicine;
        }
        //if you don't find it, add it to the database
        logger.info("Saving to database: [{}]", newReceiveMedicine);
        return receiveMedicineRepository
                .save(new ReceiveMedicine(newReceiveMedicine.quantity, newReceiveMedicine.medicineName,
                        LocalDate.parse(newReceiveMedicine.expirationDate)));
    }


    public List<ReceiveMedicine> getAllMedicine() {
        return receiveMedicineRepository.findAll();
    }

}
