package com.example.demo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserEntity,Long> {


    @Query(value = "CALL GetAllUsers()", nativeQuery = true)
    Iterable<UserEntity> getAllUserFromProcedure();

    @Procedure(name = "InsertUser")
    void InsertUser(
            @Param("name") String name,
            @Param("email") String email,
            @Param("age") int age,
            @Param("gender") String gender
    );
}
