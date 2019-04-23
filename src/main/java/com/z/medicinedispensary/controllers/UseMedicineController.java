package com.z.medicinedispensary.controllers;

import com.z.medicinedispensary.models.Error;
import com.z.medicinedispensary.models.NewUseMedicine;
import com.z.medicinedispensary.models.UseMedicine;
import com.z.medicinedispensary.services.UseMedicineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/use")
public class UseMedicineController {

    private final UseMedicineService service;

    public UseMedicineController(UseMedicineService service) {
        this.service = service;
    }

    @GetMapping
    public List<UseMedicine> getAllUsesOfMedicine(){
        return service.getAllUsesOfMedicine();
    }

    @PostMapping
    public ResponseEntity useNewMedicine(@RequestBody NewUseMedicine newUseMedicine){
        try {
            return ResponseEntity.ok().body(service.useMedicine(newUseMedicine));
        }catch (Exception exc){
            exc.printStackTrace();
            return ResponseEntity.badRequest().body(new Error(exc.getMessage()));
        }
    }
}
