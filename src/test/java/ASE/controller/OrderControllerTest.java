package ASE.controller;

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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;

    /*
    @Test
    public void testGetOrderByBuyerId()throws Exception{
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
                .andExpect(jsonPath("$.id", is(order.getId().intValue())));
    }

    @Test
    public void testGetOrderBySellerId()throws Exception{
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
                .andExpect(jsonPath("$.id", is(order.getId().intValue())));
    }*/
}
