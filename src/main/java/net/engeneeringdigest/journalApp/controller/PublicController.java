package net.engeneeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engeneeringdigest.journalApp.entity.User;
import net.engeneeringdigest.journalApp.service.UserDetailsServiceImpl;
import net.engeneeringdigest.journalApp.service.UserService;
import net.engeneeringdigest.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }

//    @PostMapping
//    public ResponseEntity<?> createUser(@RequestBody User user){
//        userService.saveUser(user);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user){
        userService.saveNewUser(user);
        return "user registered successfully";
    }

//  here we do jwt authentication.......

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken",e);
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}