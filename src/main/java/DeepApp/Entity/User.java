package DeepApp.Entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
    @Data  // All in one  @Getter  @Setter
public class User {
    @Id
    ObjectId id;
    @Indexed(unique = true)
    @NonNull
    String username;
    @NonNull
    String password;

    @DBRef
    public List<JournalEntry>  journalEntries = new ArrayList<>();
   public List<String> roles;


}
