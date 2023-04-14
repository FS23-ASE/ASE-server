package ASE.service;


import ASE.entity.Book;
import ASE.entity.Cart;
import ASE.entity.User;
import ASE.repository.BookRepository;
import ASE.repository.CartRepository;
import ASE.rest.dto.CartGetDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {
    private final Logger log = LoggerFactory.getLogger(BookService.class);

    private final CartRepository cartRepository;

    @Autowired
    public CartService(@Qualifier("cartRepository") CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Book> getBooks(long userId) {
        return this.cartRepository.findByUserId(userId).getBooks();
    }

    public void addBookToCart(long cartId, Book book) {
        Cart cart = cartRepository.findById(cartId);
        cart.getBooks().add(book);
        cartRepository.save(cart);
    }

    public Cart getCartbyid(long id) {
        Cart cart = cartRepository.findById(id);
        return cart;
    }

    public Cart getCartByUserId(long userId) {
        return this.cartRepository.findByUserId(userId);
    }

    public void deleteBookFromCart(long cartId, long bookId) {
        Cart cart = cartRepository.findById(cartId);
        List<Book> books = cart.getBooks();
        books.removeIf(book -> book.getId().equals(bookId));
        cartRepository.save(cart);
    }

}


