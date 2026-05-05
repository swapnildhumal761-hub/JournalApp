package net.engeneeringdigest.journalApp.repository;

import net.engeneeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


// IN MONGODBREPOSITORY we give "table" i.e. "entity" name and primary "id" type
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
