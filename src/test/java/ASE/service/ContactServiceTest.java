package ASE.service;

import ASE.entity.Book;
import ASE.entity.Contact;
import ASE.entity.Order;
import ASE.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class ContactServiceTest {
    private ContactService contactService;

    @Mock
    private ContactRepository contactRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        contactService = new ContactService(contactRepository);
    }

    /*
    @Test
    public void testGetContactByAccepter(){
        // given
        long accepter = 1L;
        Contact contact = new Contact();
        contact.setAccepter(accepter);
        when(contactRepository.findByAccepter(accepter)).thenReturn(contact);

        // when
        List<Contact> result=contactService.getContactByAccepter(accepter);

        // then
        assertEquals(1L, result.getAccepter());
        verify(contactRepository, times(1)).findByAccepter(accepter);
    }*/
}

