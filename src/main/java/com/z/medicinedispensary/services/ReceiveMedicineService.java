package com.z.medicinedispensary.services;

import com.z.medicinedispensary.models.NewReceiveMedicine;
import com.z.medicinedispensary.models.ReceiveMedicine;
import com.z.medicinedispensary.persistencies.ReceiveMedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReceiveMedicineService {

    private final ReceiveMedicineRepository receiveMedicineRepository;

    public ReceiveMedicineService(ReceiveMedicineRepository receiveMedicineRepository) {
        this.receiveMedicineRepository = receiveMedicineRepository;
    }

    @Transactional
    public ReceiveMedicine receiveMedicine (NewReceiveMedicine newReceiveMedicine){
        List<ReceiveMedicine> allMedicine = getAllMedicine();
        for(ReceiveMedicine medicine : allMedicine){
            if(medicine.getName().equals(newReceiveMedicine.name) && LocalDate.parse(newReceiveMedicine.expirationDate).equals(medicine.getExpirationDate())){
                receiveMedicineRepository.findById(medicine.getId()).map(x ->{
                    x.setQuantity(x.getQuantity() + newReceiveMedicine.quantity);
                    return x;
                });
                return medicine;
            }
        }
        return receiveMedicineRepository.save(new ReceiveMedicine(newReceiveMedicine.quantity, newReceiveMedicine.name, LocalDate.parse(newReceiveMedicine.expirationDate)));
    }

    public List<ReceiveMedicine> getAllMedicine(){
        return receiveMedicineRepository.findAll();
    }
}
