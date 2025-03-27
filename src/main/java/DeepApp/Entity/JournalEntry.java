package DeepApp.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

@Document(collection = "journal_entries")
    @Data  // All in one  @Getter  @Setter
public class JournalEntry {
    @Id
    private ObjectId id;
    private String title;
    private String contains;
    private LocalDate date;

}
