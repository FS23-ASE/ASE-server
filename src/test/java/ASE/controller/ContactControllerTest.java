package ASE.controller;

import ASE.entity.Book;
import ASE.entity.Cart;
import ASE.entity.Contact;
import ASE.entity.Order;
import ASE.repository.OrderRepository;
import ASE.repository.ContactRepository;
import ASE.rest.dto.BookPostDTO;
import ASE.service.BookService;
import ASE.service.CartService;
import ASE.service.OrderService;
import ASE.service.ContactService;
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
@WebMvcTest(ContactController.class)
public class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;
    @MockBean
    private ContactRepository contactRepository;

    /*
    @Test
    public void testGetContactByAccepter()throws Exception{
        long accepter=1L;
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setAccepter(accepter);
        given(contactService.getContactByAccepter(Mockito.anyLong())).willReturn(contact);

        MockHttpServletRequestBuilder getRequest = get("/contactform/accepter/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(contact.getId().intValue())));
    }*/
}
