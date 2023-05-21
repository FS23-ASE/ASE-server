package ASE.repository;

import ASE.entity.Book;
import ASE.entity.Cart;
import ASE.entity.Order;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Nested
class OrderRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;



    @Test
    void findByBuyerId_success() {
        Order order=new Order();
        order.setBuyerId(1L);
        order.setDate("1");
        order.setStatus("Paid");
        order.setSellerId(2L);
        order.setAmount(1);
        List<Book> books=new ArrayList<>();
        order.setBooks(books);
        entityManager.persist(order);
        entityManager.flush();

        List<Order> found = orderRepository.findByBuyerId(order.getBuyerId());

        assertNotNull(found);
        assertEquals(found.get(0).getBuyerId(), order.getBuyerId());
    }

    @Test
    void findBySellerId_success() {
        Order order=new Order();
        order.setSellerId(1L);
        order.setDate("1");
        order.setStatus("Paid");
        order.setBuyerId(2L);
        order.setAmount(1);
        List<Book> books=new ArrayList<>();
        order.setBooks(books);
        entityManager.persist(order);
        entityManager.flush();

        List<Order> found = orderRepository.findBySellerId(order.getSellerId());

        assertNotNull(found);
        assertEquals(found.get(0).getSellerId(), order.getSellerId());
    }



}
