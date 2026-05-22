package net.engeneeringdigest.journalApp.service;

import net.engeneeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl UserDetailsServiceImpl;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserName(){
//        when(userRepository.findByUserName("Ram")).thenReturn(User.builder().username("ram").password("onvoi"));
        UserDetailsServiceImpl.loadUserByUsername("Ram");
    }
}


//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.Mockito.when;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserDetailsServiceImplTests {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserDetailsServiceImpl userDetailsServiceImpl;
//
//    @Test
//    public void testGetUserName(){
//
//        // Create REAL object
//        User user = new User();
//        user.setUserName("Ram");
//
//        // Tell mock what to return
//        when(userRepository.findByUserName("Ram"))
//                .thenReturn(user);
//
//        var result = userDetailsServiceImpl.loadUserByUsername("Ram");
//
//        assertNotNull(result);
//    }
//}
