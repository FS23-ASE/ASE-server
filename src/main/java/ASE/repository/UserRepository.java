package ASE.repository;

import ASE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The UserRepository interface is responsible for managing the persistence and retrieval of User entities.
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the user with the specified ID
     */
    User findById(long id);

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user
     * @return the user with the specified username
     */
    User findByUsername(String username);

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return the user with the specified email
     */
    User findByEmail(String email);
}

