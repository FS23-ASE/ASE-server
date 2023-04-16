package ASE.controller;

import ASE.entity.Book;
import ASE.rest.dto.BookGetDTO;
import ASE.rest.dto.BookPostDTO;
import ASE.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        book.setAuthor("bookname");



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

    @Test
    public void testGetBookBySeller() throws Exception {
        // Set up
        long seller_id = 1L;
        Book book1 = new Book();
        book1.setName("book1");
        book1.setSellerid(seller_id);
        Book book2 = new Book();
        book2.setName("book2");
        book2.setSellerid(seller_id);
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        given(bookService.getBookBySeller(Mockito.anyLong())).willReturn(books);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder getRequest = get("/books/seller/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(book1.getName())));


    }
    @Test
    public void testUpdateBook() throws Exception {
// Set up
        Long bookId = 1L;
        Book book = new Book();
        book.setName("Book Name");
        book.setAuthor("Book Author");
        book.setPublisher("Book Publisher");
        book.setPrice(10.0F);
        book.setDescription("Book Description");
        book.setSellerid(1L);
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "Test image content".getBytes());

        given(bookService.getBookbyid(Mockito.anyLong())).willReturn(book);

// when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = MockMvcRequestBuilders.put("/books/" + bookId)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("image", image.getBytes().toString())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookImage() throws Exception {
        // Set up
        long bookId = 1L;
        String fileName = "book1.jpg";
        String uploadDir = "book-photos/" + bookId;
        String imagePath = uploadDir + "/" + fileName;

        // Create a mock book with a valid image file name
        Book book = new Book();
        book.setId(bookId);
        book.setImage(fileName);
        given(bookService.getBookbyid(bookId)).willReturn(book);

        // Create a mock image file
        File mockImage = new File(imagePath);
        if (!mockImage.exists()) {
            mockImage.getParentFile().mkdirs();
            mockImage.createNewFile();
        }

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder getRequest = get("/books/1/image")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\""));

        // Clean up
        mockImage.delete();
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