package com.server.hammer.Repository;

import com.server.hammer.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    //@Query(value = "from User where userId = :username ")
    User findUserByUserId (String userid);


}
