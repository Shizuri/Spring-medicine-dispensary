package com.z.medicinedispensary.services;

import com.z.medicinedispensary.models.Medicine;
import com.z.medicinedispensary.models.NewMedicine;
import com.z.medicinedispensary.persistencies.MedicineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;

    static final private Logger logger = LoggerFactory.getLogger(MedicineService.class);

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @Transactional
    public Medicine receiveMedicine(NewMedicine newMedicine) {
        // find the medicine in the database
        Medicine medicine = medicineRepository.findMedicineByMedicineNameAndExpirationDate
                (newMedicine.medicineName, LocalDate.parse(newMedicine.expirationDate));
        // if you do find it, increase its quantity
        if (medicine != null) {
            logger.info("Found duplicate [{}], will not add new, increasing quantity.", medicine);
            medicineRepository.findById(medicine.getId()).map(x -> {
                x.setQuantity(x.getQuantity() + newMedicine.quantity);
                return x;
            });
            return medicine;
        }
        //if you don't find it, add it to the database
        logger.info("Saving to database: [{}]", newMedicine);
        return medicineRepository
                .save(new Medicine(newMedicine.quantity, newMedicine.medicineName,
                        LocalDate.parse(newMedicine.expirationDate)));
    }


    public List<Medicine> getAllMedicine() {
        return medicineRepository.findAll();
    }

    @Transactional
    public Medicine deleteMedicine(NewMedicine newMedicine) throws Exception {
        // find the medicine in the database
        Medicine medicine = medicineRepository.findMedicineByMedicineNameAndExpirationDate
                (newMedicine.medicineName, LocalDate.parse(newMedicine.expirationDate));
        logger.info("medicine is (if null, no such medicine): [{}]", medicine);
        // no such medicine, throw exception
        if (medicine == null) {
            throw new Exception("No such medicine");
        }
        // if medicine is found in the database, delete it all if quantity is 0 or more than all
        if (newMedicine.quantity <= 0 || newMedicine.quantity >= medicine.getQuantity()) {
            medicineRepository.deleteById(medicine.getId());
        } else { // else, lower quantity
            medicineRepository.findById(medicine.getId()).map(res ->{
               res.setQuantity(res.getQuantity() - newMedicine.quantity);
               return res;
            });
        }
        return medicine;
    }

}
