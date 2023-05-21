package ASE.service;

import ASE.entity.Book;
import ASE.entity.Order;
import ASE.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * The OrderServiceTest class is responsible for testing the OrderService functionality.
 */
class OrderServiceTest {
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository);
    }


    /**
     * Test case for retrieving an order by the buyer ID from the OrderRepository and verifying the result.
     */
    @Test
    void testGetOrderByBuyerId(){
        // given
        long buyerid = 1L;
        Order order=new Order();
        order.setBuyerId(buyerid);
        List<Order> orders=new ArrayList<>();
        orders.add(order);
        when(orderRepository.findByBuyerId(buyerid)).thenReturn(orders);

        // when
        List<Order> result=orderService.getOrderByBuyerId(buyerid);

        // then
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findByBuyerId(buyerid);
    }


    /**
     * Test case for retrieving an order by the seller ID from the OrderRepository and verifying the result.
     */
    @Test
    void testGetOrderBySellerId(){
        // given
        long sellerid = 1L;
        Order order=new Order();
        order.setSellerId(sellerid);
        List<Order> orders=new ArrayList<>();
        orders.add(order);
        when(orderRepository.findBySellerId(sellerid)).thenReturn(orders);

        // when
        List<Order> result=orderService.getOrderBySellerId(sellerid);

        // then
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findBySellerId(sellerid);
    }


    /**
     * Test case for creating an order in the OrderRepository and verifying the result.
     */
    @Test
    void testCreateOrder(){
        Book newBook=new Book();
        Order newOrder=new Order();
        newOrder.setBuyerId(2L);
        when(orderRepository.save(newOrder)).thenReturn(newOrder);

        // when
        Order result=orderService.createOrder(newOrder);

        // then
        assertEquals(newOrder, result);
        verify(orderRepository, times(1)).save(newOrder);
        verify(orderRepository, times(1)).flush();
    }



}
