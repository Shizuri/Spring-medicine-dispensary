package com.z.medicinedispensary.services;

import com.z.medicinedispensary.models.NewUseMedicine;
import com.z.medicinedispensary.models.ReceiveMedicine;
import com.z.medicinedispensary.models.UseMedicine;
import com.z.medicinedispensary.persistencies.ReceiveMedicineRepository;
import com.z.medicinedispensary.persistencies.UseMedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UseMedicineService {

    private final ReceiveMedicineRepository receiveMedicineRepository;
    private final UseMedicineRepository useMedicineRepository;

    public UseMedicineService(ReceiveMedicineRepository receiveMedicineRepository, UseMedicineRepository useMedicineRepository) {
        this.receiveMedicineRepository = receiveMedicineRepository;
        this.useMedicineRepository = useMedicineRepository;
    }

    @Transactional
    public UseMedicine useMedicine (NewUseMedicine newUseMedicine){
        List<ReceiveMedicine> allMedicine = getAllMedicine();

        for(ReceiveMedicine medicine : allMedicine){
            if(newUseMedicine.medicineName.equals(medicine.getMedicineName()) && LocalDate.parse(newUseMedicine.expirationDate).equals(medicine.getExpirationDate())){
                if(medicine.getQuantity() > 0){
                    receiveMedicineRepository.findById(medicine.getReceiveId()).map(x -> {
                        x.setQuantity(x.getQuantity() -1);
                        return x;
                    });
                    return useMedicineRepository.save(new UseMedicine(newUseMedicine.medicineName, LocalDate.parse(newUseMedicine.expirationDate), newUseMedicine.patientName, LocalDate.parse(newUseMedicine.dateOfAdministration)));
                }
            }
        }

        return null;
    }

/*    @Transactional
    public ReceiveMedicine receiveMedicine (NewReceiveMedicine newReceiveMedicine){
        List<ReceiveMedicine> allMedicine = getAllMedicine();
        for(ReceiveMedicine medicine : allMedicine){
            if(medicine.getMedicineName().equals(newReceiveMedicine.name) && LocalDate.parse(newReceiveMedicine.expirationDate).equals(medicine.getExpirationDate())){
                receiveMedicineRepository.findById(medicine.getReceiveId()).map(x ->{
                    x.setQuantity(x.getQuantity() + newReceiveMedicine.quantity);
                    return x;
                });
                return medicine;
            }
        }
        return receiveMedicineRepository.save(new ReceiveMedicine(newReceiveMedicine.quantity, newReceiveMedicine.name, LocalDate.parse(newReceiveMedicine.expirationDate)));
    }
*/
    public List<ReceiveMedicine> getAllMedicine(){
        return receiveMedicineRepository.findAll();
    }

    public List<UseMedicine> getAllUsesOfMedicine(){
        return useMedicineRepository.findAll();
    }
}
