package com.z.medicinedispensary.persistencies;

import com.z.medicinedispensary.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);
}
