package ASE.repository;

import ASE.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Long> {
  Book findById(long id);
  List<Book> findBySellerid(long sellerid);

  Book findByName(String name);

}
