package ASE.repository;

import ASE.entity.Cart;
import ASE.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class OrderRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;


    @Test
    public void findByBuyerId_success() {
        Order order=new Order();
        order.setBuyerId(1L);
        order.setSellerId(2L);
        entityManager.persist(order);
        entityManager.flush();

        Order found = orderRepository.findByBuyerId(order.getBuyerId());

        assertNotNull(found.getId());
        assertEquals(found.getBuyerId(), order.getBuyerId());
    }

    @Test
    public void findBySellerId_success() {
        Order order=new Order();
        order.setSellerId(1L);
        order.setBuyerId(1L);
        entityManager.persist(order);
        entityManager.flush();

        Order found = orderRepository.findBySellerId(order.getSellerId());

        assertNotNull(found.getId());
        assertEquals(found.getSellerId(), order.getSellerId());
    }



}
