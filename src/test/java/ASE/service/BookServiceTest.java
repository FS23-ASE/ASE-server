package ASE.service;

import ASE.entity.Book;
import ASE.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * The BookServiceTest class is responsible for testing the BookService functionality.
 */
public class BookServiceTest {

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository);
    }

    /**
     * Test case for retrieving books from the BookRepository and verifying the result.
     */
    @Test
    public void testGetBooks() {
        // given
        List<Book> books = new ArrayList<>();
        Book book1=new Book();
        book1.setSellerid(1L);
        Book book2=new Book();
        book2.setSellerid(1L);
        books.add(book1);
        books.add(book2);
        when(bookRepository.findAll()).thenReturn(books);

        // when
        List<Book> result = bookService.getBooks();

        // then
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    /**
     * Test case for creating a book in the BookRepository and verifying the result.
     */
    @Test
    public void testCreateBook() {
        // given
        Book newBook=new Book();
        newBook.setName("book");
        when(bookRepository.save(newBook)).thenReturn(newBook);

        // when
        Book result = bookService.createBook(newBook);

        // then
        assertEquals(newBook, result);
        verify(bookRepository, times(1)).save(newBook);
        verify(bookRepository, times(1)).flush();
    }

    /**
     * Test case for retrieving a book by its ID from the BookRepository and verifying the result.
     */
    @Test
    public void testGetBookById() {
        // given
        long id = 1L;
        Book book=new Book();
        book.setSellerid(id);
        when(bookRepository.findById(id)).thenReturn(book);

        // when
        Book result = bookService.getBookbyid(id);

        // then
        assertEquals(book, result);
        verify(bookRepository, times(1)).findById(id);
    }

    /**
     * Test case for validating the Book constructor.
     */
    @Test
    public void testBookConstructor() {
        String name = "The Great Gatsby";
        String author = "F. Scott Fitzgerald";
        String description = "A novel about the decadence of the Roaring Twenties.";
        String publisher = "Charles Scribner's Sons";
        Long sellerid = 123L;

        Book book = new Book(name, author, description, publisher, sellerid);

        assertEquals(name, book.getName());
        assertEquals(author, book.getAuthor());
        assertEquals(description, book.getDescription());
        assertEquals(publisher, book.getPublisher());
        assertEquals(sellerid, book.getSellerid());
    }
    @Test
    public void testUpdateBook() {
        // Set up
//        Long bookId = 1L;
//        Book existingBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", "A classic novel", "Scribner", 1L);
//        existingBook.setId(bookId);
//        Book updatedBook = new Book("To Kill a Mockingbird", "Harper Lee", "A Pulitzer Prize-winning novel", "J. B. Lippincott & Co.", 2L);
//        updatedBook.setImage("mock_image.jpg");
//
//        // Exercise
//        bookService.update(existingBook, updatedBook);
//
//        // Verify
//        verify(bookRepository, times(1)).save(existingBook);
//        assertEquals(existingBook.getName(), updatedBook.getName());
//        assertEquals(existingBook.getAuthor(), updatedBook.getAuthor());
//        assertEquals(existingBook.getDescription(), updatedBook.getDescription());
//        assertEquals(existingBook.getPublisher(), updatedBook.getPublisher());
//        assertEquals(existingBook.getSellerid(), updatedBook.getSellerid());
//        assertEquals(existingBook.getImage(), updatedBook.getImage());
    }

    @Test
    public void testGetBookBySeller() {
        // given
        long sellerid = 1L;
        List<Book> books = new ArrayList<>();
        Book book1=new Book();
        book1.setSellerid(sellerid);
        Book book2=new Book();
        book2.setSellerid(sellerid);
        books.add(book1);
        books.add(book2);
        when(bookRepository.findBySellerid(sellerid)).thenReturn(books);

        // when
        List<Book> result = bookService.getBookBySeller(sellerid);

        // then
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findBySellerid(sellerid);
    }
}