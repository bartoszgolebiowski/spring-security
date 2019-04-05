package pl.ttwug.security.ttwug.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.ttwug.security.ttwug.model.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserRepository extends MongoRepository<ApplicationUser, ObjectId> {
    Optional<ApplicationUser> findByUsername(String username);

    Optional<ApplicationUser> findByUsernameOrEmail(String username, String email);

    Optional<ApplicationUser> findByEmail(String email);
}