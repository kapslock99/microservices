package com.microservices.userService.repositaries;

import com.microservices.userService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositary extends JpaRepository<User , String> {

}
