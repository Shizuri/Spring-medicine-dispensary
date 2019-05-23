package com.z.medicinedispensary.controllers;

import com.z.medicinedispensary.models.*;
import com.z.medicinedispensary.services.UserService;
import org.springframework.http.HttpStatus;
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

    @GetMapping()
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody NewUser newUser){
        try{
            return ResponseEntity.ok().body(userService.createUser(newUser));
        } catch (Exception exc){
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping()
    public ResponseEntity login(@RequestBody LoginUser loginUser){
        try{
            return ResponseEntity.ok().body(userService.logIn(loginUser));
        } catch (Exception exc){
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping()
    public ResponseEntity delete(@RequestBody String name){
        try{
            return ResponseEntity.ok().body(userService.deleteUser(name));
        } catch (Exception exc){
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping()
    public ResponseEntity updateUser(@RequestBody UpdateUser user){
        try {
            return ResponseEntity.ok().body(userService.updateUser(user));
        } catch (Exception exc){
            return ResponseEntity.badRequest().body(exc.getMessage());
        }
    }

    @PutMapping("/change")
    public ResponseEntity changePassword(@RequestBody ChangePassword change){
        try {
            return ResponseEntity.ok().body(userService.changePassword(change));
        } catch (Exception exc){
            return ResponseEntity.badRequest().body(exc.getMessage());
        }
    }
}
