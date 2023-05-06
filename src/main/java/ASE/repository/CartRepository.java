package ASE.repository;

import ASE.entity.Book;
import ASE.entity.Cart;
import ASE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * The CartRepository interface is responsible for managing the persistence and retrieval of Cart entities.
 */
@Repository("cartRepository")
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Retrieves a cart by its ID.
     *
     * @param id the ID of the cart
     * @return the cart with the specified ID
     */
    Cart findById(long id);

    /**
     * Retrieves a cart by the user ID.
     *
     * @param userId the ID of the user
     * @return the cart associated with the user ID
     */
    Cart findByUserId(long userId);
}