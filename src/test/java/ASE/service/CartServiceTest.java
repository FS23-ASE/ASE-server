package ASE.service;

import ASE.entity.Book;
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

    /*@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(cartRepository,bookRepository);
    }*/

    @Test
    public void testAddBookToCart(){
//        Book newBook=new Book();
//        newBook.setName("book");
//        long bookid=newBook.getId();

    }

    @Test
    public void testGetCartByUserId(){

    }

    @Test
    public void testDeleteBookFromCart(){

    }
}
