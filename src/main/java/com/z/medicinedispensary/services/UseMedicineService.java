package com.z.medicinedispensary.services;

import com.z.medicinedispensary.models.NewMedicine;
import com.z.medicinedispensary.models.NewUseMedicine;
import com.z.medicinedispensary.models.Medicine;
import com.z.medicinedispensary.models.UseMedicine;
import com.z.medicinedispensary.persistencies.MedicineRepository;
import com.z.medicinedispensary.persistencies.UseMedicineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UseMedicineService {

    private final MedicineRepository medicineRepository;
    private final UseMedicineRepository useMedicineRepository;

    static final private Logger logger = LoggerFactory.getLogger(UseMedicineService.class);


    public UseMedicineService(MedicineRepository medicineRepository, UseMedicineRepository useMedicineRepository) {
        this.medicineRepository = medicineRepository;
        this.useMedicineRepository = useMedicineRepository;
    }

    @Transactional
    public UseMedicine useMedicine(NewUseMedicine newUseMedicine) throws Exception {
        // find medicine in database
        Medicine medicine = medicineRepository
                .findMedicineByMedicineNameAndExpirationDate(newUseMedicine.medicineName,
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
            medicineRepository.findById(medicine.getId()).map(x -> {
                x.setQuantity(x.getQuantity() - 1);
                return x;
            });
            //delete from database if that medicine ran out
            if (medicine.getQuantity() == 0) {
                logger.warn("Quantity is [{}] DELETING [{}]", medicine.getQuantity(), medicine);
                medicineRepository.delete(medicine);
            }
            //save use of medicine with dateOfAdministration or without
            if (!newUseMedicine.dateOfAdministration.isEmpty()) {
                return useMedicineRepository
                        .save(new UseMedicine(newUseMedicine.medicineName
                                , LocalDate.parse(newUseMedicine
                                .expirationDate), newUseMedicine.patientName
                                , LocalDate.parse(newUseMedicine.dateOfAdministration)));
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

    public List<Medicine> getAllMedicine() {
        return medicineRepository.findAll();
    }

    public List<UseMedicine> getAllUsesOfMedicine() {
        return useMedicineRepository.findAll();
    }

    @Transactional
    public UseMedicine undoUse(NewUseMedicine newUseMedicine) throws Exception{
        // find the use in the database
        UseMedicine use = this.useMedicineRepository
                .findFirstByMedicineNameAndExpirationDateAndPatientNameAndDateOfAdministration
                        (
                                newUseMedicine.medicineName,
                                LocalDate.parse(newUseMedicine.expirationDate),
                                newUseMedicine.patientName,
                                LocalDate.parse(newUseMedicine.dateOfAdministration)
                        );
        logger.info("use is (if null, no such use): [{}]", use);
        // no such medicine, throw exception
        if (use == null) {
            throw new Exception("No such use");
        }
        // delete use
        useMedicineRepository.deleteById(use.getUseId());
        // increase value in medicine stock
        increaseMedicineByOne(newUseMedicine);
        return use;
    }

    private void increaseMedicineByOne(NewUseMedicine newUseMedicine) {

        Medicine medicine = medicineRepository.findMedicineByMedicineNameAndExpirationDate(newUseMedicine.medicineName, LocalDate.parse(newUseMedicine.expirationDate));
        // all medicine was used and deleted from the database, create a new one
        if (medicine == null) {
            logger.warn("Medicine was all used up, creating new such item with quantity 1");
            medicineRepository.save(new Medicine(1L, newUseMedicine.medicineName, LocalDate.parse(newUseMedicine.expirationDate)));
        }
        if (medicine != null) {
            logger.info("Undoing use of medicine [{}]", medicine);
            medicineRepository.findById(medicine.getId()).map(res ->{
                res.setQuantity(res.getQuantity() + 1L);
                return res;
            });
        }
    }
}
