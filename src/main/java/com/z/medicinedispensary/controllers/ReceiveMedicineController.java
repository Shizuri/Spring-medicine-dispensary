package com.z.medicinedispensary.controllers;

import com.z.medicinedispensary.models.Error;
import com.z.medicinedispensary.models.NewReceiveMedicine;
import com.z.medicinedispensary.models.ReceiveMedicine;
import com.z.medicinedispensary.services.ReceiveMedicineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receive")
public class ReceiveMedicineController {

    private final ReceiveMedicineService service;

    static final private Logger logger = LoggerFactory.getLogger(ReceiveMedicineService.class);

    public ReceiveMedicineController(ReceiveMedicineService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReceiveMedicine> getAllMedicine(){
        return service.getAllMedicine();
    }

    @PostMapping
    public ResponseEntity receiveNewMedicine(@RequestBody NewReceiveMedicine newReceiveMedicine){
        try {
            return ResponseEntity.ok().body(service.receiveMedicine(newReceiveMedicine));
        }catch (Exception exc){
            logger.warn("Found exception [{}]", exc.getMessage());
            return ResponseEntity.badRequest().body(exc.getMessage());
        }
    }
//for testing
//    @PostMapping("/test")
//    ReceiveMedicine test1(@RequestBody NewReceiveMedicine newReceiveMedicine){
//        return service.receiveMedicine2(newReceiveMedicine);
//    }
}
