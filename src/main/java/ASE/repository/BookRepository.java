package ASE.repository;

import ASE.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The BookRepository interface is responsible for managing the persistence and retrieval of Book entities.
 */
@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book
     * @return the book with the specified ID
     */
    Book findById(long id);

    /**
     * Retrieves a list of books by the seller ID.
     *
     * @param sellerId the ID of the seller
     * @return a list of books associated with the seller ID
     */
    List<Book> findBySellerid(long sellerId);

    /**
     * Retrieves a book by its name.
     *
     * @param name the name of the book
     * @return the book with the specified name
     */
    Book findByName(String name);
}
