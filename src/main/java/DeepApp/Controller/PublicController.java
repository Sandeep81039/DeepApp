package DeepApp.Controller;

import DeepApp.Entity.User;
import DeepApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("public")
@RestController
public class PublicController {

    @Autowired
    private UserService userService;


    @PostMapping("createEntry")
    public ResponseEntity<?> createUser(@RequestBody User user){
         return    userService.saveNewUser(user);
    }


}
