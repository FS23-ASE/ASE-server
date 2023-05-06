package ASE.controller;

import ASE.entity.Cart;
import ASE.entity.Order;
import ASE.rest.dto.CartGetDTO;
import ASE.rest.dto.CartPostDTO;
import ASE.rest.dto.OrderGetDTO;
import ASE.rest.dto.OrderPostDTO;
import ASE.rest.mapper.DTOMapper;
import ASE.service.CartService;
import ASE.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * Order Controller
 * This class is responsible for handling all REST request that are related to
 * the order.
 * The controller will receive the request and delegate the execution to the
 * OrderService and finally return the result.
 */
@RestController
public class OrderController {
    private final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/buyer/{buyerId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OrderGetDTO getOrderByBuyerId(@PathVariable("buyerId") Long buyerId) {
        Order order = orderService.getOrderByBuyerId(buyerId);
        return DTOMapper.INSTANCE.convertEntityToOrderGetDTO(order);
    }

    @GetMapping("/order/seller/{sellerId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OrderGetDTO getOrderBySellerId(@PathVariable("sellerId") Long sellerId) {
        Order order = orderService.getOrderBySellerId(sellerId);
        return DTOMapper.INSTANCE.convertEntityToOrderGetDTO(order);
    }

    @GetMapping("/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OrderGetDTO getOrderById(@PathVariable("id") Long id) {
        Order order = orderService.getOrderById(id);
        return DTOMapper.INSTANCE.convertEntityToOrderGetDTO(order);
    }


}
