package DeepApp.Controller;

import DeepApp.Entity.User;
import DeepApp.Repository.UserRepository;
import DeepApp.Services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("id/{username}/{id}")
    public Optional<User> getById(@PathVariable ObjectId id, @PathVariable String username) {
        return userService.getById(id, username);

    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByName(userName);
        if (userInDb != null) {
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



     @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}