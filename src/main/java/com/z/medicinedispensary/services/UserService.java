package com.z.medicinedispensary.services;

import com.z.medicinedispensary.models.LoginUser;
import com.z.medicinedispensary.models.NewUser;
import com.z.medicinedispensary.models.User;
import com.z.medicinedispensary.persistencies.UserRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    static final private Logger logger = LoggerFactory.getLogger(UserService.class);


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(NewUser newUser) throws Exception {
        //check for duplicates
        User foundUser = userRepository.findFirstByName(newUser.name);
        if (foundUser != null) {
            logger.warn("User name [{}] already exists", newUser.name);
            throw new Exception("User already exists");
        } else {
            logger.info("Saving [{}] to repo ", newUser.name);
            return userRepository.save(new User(newUser.name, encoder().encode(newUser.password), newUser.role, newUser.active));
        }
    }

    public User logIn(LoginUser user) throws Exception{
        User foundUser = userRepository.findFirstByName(user.name);
        if(foundUser == null){
            logger.warn("No such user [{}]", user.name);
            throw new Exception("No such user");
        } else if(encoder().matches(user.password, foundUser.getPassword())) {
            logger.info("User and password are correct");
            return foundUser;
        } else {
            logger.warn("User exists but password is wrong!");
            throw new Exception("User exists but password is wrong");
        }
    }

//    public boolean logIn2(LoginUser user) {
//
//        User foundUser = userRepository.findFirstByName(user.name);
//        if(foundUser == null){
//            logger.warn("No such user [{}]", user.name);
//            return false;
//        } else if(encoder().matches(user.password, foundUser.getPassword())) {
//            logger.info("User and password are correct");
//            return true;
//        } else {
//            logger.warn("User exists but password is wrong!");
//            return false;
//        }
//    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
