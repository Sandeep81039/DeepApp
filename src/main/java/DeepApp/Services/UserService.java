package DeepApp.Services;

import DeepApp.Entity.User;
import DeepApp.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  ///  private final static Logger logger = LoggerFactory.getLogger(UserService.class);// Instance of this use slf4 @


    public ResponseEntity<?> saveNewUser(User myEntry){
        try {
            myEntry.setPassword( passwordEncoder.encode(myEntry.getPassword()));
            myEntry.setRoles(Arrays.asList("USER"));
            userRepository.save(myEntry);
            return new  ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            log.info("HYY");
            log.trace("HYY");
            log.warn("HYY");
            log.error("HYY");
            log.debug("HYY");
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
     }


    public void saveUser(User myEntry){
        myEntry.setRoles(Arrays.asList("USER"));
        userRepository.save(myEntry);
    }

     public List<User> getAll(){
         return userRepository.findAll();
     }

     public Optional<User> getById(ObjectId id, String username){
         return userRepository.findById(id);
     }


     public void deleteById(ObjectId id){
         userRepository.deleteById(id);
     }

     public User findByName(String userName){
         return userRepository.findByUsername(userName);
     }

}

 

// Controller --------> service ------> repository ;