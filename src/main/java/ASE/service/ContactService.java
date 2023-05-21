package ASE.service;

import ASE.entity.Contact;

import ASE.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContactService {
    private final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(@Qualifier("contactRepository") ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getContactByAccepter(long accepter){return this.contactRepository.findByAccepter(accepter);}

    public Contact createContact(Contact newContact){

        newContact = contactRepository.save(newContact);

        contactRepository.flush();

        log.debug("Created Information for Contact: {}", newContact);
        return newContact;

    }

}
