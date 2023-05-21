package ASE.controller;

import ASE.entity.Book;
import ASE.entity.Order;
import ASE.repository.OrderRepository;
import ASE.service.BookService;
import ASE.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;


    @Test
    void testGetOrderByBuyerId()throws Exception{
        long buyerId=1L;
        Order order=new Order();
        order.setId(1L);
        order.setBuyerId(buyerId);
        List<Order> orders=new ArrayList<>();
        orders.add(order);
        given(orderService.getOrderByBuyerId(Mockito.anyLong())).willReturn(orders);

        MockHttpServletRequestBuilder getRequest = get("/order/buyer/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(order.getId().intValue())));

    }

    @Test
    void testGetOrderBySellerId()throws Exception{
        long sellerId=2L;
        Order order=new Order();
        order.setId(2L);
        order.setSellerId(sellerId);
        List<Order> orders=new ArrayList<>();
        orders.add(order);
        given(orderService.getOrderBySellerId(Mockito.anyLong())).willReturn(orders);

        MockHttpServletRequestBuilder getRequest = get("/order/seller/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(order.getId().intValue())));
    }

    @Test
    void testGetOrderById()throws Exception{
        long id=2L;
        Order order=new Order();
        order.setId(id);
        given(orderService.getOrderById(Mockito.anyLong())).willReturn(order);

        MockHttpServletRequestBuilder getRequest = get("/order/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(order.getId().intValue())));
    }

    @Test
    void testGetBooks() throws Exception{
        long id=2L;
        Order order=new Order();
        order.setId(id);
        Book book=new Book();
        book.setId(1L);
        List<Book> books=new ArrayList<>();
        books.add(book);
        order.setBooks(books);
        given(orderService.getOrderById(Mockito.anyLong())).willReturn(order);

        MockHttpServletRequestBuilder getRequest = get("/order/books/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(book.getId().intValue())));
    }


    @Test
    void testReceiveOrder() throws Exception{
        long id=2L;
        Order order=new Order();
        order.setId(id);
        order.setStatus("PAID");
        given(orderService.getOrderById(Mockito.anyLong())).willReturn(order);

        MockHttpServletRequestBuilder putRequest = put("/order/received/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());
    }

    @Test
    void testCancelOrder() throws Exception{
        long id=2L;
        Order order=new Order();
        order.setId(id);
        order.setStatus("PAID");
        given(orderService.getOrderById(Mockito.anyLong())).willReturn(order);

        MockHttpServletRequestBuilder putRequest = put("/order/cancel/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());
    }
}
