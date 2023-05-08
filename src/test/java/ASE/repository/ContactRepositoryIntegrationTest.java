package ASE.repository;

import ASE.entity.Cart;
import ASE.entity.Contact;
import ASE.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class ContactRepositoryIntegrationTest {
    @DataJpaTest
    public class OrderRepositoryIntegrationTest {
        @Autowired
        private TestEntityManager entityManager;

        @Autowired
        private ContactRepository contactRepository;


        @Test
        public void findByAccepter_success() {
            Contact contact = new Contact();
            contact.setAccepter(1L);
            entityManager.persist(contact);
            entityManager.flush();

            Contact found = contactRepository.findByAccepter(contact.getAccepter());

            assertNotNull(found.getId());
            assertEquals(found.getAccepter(), contact.getAccepter());
        }
    }
}
