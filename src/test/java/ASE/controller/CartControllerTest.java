package ASE.controller;

import ASE.entity.Book;
import ASE.entity.Cart;
import ASE.rest.dto.BookPostDTO;
import ASE.rest.dto.CartPostDTO;
import ASE.service.BookService;
import ASE.service.CartService;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * CartControllerTest
 * This is a WebMvcTest which allows to test the CartController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the CartController works.
 */
@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    void testGetCartByUserId() throws Exception {
        // Set up
        long userId = 1L;
        Cart cart1=new Cart();
        cart1.setUserId(userId);

        given(cartService.getCartByUserId(Mockito.anyLong())).willReturn(cart1);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder getRequest = get("/cart/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is((cart1.getUserId().intValue()))));


    }


    @Test
    void testCreateCart() throws Exception{

        Cart cart=new Cart();
        cart.setId(1L);
        cart.setUserId(2L);

        CartPostDTO cartPostDTO=new CartPostDTO();
        cartPostDTO.setUserId(2L);

        given(cartService.createCart(Mockito.any())).willReturn(cart);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cartPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(cart.getId().intValue())))
                .andExpect(jsonPath("$.userId", is(cart.getUserId().intValue())));
    }

    @Test
    void testGetBooks() throws Exception{
        Cart cart=new Cart();
        cart.setUserId(2L);

        Book book=new Book();
        book.setId(1L);
        List<Book> books=new ArrayList<>();
        books.add(book);
        cart.setBooks(books);

        given(cartService.getCartByUserId(Mockito.anyLong())).willReturn(cart);

        MockHttpServletRequestBuilder getRequest = get("/cart/books/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is((cart.getBooks().get(0).getId().intValue()))));
    }



    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}
