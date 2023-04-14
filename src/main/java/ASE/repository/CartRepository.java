package ASE.repository;

import ASE.entity.Book;
import ASE.entity.Cart;
import ASE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findById(long id);

    Cart findByUserId(long userId);



}