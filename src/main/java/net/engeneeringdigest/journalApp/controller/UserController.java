package net.engeneeringdigest.journalApp.controller;


import net.engeneeringdigest.journalApp.entity.User;
import net.engeneeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @DeleteMapping
//    public void deleteUser(ObjectId id){
//        userService.deleteById(id);
//    }

//    here we update password using username , userName is a unique
    @GetMapping("/{userName}")
    public User getUser(@PathVariable String userName){

        return userService.findByUserName(userName);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        User userIndb = userService.findByUserName(userName);
        if(userIndb != null){
            userIndb.setUserName(user.getUserName());
            userIndb.setPassword(user.getPassword());
            userService.saveUser(userIndb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
