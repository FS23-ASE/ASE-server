package ASE.repository;

import ASE.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("contactRepository")
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Contact findById(long id);

    List<Contact> findBySender(long sender);

    List<Contact> findByAccepter(long accepter);
}