package ASE.repository;

import ASE.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class BookRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BookRepository bookRepository;
  @Test
  public void findByName_success() {
    // given
    Book book = new Book();
    book.setName("bookname");

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
