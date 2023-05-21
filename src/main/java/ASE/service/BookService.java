package ASE.service;

import ASE.entity.Book;
import ASE.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;



/**
 * Book Service
 * This class is the "worker" and responsible for all functionality related to
 * the book
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookService(@Qualifier("bookRepository") BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books.
     *
     * @return the list of all books
     */
    public List<Book> getBooks() {
        return this.bookRepository.findAll();
    }

    /**
     * Creates a new book.
     *
     * @param newBook the book to be created
     * @return the created book
     */
    public Book createBook(Book newBook) {
        newBook.setStatus(true);
        newBook = bookRepository.save(newBook);
        bookRepository.flush();

        log.debug("Created Information for Book: {}", newBook);
        return newBook;
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return the retrieved book
     */
    public Book getBookbyid(long id) {
        Book book = bookRepository.findById(id);
        return book;
    }

    /**
     * Retrieves books by the seller ID.
     *
     * @param sellerid the ID of the seller
     * @return the list of books belonging to the seller
     */
    public List<Book> getBookBySeller(long sellerid) {
        return this.bookRepository.findBySellerid(sellerid);
    }

    /**
     * Updates a book with new information.
     *
     * @param book       the book to update
     * @param bookinput  the updated book information
     */
    public void update(Book book, Book bookinput) {
        if (bookinput.getImage() != null) {
            String updateImage = bookinput.getImage();
            book.setImage(bookinput.getImage());
        }
        bookRepository.save(book);
    }
}

