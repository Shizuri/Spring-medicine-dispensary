package com.z.medicinedispensary.controllers;

import com.z.medicinedispensary.models.NewUseMedicine;
import com.z.medicinedispensary.models.UseMedicine;
import com.z.medicinedispensary.services.UseMedicineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/use")
public class UseMedicineController {

    private final UseMedicineService service;

    static final private Logger logger = LoggerFactory.getLogger(UseMedicineController.class);

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
            logger.warn("|useNewMedicine| Found exception [{}]", exc.getMessage());
            return ResponseEntity.badRequest().body(exc.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity undoUse(@RequestBody NewUseMedicine newUseMedicine){
        try {
            return ResponseEntity.ok().body(service.undoUse(newUseMedicine));
        }catch (Exception exc){
            logger.warn("|undoUse| Found exception while undoing use [{}]", exc.getMessage());
            return ResponseEntity.badRequest().body(exc.getMessage());
        }
    }
}
