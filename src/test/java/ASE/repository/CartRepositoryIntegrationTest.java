package ASE.repository;


import ASE.entity.Book;
import ASE.entity.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@DataJpaTest
public class CartRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CartRepository cartRepository;
    @Test
    public void findByUserId_success() {
        // given
        Cart cart = new Cart();
        cart.setUserId(1L);

        entityManager.persist(cart);
        entityManager.flush();

        // when
        Cart found = cartRepository.findByUserId(cart.getUserId());

        // then
        assertNotNull(found.getId());
        assertEquals(found.getUserId(), cart.getUserId());
    }
}
