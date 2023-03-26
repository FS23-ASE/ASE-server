package ASE.service;

import ASE.entity.Book;
import ASE.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookService(@Qualifier("bookRepository") BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return this.bookRepository.findAll();
    }

    public Book createBook(Book newBook) {
        newBook = bookRepository.save(newBook);
        bookRepository.flush();

        log.debug("Created Information for Book: {}", newBook);
        return newBook;
    }

    public Book getBookbyid(long id) {
        Book book = bookRepository.findById(id);
        return book;
    }

    public List<Book> getBookBySeller(long sellerid) {
        return this.bookRepository.findBySellerid(sellerid);
    }

//  public Book update(Book book, Book bookinput){
//
//      if(bookinput.getStatus()!=null)
//          book.setStatus(bookinput.getStatus());
//      return book;
//  }


}
