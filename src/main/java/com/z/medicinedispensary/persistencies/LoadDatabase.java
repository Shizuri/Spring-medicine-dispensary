package com.z.medicinedispensary.persistencies;

import com.z.medicinedispensary.controllers.UsersController;
import com.z.medicinedispensary.models.NewUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    static final private Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UsersController controller){
        NewUser user = new NewUser();
        user.name = "Zdravko";
        user.password = "pass";
        user.role = "ADMIN";
        user.active = true;
        return args -> {
            logger.info("Preloading admin " + controller.createUser(user));
        };

    }
}