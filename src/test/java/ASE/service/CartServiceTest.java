package ASE.service;

import ASE.entity.Book;
import ASE.entity.Cart;
import ASE.repository.BookRepository;
import ASE.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    private CartService cartService;

    @Mock
    private CartRepository cartRepository;
    @Mock
    private BookRepository bookRepository;

    @Mock
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(cartRepository,bookRepository,orderService);
    }

    @Test
    public void testAddBookToCart(){
        Book book=new Book();
        book.setId(1L);
        book.setPrice(10);
        when(bookRepository.findById(1L)).thenReturn(book);
        Cart cart=new Cart();
        cart.setUserId(2L);
        List<Book> books=new ArrayList<>();
        cart.setBooks(books);
        cart.setPrices(0);

        when(cartRepository.findByUserId(2L)).thenReturn(cart);

        cartService.addBookToCart(2L,1L);

        assertEquals(cart.getQuantity(),1);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    public void testGetCartbyId(){
        long id = 1L;
        Cart cart=new Cart();
        cart.setId(id);
        when(cartRepository.findById(id)).thenReturn(cart);

        // when
        Cart result = cartService.getCartbyId(id);

        // then
        assertEquals(cart, result);
        verify(cartRepository, times(1)).findById(id);
    }

    @Test
    public void testGetCartByUserId(){
        long userid = 1L;
        Cart cart=new Cart();
        cart.setUserId(userid);
        when(cartRepository.findByUserId(userid)).thenReturn(cart);

        // when
        Cart result = cartService.getCartByUserId(userid);

        // then
        assertEquals(cart,result);
        verify(cartRepository, times(1)).findByUserId(userid);
    }

    @Test
    public void testDeleteBookFromCart(){
        Book book=new Book();
        book.setId(2L);
        Cart cart=new Cart();
        cart.setId(1L);
        List<Book> books=new ArrayList<>();
        books.add(book);
        cart.setBooks(books);
        when(cartRepository.findById(1L)).thenReturn(cart);

        cartService.deleteBookFromCart(1L,2L);

        assertEquals(cart.getBooks().size(),0);
        verify(cartRepository, times(1)).save(cart);

    }

    @Test
    public void testCreateCart(){
        Cart cart=new Cart();
        cart.setUserId(1L);
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result=cartService.createCart(cart);

        assertEquals(cart, result);
        verify(cartRepository, times(1)).save(cart);
        verify(cartRepository, times(1)).flush();
    }

    @Test
    public void testCheckoutCart(){
        Book book=new Book();
        book.setId(2L);
        book.setStatus(true);

        Cart cart=new Cart();
        cart.setUserId(1L);
        cart.setPrices(16);
        List<Book> books=new ArrayList<>();
        books.add(book);
        cart.setBooks(books);
        when(cartRepository.findByUserId(1L)).thenReturn(cart);

        Cart result=cartService.checkoutCart(1L);

        assertEquals(cart.getBooks().size(),0);
        assertEquals(cart.getPrices(),0);

    }


}
