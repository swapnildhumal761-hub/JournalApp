package net.engeneeringdigest.journalApp.controller;

import net.engeneeringdigest.journalApp.entity.User;
import net.engeneeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }

//    @PostMapping
//    public ResponseEntity<?> createUser(@RequestBody User user){
//        userService.saveUser(user);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

    @PostMapping("/register")
    public String createUser(@RequestBody User user){
        userService.saveNewUser(user);
        return "user registered successfully";
    }
}