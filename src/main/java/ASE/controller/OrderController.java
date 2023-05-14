package ASE.controller;

import ASE.entity.Book;
import ASE.entity.Cart;
import ASE.entity.Order;
import ASE.rest.dto.*;
import ASE.rest.mapper.DTOMapper;
import ASE.service.CartService;
import ASE.service.OrderService;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
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

    @DeleteMapping("/order/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @PutMapping("/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateOrderStatus(@PathVariable Long id, @RequestParam(required = true) String status) {
        Order order = orderService.getOrderById(id);
        if(order == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find order!");
        }
        orderService.update(order,status);
    }


}
