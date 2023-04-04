package ASE.controller;

import ASE.entity.Book;
import ASE.entity.Cart;
import ASE.rest.dto.BookGetDTO;
import ASE.rest.dto.CartGetDTO;
import ASE.rest.mapper.DTOMapper;
import ASE.service.BookService;
import ASE.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CartController {
    private final CartService cartService;

    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public CartGetDTO getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return DTOMapper.INSTANCE.convertEntityToCartGetDTO(cart);
    }

    @PostMapping("/{userId}/{bookId}")
    public void addBookToCart(@PathVariable Long userId, @RequestBody Book book) {
        cartService.addBookToCart(userId, book);
    }

    @DeleteMapping("/{userId}/{bookId}")
    public void deleteBookFromCart(@PathVariable Long userId, @PathVariable Long bookId) {
        cartService.deleteBookFromCart(userId, bookId);
    }



}
