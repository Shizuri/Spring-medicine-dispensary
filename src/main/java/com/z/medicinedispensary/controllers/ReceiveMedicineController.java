package com.z.medicinedispensary.controllers;

import com.z.medicinedispensary.models.Error;
import com.z.medicinedispensary.models.NewReceiveMedicine;
import com.z.medicinedispensary.models.ReceiveMedicine;
import com.z.medicinedispensary.services.ReceiveMedicineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recieve")
public class ReceiveMedicineController {

    private final ReceiveMedicineService service;

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
            exc.printStackTrace();
            return ResponseEntity.badRequest().body(new Error(exc.getMessage()));
        }
    }
}
