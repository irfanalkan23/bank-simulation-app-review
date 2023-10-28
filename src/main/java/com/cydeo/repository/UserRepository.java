package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //this is not a must
public interface UserRepository extends JpaRepository<User,Long> {

    //I need a method accepting username (because of loadUserByUsername(String username))
    User findByUsername(String username);
}
