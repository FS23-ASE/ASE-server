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
import java.util.*;


/**
 * Cart Service
 * This class is the "worker" and responsible for all functionality related to
 * the cart
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class CartService {
    private final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    private final OrderService orderService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Autowired
    public CartService(@Qualifier("cartRepository") CartRepository cartRepository,
                       @Qualifier("bookRepository") BookRepository bookRepository,
                       @Qualifier("orderService") OrderService orderService) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.orderService= orderService;
    }

    /**
     * Adds a book to the user's cart.
     *
     * @param userId  the ID of the user
     * @param bookId  the ID of the book to add
     */
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

    /**
     * Retrieves a cart by its ID.
     *
     * @param id  the ID of the cart to retrieve
     * @return the retrieved cart
     */
    public Cart getCartbyId(long id) {
        Cart cart = cartRepository.findById(id);
        return cart;
    }


    /**
     * Retrieves the cart of a user.
     *
     * @param userId  the ID of the user
     * @return the user's cart
     */
    public Cart getCartByUserId(long userId) {
        return this.cartRepository.findByUserId(userId);
    }


    /**
     * Deletes a book from the cart.
     *
     * @param cartId   the ID of the cart
     * @param bookId   the ID of the book to delete
     */
    public void deleteBookFromCart(long cartId, long bookId) {
        Cart cart = cartRepository.findById(cartId);
        List<Book> books = cart.getBooks();
        books.removeIf(book -> book.getId().equals(bookId));
        cartRepository.save(cart);
    }


    /**
     * Creates a new cart.
     *
     * @param newCart  the cart to create
     * @return the created cart
     */
    public Cart createCart(Cart newCart) {
        newCart = cartRepository.save(newCart);
        cartRepository.flush();

        log.debug("Created Information for Cart: {}", newCart);
        return newCart;
    }

    /**
     * Checks out the user's cart.
     *
     * @param userId  the ID of the user
     * @return the checked out cart
     */
    public Cart checkoutCart(long userId) {
        Cart cart = getCartByUserId(userId);
        cart.setPrices(0);
        List<Book> books = cart.getBooks();
        for(Book book:books){
            book.setStatus(false);
        }
        System.out.println("checking out...");
        books.clear();
        return cart;
    }

    /**
     * Creates orders for the books in the user's cart.
     *
     * @param userId  the ID of the user
     */
    public void createOrder(long userId){
        Cart cart = cartRepository.findByUserId(userId);

        Map<Long, List<Book>> sellerBooksMap = new HashMap<>();

        for (Book book : cart.getBooks()) {
            sellerBooksMap.computeIfAbsent(book.getSellerid(), k -> new ArrayList<>()).add(book);
        }

        for (List<Book> sellerBooks : sellerBooksMap.values()) {
            Order order = new Order();
            order.setBuyerId(userId);
            order.setStatus("PAID");

            double totalAmount = 0.0;

            for (Book book : sellerBooks) {
                totalAmount += book.getPrice();
            }

            order.setAmount(totalAmount);
            order.setSellerId(sellerBooks.get(0).getSellerid());
            String format= dateFormat.format(new Date());
            order.setDate(format);
            order.setBooks(sellerBooks);

            Order newOrder = orderService.createOrder(order);

            // 可根据需要进一步处理新订单的逻辑
        }
    }


}


