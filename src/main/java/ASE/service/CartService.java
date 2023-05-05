package ASE.service;


import ASE.entity.Book;
import ASE.entity.Cart;
import ASE.entity.Order;
import ASE.entity.User;
import ASE.repository.BookRepository;
import ASE.repository.CartRepository;
import ASE.rest.dto.CartGetDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CartService {
    private final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    private OrderService orderService;

    public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public Date date;

    @Autowired
    public CartService(@Qualifier("cartRepository") CartRepository cartRepository,
                       @Qualifier("bookRepository") BookRepository bookRepository,
                       @Qualifier("orderService") OrderService orderService) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.orderService= orderService;
    }


    public void addBookToCart(long userId, long bookId) {
        Cart cart = cartRepository.findByUserId(userId);
        Book book = bookRepository.findById(bookId);
        if(cart.getBooks().contains(book)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "The book is in the cart!");
        }
        cart.setPrices(cart.getPrices()+book.getPrice());
        cart.setQuantity(cart.getQuantity()+1);
        cart.getBooks().add(book);
        cartRepository.save(cart);
    }

    public Cart getCartbyId(long id) {
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
    public Cart checkoutCart(long userId) {
        Cart cart = getCartByUserId(userId);
        cart.setPrices(0);
        List<Book> books = cart.getBooks();
        for(Book book:books){
            book.setStatus(false);
        }
        System.out.println("checking out...");
        books.removeAll(books);
        return cart;
    }

    public void createOrder(long userId){
        Cart cart = cartRepository.findByUserId(userId);
        List<Long> books;
        for(Book book:cart.getBooks()){
            Order order=new Order();
            order.setBuyerId(userId);
            order.setStatus("PAID");
            order.setAmount(book.getPrice());
            order.setSellerId(book.getSellerid());
            books= new ArrayList<>();
            books.add(book.getId());
            order.setBooks(books);
            String format= dateFormat.format(new Date());
            order.setDate(format);
            orderService.createOrder(order);
        }
    }


}


