package net.engeneeringdigest.journalApp.controller;


import net.engeneeringdigest.journalApp.api.response.WeatherResponse;
import net.engeneeringdigest.journalApp.entity.User;
import net.engeneeringdigest.journalApp.repository.UserRepository;
import net.engeneeringdigest.journalApp.service.UserService;
import net.engeneeringdigest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{userName}")
    public User getUser(@PathVariable String userName){
        return userService.findByUserName(userName);
    }
//    @DeleteMapping
//    public void deleteUser(ObjectId id){
//        userService.deleteById(id);
//    }

//    here we update password using username , userName is a unique

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        // ① Get the authenticated user's name from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();  // → "swapnil"

        // ② Find the existing user in DB
        User userIndb = userService.findByUserName(userName);

        // ③ Update fields from request body
        userIndb.setUserName(user.getUserName());    // → "swapnil_updated"
        userIndb.setPassword(user.getPassword());    // → "newPassword123"

        // ④ Save (password will be re-hashed)
        userService.saveNewUser(userIndb);

        // ⑤ Return 204 No Content
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Pune");
        String greeting = "";
        if(weatherResponse != null){
            greeting = " Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi"+" "+authentication.getName() + " " + greeting, HttpStatus.OK);
    }

}
