package com.z.medicinedispensary.services;

import com.z.medicinedispensary.models.*;
import com.z.medicinedispensary.persistencies.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            logger.warn("|createUser| User name [{}] already exists", newUser.name);
            throw new Exception("User already exists");
        } else {
            logger.info("|createUser| Saving [{}] to repo ", newUser.name);
            return userRepository.save(new User(newUser.name, encoder().encode(newUser.password), newUser.role, newUser.active));
        }
    }

    public User logIn(LoginUser user) throws Exception{
        User foundUser = userRepository.findFirstByName(user.name);
        if(foundUser == null){
            logger.warn("|logIn| No such user [{}]", user.name);
            throw new Exception("No such user");
        } else if(encoder().matches(user.password, foundUser.getPassword())) {
            logger.info("|logIn| User and password are correct");
            return foundUser;
        } else {
            logger.warn("|logIn| User exists but password is wrong!");
            throw new Exception("User exists but password is wrong");
        }
    }

    public User deleteUser(String name) throws Exception{
        User foundUser = userRepository.findFirstByName(name);
        if(foundUser == null){
            logger.warn("|deleteUser| No such user [{}]", name);
            throw new Exception("No such user");
        } else {
            logger.warn("|deleteUser| Deleting user: [{}]", foundUser.getName());
            try {
                userRepository.delete(foundUser);
            }catch (Exception exc){
                logger.warn("|deleteUser| Error during deleting: [{}]", exc.getMessage());
            }
            return foundUser;
        }
    }

    @Transactional
    public User updateUser(UpdateUser user) throws Exception{
        User foundUser = userRepository.findFirstByName(user.name);
        if(foundUser == null){
            logger.warn("|updateUser| No such user [{}]", user.name);
            throw new Exception("No such user");
        } else {
            logger.warn("|updateUser| Updating user: [{}]", foundUser);
            logger.warn("|updateUser| Using values: [{}]", user);
            try {
                foundUser.setName(user.newName);
                foundUser.setActive(user.active);
                foundUser.setRole(user.role);
            }catch (Exception exc){
                logger.warn("|updateUser| Error during updating: [{}]", exc.getMessage());
            }
            return foundUser;
        }
    }

    @Transactional
    public User changePassword(ChangePassword change) throws Exception{
        User foundUser = userRepository.findFirstByName(change.name);
        if(foundUser == null){
            logger.warn("|changePassword| No such user [{}]", change.name);
            throw new Exception("No such user");
        }
        if(!change.newPassword.equals(change.repeatPassword)){
            logger.warn("|changePassword| Passwords don't match");
            throw new Exception("Passwords don't match");
        }
        if(encoder().matches(change.oldPassword, foundUser.getPassword())){
            logger.info("|changePassword| User and password are correct, changing password");
            foundUser.setPassword(encoder().encode(change.newPassword));
        } else {
            logger.warn("|changePassword| Incorrect password");
            throw new Exception("Incorrect password");
        }
        return foundUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
