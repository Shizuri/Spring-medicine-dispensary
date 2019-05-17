package com.z.medicinedispensary.controllers;

import com.z.medicinedispensary.models.LoginUser;
import com.z.medicinedispensary.models.NewUser;
import com.z.medicinedispensary.models.User;
import com.z.medicinedispensary.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody NewUser newUser){
        try{
            return ResponseEntity.ok().body(userService.createUser(newUser));
        } catch (Exception exc){
            return ResponseEntity.badRequest().body(exc.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginUser loginUser){
        if(userService.logIn(loginUser)){
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.badRequest().body("Invalid user name or password");
        }
    }
}
