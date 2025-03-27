package DeepApp.Controller;

import DeepApp.Entity.JournalEntry;
import DeepApp.Entity.User;
import DeepApp.Repository.JournalEntryRepository;
import DeepApp.Repository.UserRepository;
import DeepApp.Services.JournalEntryService;
import DeepApp.Services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("journal")
public class JournalEntryController {


  @Autowired
 private JournalEntryService journalEntryService;

  @Autowired
  private UserService userService;



    @GetMapping()
    public ResponseEntity<?> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userdb =userService.findByName(authentication.getName());
        List<JournalEntry> all = userdb.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }





    @PostMapping()
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          journalEntryService.saveEntry(myEntry, authentication.getName());
           return new ResponseEntity<>(myEntry,HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }




    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user =  userService.findByName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
         if(!collect.isEmpty()) {
             Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);
             if (journalEntry.isPresent()) {
                 return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
             }
         }
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteVariableById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByName(userName);

        // Directly fetch from DB instead of filtering in memory
        Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);

        if (journalEntry.isPresent() && user.getJournalEntries().contains(journalEntry.get())) {
            journalEntryService.deleteById(myId, authentication.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateGeneralById(@PathVariable ObjectId myId,@RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user =  userService.findByName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);
            if (journalEntry.isPresent()) {
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContains(newEntry.getContains() != null && !newEntry.getContains().equals("") ? newEntry.getContains() : old.getContains());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }



}