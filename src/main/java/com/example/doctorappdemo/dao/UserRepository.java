package com.example.doctorappdemo.dao;

import com.example.doctorappdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserByEmail(String email);
    User findUserByUserName(String username);
    User findById(int id);
    User findByEmail(String email);

    @Query("select  u.id from User u where u.email = :username")
    int findId(@Param("username") String username);


}

