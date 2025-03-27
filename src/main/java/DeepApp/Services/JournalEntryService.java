package DeepApp.Services;

import DeepApp.Entity.JournalEntry;
import DeepApp.Entity.User;
import DeepApp.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private  UserService userService;

    @Transactional
     public void saveEntry(JournalEntry myEntry, String username){
             User user = userService.findByName(username);
             myEntry.setDate(LocalDate.now());
             JournalEntry saved = journalEntryRepository.save(myEntry);
             user.getJournalEntries().add(saved);
             //user.setUsername(null);
             userService.saveUser(user);
     }


    public void saveEntry(JournalEntry myEntry){
        journalEntryRepository.save(myEntry);
    }



     public Optional<JournalEntry> getById(ObjectId id){
         return journalEntryRepository.findById(id);
     }


     public void deleteById(ObjectId id, String username){
         User user = userService.findByName(username);
         user.getJournalEntries().removeIf(x -> x.getId().equals(id));
         userService.saveUser(user);
         journalEntryRepository.deleteById(id);
     }



}



// Controller --------> service ------> repository ;