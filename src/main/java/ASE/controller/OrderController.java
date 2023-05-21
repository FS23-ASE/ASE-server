package ASE.controller;

import ASE.entity.Book;

import ASE.entity.Order;

import ASE.rest.dto.*;
import ASE.rest.mapper.DTOMapper;

import ASE.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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
    public List<OrderGetDTO> getOrderByBuyerId(@PathVariable("buyerId") Long buyerId) {
        List<Order> orders = orderService.getOrderByBuyerId(buyerId);
        List<OrderGetDTO> orderGetDTOs = new ArrayList<>();

        for (Order  order : orders) {
            orderGetDTOs.add(DTOMapper.INSTANCE.convertEntityToOrderGetDTO(order));
        }
        return orderGetDTOs;
    }

    @GetMapping("/order/seller/{sellerId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderGetDTO> getOrderBySellerId(@PathVariable("sellerId") Long sellerId) {
        List<Order> orders = orderService.getOrderBySellerId(sellerId);
        List<OrderGetDTO> orderGetDTOs = new ArrayList<>();

        for (Order  order : orders) {
            orderGetDTOs.add(DTOMapper.INSTANCE.convertEntityToOrderGetDTO(order));
        }
        return orderGetDTOs;
    }

    @GetMapping("/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OrderGetDTO getOrderById(@PathVariable("id") Long id) {
        Order order = orderService.getOrderById(id);
        return DTOMapper.INSTANCE.convertEntityToOrderGetDTO(order);
    }

    @GetMapping("/order/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BookGetDTO> getBooks(@PathVariable("id") long id) {
        Order order = orderService.getOrderById(id);
        List<Book> books = order.getBooks();
        List<BookGetDTO> bookGetDTOs = new ArrayList<>();
        for (Book book : books) {
            System.out.println(book.getId());
            bookGetDTOs.add(DTOMapper.INSTANCE.convertEntityToBookGetDTO(book));
        }
        return bookGetDTOs;
    }

    @PutMapping("/order/received/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void receiveOrder(@PathVariable("id") Long id){
        Order order = orderService.getOrderById(id);
        System.out.println(order.getStatus());
        orderService.setStatus(id,"RECEIVED");
        System.out.println(order.getStatus());
    }

    @PutMapping("/order/shipped/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void shipOrder(@PathVariable("id") Long id){
        Order order = orderService.getOrderById(id);
        System.out.println(order.getStatus());
        orderService.setStatus(id,"SHIPPED");
        System.out.println(order.getStatus());
    }

    @PutMapping("/order/cancel/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void cancelOrder(@PathVariable("id") Long id){
        Order order = orderService.getOrderById(id);
        System.out.println(order.getStatus());
        orderService.setStatus(id,"CANCELLED");
        System.out.println(order.getStatus());
    }






}
