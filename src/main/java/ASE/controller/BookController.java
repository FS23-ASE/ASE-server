package ASE.controller;

import ASE.entity.Book;
import ASE.entity.User;
import ASE.rest.dto.BookGetDTO;
import ASE.rest.dto.BookPostDTO;
import ASE.rest.dto.UserGetDTO;
import ASE.rest.mapper.DTOMapper;
import ASE.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;


@RestController
public class BookController {

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }
    private final Logger log = LoggerFactory.getLogger(BookService.class);

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BookGetDTO> getAllBooks() {
        // fetch all books in the internal representation
        List<Book> books = bookService.getBooks();
        List<BookGetDTO> bookGetDTOs = new ArrayList<>();

        // convert each book to the API representation
        for (Book book : books) {
            bookGetDTOs.add(DTOMapper.INSTANCE.convertEntityToBookGetDTO(book));
        }
        return bookGetDTOs;
    }


    @GetMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BookGetDTO getBook(@PathVariable("id") long id) {
        Book book = bookService.getBookbyid(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find book!");
        }
        return DTOMapper.INSTANCE.convertEntityToBookGetDTO(book);
    }


    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BookGetDTO createBook(@RequestBody BookPostDTO bookPostDTO) throws SQLException {
        // convert API book to internal representation
        Book bookInput = DTOMapper.INSTANCE.convertBookPostDTOtoEntity(bookPostDTO);
        log.debug("Created Information for: {}", bookInput);
//
//        byte[] bytes = Base64.getDecoder().decode(bookPostDTO.getImage());
//        Blob imageBlob = new SerialBlob(bytes);
//
//        bookInput.setImage(imageBlob);

        Book createdBook = bookService.createBook(bookInput);

        // convert internal representation of book back to API
        return DTOMapper.INSTANCE.convertEntityToBookGetDTO(createdBook);
    }


//    @PostMapping("/books")
//    @ResponseStatus(HttpStatus.CREATED)
//    @ResponseBody
//    public BookGetDTO createBook(@RequestParam("name") String name,
//                                 @RequestParam("author") String author,
//                                 @RequestParam("description") String description,
//                                 @RequestParam("publisher") String publisher,
//                                 @RequestParam("sellerid") Long sellerid,
//                                 @RequestParam("image") MultipartFile image) throws SQLException, IOException {
//        // convert API book to internal representation
//        if (true) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find book!");
//        }
//        Book bookInput = new Book(name, author, description, publisher, sellerid);
//        log.debug("Created Information for User: {}", bookInput);
//
//        byte[] bytes = image.getBytes();
//        Blob imageBlob = new SerialBlob(bytes);
//        bookInput.setImage(imageBlob);
//
//        Book createdBook = bookService.createBook(bookInput);
//        // convert internal representation of book back to API
//        return DTOMapper.INSTANCE.convertEntityToBookGetDTO(createdBook);
//    }
    @GetMapping("/books/seller/{sellerid}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BookGetDTO> getBookBySeller(@PathVariable("sellerid") long seller_id) {
        List<Book> books = bookService.getBookBySeller(seller_id);
        List<BookGetDTO> bookGetDTOs = new ArrayList<>();

        for (Book book : books) {
            bookGetDTOs.add(DTOMapper.INSTANCE.convertEntityToBookGetDTO(book));
        }
        return bookGetDTOs;
    }


    public Blob convertBase64ToBlob(String base64String) throws SQLException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        Blob blob = null;
        try {
            blob = new javax.sql.rowset.serial.SerialBlob(decodedBytes);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return blob;
    }


//    @PutMapping("/books/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public void updateBook(@PathVariable("id") long id, @RequestBody BookPostDTO bookPostDTO) {
//
//        Book book = bookService.getBookbyid(id);
//        Book bookInput = DTOMapper.INSTANCE.convertBookPostDTOtoEntity(bookPostDTO);
//        if (book == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find book!");
//        }
//        else {
//            bookService.update(book, bookInput);
//        }
//    }

    public void excep(String x) {
        if (true) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, x);
        }
    }


}
