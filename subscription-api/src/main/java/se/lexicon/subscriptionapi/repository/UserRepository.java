package se.lexicon.subscriptionapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.subscriptionapi.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    void deleteById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByFirstNameIgnoreCase(String firstName);
    Optional<User> findByLastNameIgnoreCase(String lastName);
    boolean existsByEmail(String email);

    // add necessery query methods for searching by credentials, etc.
}
