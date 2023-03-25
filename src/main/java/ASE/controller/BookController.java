package ASE.controller;

import ASE.entity.Book;
import ASE.rest.dto.BookGetDTO;
import ASE.rest.dto.BookPostDTO;
import ASE.rest.mapper.DTOMapper;
import ASE.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

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
    public BookGetDTO createBook(@RequestBody BookPostDTO bookPostDTO) {
        // convert API book to internal representation
        Book bookInput = DTOMapper.INSTANCE.convertBookPostDTOtoEntity(bookPostDTO);

        // create book
        Book createdBook = bookService.createBook(bookInput);
        // convert internal representation of book back to API
        return DTOMapper.INSTANCE.convertEntityToBookGetDTO(createdBook);
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





}
