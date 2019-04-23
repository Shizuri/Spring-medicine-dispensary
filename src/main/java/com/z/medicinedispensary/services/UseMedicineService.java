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
    public UseMedicine useMedicine(NewUseMedicine newUseMedicine) {
        List<ReceiveMedicine> allMedicine = getAllMedicine();

        for (ReceiveMedicine medicine : allMedicine) {
            if (newUseMedicine.medicineName.equals(medicine.getMedicineName()) && LocalDate.parse(newUseMedicine.expirationDate).equals(medicine.getExpirationDate())) {
                if (medicine.getQuantity() > 0) {
                    logger.info("Using medicine: [{}]", medicine);
                    receiveMedicineRepository.findById(medicine.getReceiveId()).map(x -> {
                        x.setQuantity(x.getQuantity() - 1);
                        return x;
                    });
                    return useMedicineRepository.save(new UseMedicine(newUseMedicine.medicineName, LocalDate.parse(newUseMedicine.expirationDate), newUseMedicine.patientName, LocalDate.parse(newUseMedicine.dateOfAdministration)));
                } else {
                    logger.warn("Not enough quantity for [{}]", medicine);
                    return null;
                }
            }
        }
        logger.warn("No such medicine found");

        return null;
    }

    /*    @Transactional
        public ReceiveMedicine receiveMedicine (NewReceiveMedicine newReceiveMedicine){
            List<ReceiveMedicine> allMedicine = getAllMedicine();
            for(ReceiveMedicine medicine : allMedicine){
                if(medicine.getMedicineName().equals(newReceiveMedicine.medicineName) && LocalDate.parse(newReceiveMedicine.expirationDate).equals(medicine.getExpirationDate())){
                    receiveMedicineRepository.findById(medicine.getReceiveId()).map(x ->{
                        x.setQuantity(x.getQuantity() + newReceiveMedicine.quantity);
                        return x;
                    });
                    return medicine;
                }
            }
            return receiveMedicineRepository.save(new ReceiveMedicine(newReceiveMedicine.quantity, newReceiveMedicine.medicineName, LocalDate.parse(newReceiveMedicine.expirationDate)));
        }
    */
    public List<ReceiveMedicine> getAllMedicine() {
        return receiveMedicineRepository.findAll();
    }

    public List<UseMedicine> getAllUsesOfMedicine() {
        return useMedicineRepository.findAll();
    }
}
