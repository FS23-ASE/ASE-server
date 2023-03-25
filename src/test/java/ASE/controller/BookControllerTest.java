package ASE.controller;

import ASE.entity.Book;
import ASE.rest.dto.BookPostDTO;
import ASE.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * BookControllerTest
 * This is a WebMvcTest which allows to test the BookController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the BookController works.
 */
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void givenBooks_whenGetBooks_thenReturnJsonArray() throws Exception {
        // given
        Book book = new Book();
        book.setName("bookname");

        List<Book> allBooks = Collections.singletonList(book);

        // this mocks the BookService -> we define above what the bookService should
        // return when getBooks() is called
        given(bookService.getBooks()).willReturn(allBooks);

        // when
        MockHttpServletRequestBuilder getRequest = get("/books").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(book.getName())));
    }

    @Test
    public void createBook_validInput_bookCreated() throws Exception {
        // given
        Book book = new Book();
        book.setId(1L);
        book.setName("testBookname");

        BookPostDTO bookPostDTO = new BookPostDTO();
        bookPostDTO.setName("testBookname");

        given(bookService.createBook(Mockito.any())).willReturn(book);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(book.getId().intValue())))
                .andExpect(jsonPath("$.name", is(book.getName())));
    }


    //@Test
//    public void updateBook_validInput_bookUpdated() throws Exception {
//        // given
//        Book existingBook = new Book();
//        existingBook.setId(1L);
//        existingBook.setBookname("testBookname");
//        existingBook.setToken("1");
//
//        Book updatedBook = new Book();
//        updatedBook.setId(1L);
//        updatedBook.setBookname("testBookname2");
//
//        given(bookService.getBookbyid(Mockito.anyLong())).willReturn(existingBook);
//
//        given(bookService.update(existingBook,updatedBook)).willReturn(existingBook);
//
//        // when/then -> do the request + validate the result
//        MockHttpServletRequestBuilder putRequest = put("/books/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(updatedBook));
//
//        // then
//        mockMvc.perform(putRequest)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").doesNotExist());
//    }

//    @Test
//    public void updateBook_invalidInput_bookNotFound() throws Exception {
//        // given
//        Book updatedBook = new Book();
//        updatedBook.setId(1L);
//        updatedBook.setName("testBookname2");
//
//        given(bookService.update(updatedBook, updatedBook))
//                .willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "The book does not exist."));
//
//        // when/then -> do the request + validate the result
//        MockHttpServletRequestBuilder putRequest = put("/books/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(updatedBook));
//
//        // then
//        mockMvc.perform(putRequest)
//                .andExpect(status().isNotFound());
//    }

    @Test
    public void getBook_validInput_bookReturned() throws Exception {
        // given
        Book book = new Book();
        book.setId(1L);
        book.setName("testBookname");

        given(bookService.getBookbyid(Mockito.anyLong())).willReturn(book);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder getRequest = get("/books/1")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(book.getId().intValue())))
                .andExpect(jsonPath("$.name", is(book.getName())));
    }

    @Test
    public void getBook_invalidInput_bookNotFound() throws Exception {
        // given
        given(bookService.getBookbyid(Mockito.anyLong())).willReturn(null);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder getRequest = get("/books/1")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isNotFound());
    }





    /**
     * Helper Method to convert bookPostDTO into a JSON string such that the input
     * can be processed
     * Input will look like this: {"name": "Test Book", "bookname": "testBookname"}
     *
     * @param object
     * @return string
     */
    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}
