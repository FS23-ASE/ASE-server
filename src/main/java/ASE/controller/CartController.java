package ASE.controller;

import ASE.entity.Book;
import ASE.entity.Cart;

import ASE.rest.dto.*;
import ASE.rest.mapper.DTOMapper;

import ASE.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Cart Controller
 * This class is responsible for handling all REST request that are related to
 * the cart.
 * The controller will receive the request and delegate the execution to the
 * CartService and finally return the result.
 */
@RestController
public class CartController {
    private final CartService cartService;

    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CartGetDTO getCartByUserId(@PathVariable("userId") Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return DTOMapper.INSTANCE.convertEntityToCartGetDTO(cart);
    }

    @PostMapping("/cart/{userId}/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void addBookToCart(@PathVariable("userId") Long userId, @PathVariable("bookId") Long bookId, @RequestBody Map<String, Object> requestBody) {
        Long userIdFromRequestBody = Long.parseLong(requestBody.get("userId").toString());
        Long bookIdFromRequestBody = Long.parseLong(requestBody.get("bookId").toString());
        cartService.addBookToCart(userIdFromRequestBody, bookIdFromRequestBody);
    }


    @DeleteMapping("/cart/{userId}/{bookId}")
    public void deleteBookFromCart(@PathVariable Long userId, @PathVariable Long bookId) {
        cartService.deleteBookFromCart(userId, bookId);
    }


    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CartGetDTO createCart(@RequestBody CartPostDTO cartPostDTO) {
        // convert API user to internal representation
        Cart cartInput = DTOMapper.INSTANCE.convertCartPostDTOtoEntity(cartPostDTO);

        // create cart
        Cart createdCart = cartService.createCart(cartInput);
        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToCartGetDTO(createdCart);
    }

    @PostMapping("/cart/order/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createOrder(@PathVariable("userId") Long userId) {cartService.createOrder(userId);}

    @GetMapping("/cart/books/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BookGetDTO> getBooks(@PathVariable("userId") long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        List<Book> books = cart.getBooks();
        List<BookGetDTO> bookGetDTOs = new ArrayList<>();
        for (Book book : books) {
            bookGetDTOs.add(DTOMapper.INSTANCE.convertEntityToBookGetDTO(book));
        }
        return bookGetDTOs;
    }

    @PutMapping("/cart/checkout/{id}")
    @ResponseBody
    public CartGetDTO checkoutCart(@PathVariable Long id) {
        // Call the cartService to check out the cart
        return DTOMapper.INSTANCE.convertEntityToCartGetDTO(cartService.checkoutCart(id));
    }

}
