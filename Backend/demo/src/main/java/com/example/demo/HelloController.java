package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HelloController {
    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserEntity>> helloWorld() throws Exception{

            List<UserEntity> users = new ArrayList<>();
            logger.info("Started to fetch the data");
            Iterable<UserEntity> userEntities = userRepository.getAllUserFromProcedure();
//            userEntities.forEach(users::add);
            if(users.isEmpty()) {
                throw new SQLException("Whole data is flushed THIS IS CRITICAL...!");
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @ExceptionHandler(UserListEmptyException.class)
    public ResponseEntity<String> handleUserListEmptyException(UserListEmptyException ex) {
        logger.warn("There are no users in the Database");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleUserListSQLException(SQLException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>("Some Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody UserRequest userRequest){

        UserEntity userEntity = new UserEntity();

        userEntity.setName(userRequest.getName());
        userEntity.setAge(userRequest.getAge());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setGender(userRequest.getGender());

        userRepository.InsertUser(
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getAge(),
                userEntity.getGender()
        );

        return "Saved";

    }
}
