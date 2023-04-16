package ASE.controller;

import ASE.entity.Book;
import ASE.entity.Cart;
import ASE.entity.User;
import ASE.rest.dto.*;
import ASE.rest.mapper.DTOMapper;
import ASE.service.BookService;
import ASE.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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
    public void addBookToCart(@PathVariable("userId") Long userId,@PathVariable("bookId") Long bookId) {
        cartService.addBookToCart(userId, bookId);
    }

    @DeleteMapping("/cart/{userId}/{bookId}")
    public void deleteBookFromCart(@PathVariable Long userId, @PathVariable Long bookId) {
        cartService.deleteBookFromCart(userId, bookId);
    }


    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CartGetDTO createUser(@RequestBody CartPostDTO cartPostDTO) {
        // convert API user to internal representation
        Cart cartInput = DTOMapper.INSTANCE.convertCartPostDTOtoEntity(cartPostDTO);

        // create cart
        Cart createdCart = cartService.createCart(cartInput);
        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToCartGetDTO(createdCart);
    }

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
    public void checkoutCart(@PathVariable Long id) {
        // Call the cartService to check out the cart
        cartService.checkoutCart(id);
    }

}
