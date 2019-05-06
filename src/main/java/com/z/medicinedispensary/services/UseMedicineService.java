package com.z.medicinedispensary.services;

import com.z.medicinedispensary.models.NewUseMedicine;
import com.z.medicinedispensary.models.ReceiveMedicine;
import com.z.medicinedispensary.models.UseMedicine;
import com.z.medicinedispensary.persistencies.ReceiveMedicineRepository;
import com.z.medicinedispensary.persistencies.UseMedicineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UseMedicineService {

    private final ReceiveMedicineRepository receiveMedicineRepository;
    private final UseMedicineRepository useMedicineRepository;

    static final private Logger logger = LoggerFactory.getLogger(UseMedicineService.class);


    public UseMedicineService(ReceiveMedicineRepository receiveMedicineRepository, UseMedicineRepository useMedicineRepository) {
        this.receiveMedicineRepository = receiveMedicineRepository;
        this.useMedicineRepository = useMedicineRepository;
    }

    @Transactional
    public UseMedicine useMedicine(NewUseMedicine newUseMedicine) throws Exception {
        // find medicine in database
        ReceiveMedicine medicine = receiveMedicineRepository
                .findReceiveMedicineByMedicineNameAndExpirationDate(newUseMedicine.medicineName,
                        LocalDate.parse(newUseMedicine.expirationDate));
        // if there is no such medicine return null
        if (medicine == null) {
            logger.warn("No such medicine found");
            throw new Exception("No such medicine found!");
        }
        // check if the medicine has quantity
        if (medicine.getQuantity() > 0) {
            logger.info("Using medicine: [{}]", medicine);
            logger.info("Medicine use: [{}]", newUseMedicine);
            //reduce the quantity in the storage
            receiveMedicineRepository.findById(medicine.getReceiveId()).map(x -> {
                x.setQuantity(x.getQuantity() - 1);
                return x;
            });
            //delete from database if that medicine ran out
            if(medicine.getQuantity() == 0){
                logger.warn("Quantity is [{}] DELETING [{}]", medicine.getQuantity(),medicine);
                receiveMedicineRepository.delete(medicine);
            }
            //save use of medicine with dateOfAdministration or without
            if(!newUseMedicine.dateOfAdministration.isEmpty()){
                return useMedicineRepository
                        .save(new UseMedicine(newUseMedicine.medicineName
                                , LocalDate.parse(newUseMedicine
                                .expirationDate), newUseMedicine.patientName
                                ,LocalDate.parse(newUseMedicine.dateOfAdministration)));
            } else {
                return useMedicineRepository
                        .save(new UseMedicine(newUseMedicine.medicineName
                                , LocalDate.parse(newUseMedicine
                                .expirationDate), newUseMedicine.patientName));
            }

        } else {
            logger.warn("Not enough quantity for [{}]", medicine);
            throw new Exception("Not enough quantity for: " + medicine.getMedicineName());
        }

    }

    public List<ReceiveMedicine> getAllMedicine() {
        return receiveMedicineRepository.findAll();
    }

    public List<UseMedicine> getAllUsesOfMedicine() {
        return useMedicineRepository.findAll();
    }
}
