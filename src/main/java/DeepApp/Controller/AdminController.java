package DeepApp.Controller;


import DeepApp.Entity.User;
import DeepApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-user")
    public ResponseEntity<?> getAllUser(){
        List<User> allUser = userService.getAll();
        if(allUser!=null && !allUser.isEmpty()){
            return new ResponseEntity<>(allUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

