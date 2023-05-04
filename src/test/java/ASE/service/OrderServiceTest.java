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

public class OrderServiceTest {
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository);
    }

    @Test
    public void testGetOrderByBuyerId(){
        // given
        long buyerid = 1L;
        Order order=new Order();
        order.setBuyerId(buyerid);
        when(orderRepository.findByBuyerId(buyerid)).thenReturn(order);

        // when
        Order result=orderService.getOrderByBuyerId(buyerid);

        // then
        assertEquals(1L, result.getBuyerId());
        verify(orderRepository, times(1)).findByBuyerId(buyerid);
    }

    @Test
    public void testGetOrderBySellerId(){
        // given
        long sellerid = 1L;
        Order order=new Order();
        order.setSellerId(sellerid);
        when(orderRepository.findBySellerId(sellerid)).thenReturn(order);

        // when
        Order result=orderService.getOrderBySellerId(sellerid);

        // then
        assertEquals(1L, result.getSellerId());
        verify(orderRepository, times(1)).findBySellerId(sellerid);
    }

    @Test
    public void testCreateOrder(){
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
