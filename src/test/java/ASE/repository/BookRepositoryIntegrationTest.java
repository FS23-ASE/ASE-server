package ASE.repository;

import ASE.entity.Book;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Nested
class BookRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;
    @Test
    void findByName_success() {
        // given
        Book book = new Book();
        book.setName("bookname");
        book.setAuthor("bookname");
        book.setSellerid(1);
        book.setPrice(11.1F);
        book.setPublisher("name");
        book.setStatus(TRUE);





        entityManager.persist(book);
        entityManager.flush();

        // when
        Book found = bookRepository.findByName(book.getName());

        // then
        assertNotNull(found.getId());
        assertEquals(found.getName(), book.getName());
        assertEquals(found.getStatus(), book.getStatus());
    }
}