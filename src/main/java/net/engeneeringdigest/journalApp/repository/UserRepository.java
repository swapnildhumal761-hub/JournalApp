package net.engeneeringdigest.journalApp.repository;

import net.engeneeringdigest.journalApp.entity.JournalEntry;
import net.engeneeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


// IN MONGODBREPOSITORY we give "table" i.e. "entity" name and primary "id" type
public interface UserRepository extends MongoRepository<User, ObjectId> {
//   here we create method to find user
//    Yes — Spring will try to build the query automatically, but only if the method name matches a real field correctly.
    User findByUserName(String userName);
    void deleteByUserName(String userName);
}
