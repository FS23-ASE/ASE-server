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
import java.util.UUID;

@Service
@Transactional
public class CartService {
    private final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CartService(@Qualifier("cartRepository") CartRepository cartRepository,
                       @Qualifier("bookRepository") BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }


    public void addBookToCart(long userId, long bookid) {
        Cart cart = cartRepository.findByUserId(userId);
        Book book = bookRepository.findById(bookid);
        cart.setPrices(cart.getPrices()+book.getPrice());
        cart.setQuantity(cart.getQuantity()+1);
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

    public Cart createCart(Cart newCart) {
        newCart = cartRepository.save(newCart);
        cartRepository.flush();

        log.debug("Created Information for Cart: {}", newCart);
        return newCart;
    }
    public void checkoutCart(long cartId) {
        Cart cart = cartRepository.findById(cartId);
        List<Book> books = cart.getBooks();
        for(Book book:books){
            book.setStatus(false);
        }

        books.removeAll(books);
    }

}


