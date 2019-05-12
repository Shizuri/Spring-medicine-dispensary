package com.z.medicinedispensary.controllers;

import com.z.medicinedispensary.models.NewMedicine;
import com.z.medicinedispensary.models.Medicine;
import com.z.medicinedispensary.services.MedicineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receive")
public class MedicineController {

    private final MedicineService service;

    static final private Logger logger = LoggerFactory.getLogger(MedicineService.class);

    public MedicineController(MedicineService service) {
        this.service = service;
    }

    @GetMapping
    public List<Medicine> getAllMedicine(){
        return service.getAllMedicine();
    }

    @GetMapping("/alive")
    public String isAlive(){
        return "alive";
    }

    @PostMapping
    public ResponseEntity receiveNewMedicine(@RequestBody NewMedicine newMedicine){
        try {
            return ResponseEntity.ok().body(service.receiveMedicine(newMedicine));
        }catch (Exception exc){
            logger.warn("Found exception on post [{}]", exc.getMessage());
            return ResponseEntity.badRequest().body(exc.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity deleteMedicine(@RequestBody NewMedicine newMedicine){
        try {
            return ResponseEntity.ok().body(service.deleteMedicine(newMedicine));
        }catch (Exception exc){
            logger.warn("Found exception while deleting [{}]", exc.getMessage());
            return ResponseEntity.badRequest().body(exc.getMessage());
        }
    }
//for testing
//    @PostMapping("/test")
//    Medicine test1(@RequestBody NewMedicine newReceiveMedicine){
//        return service.receiveMedicine2(newReceiveMedicine);
//    }
}
