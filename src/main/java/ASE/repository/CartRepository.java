package ASE.repository;

import ASE.entity.Book;
import ASE.entity.Cart;
import ASE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("cartRepository")
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findById(long id);

    Cart findByUserId(long userId);

}