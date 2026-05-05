
package net.engeneeringdigest.journalApp.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

// we are mapping this class with collection in mongodb
// each object of that class is document in mongodb

@Document(collection = "journal_entries")
@Getter
@Setter

// @Data is equivalent to @getter, @setter, @requiredArgsConstructor, @tostring, @equalsAndHashCode

public class JournalEntry {
//    @Id is for primary key
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private  String content;
    private LocalDateTime date;
}
