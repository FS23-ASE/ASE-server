package ASE.controller;

import ASE.entity.User;
import ASE.entity.Order;
import ASE.entity.Contact;
import ASE.rest.dto.UserGetDTO;
import ASE.rest.dto.UserPostDTO;
import ASE.rest.dto.OrderGetDTO;
import ASE.rest.dto.OrderPostDTO;
import ASE.rest.dto.ContactGetDTO;
import ASE.rest.dto.ContactPostDTO;
import ASE.rest.mapper.DTOMapper;
import ASE.service.CartService;
import ASE.service.OrderService;
import ASE.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
public class ContactController {

    private final ContactService contactService;

    ContactController(ContactService contactService){this.contactService = contactService;};

    @PostMapping("/contactform")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ContactGetDTO createContact(@RequestBody ContactPostDTO contactPostDTO) {
        // convert API user to internal representation
        Contact contactInput = DTOMapper.INSTANCE.convertContactPostDTOtoEntity(contactPostDTO);

        // create cart
        Contact createdContact = contactService.createContact(contactInput);
        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToContactGetDTO(createdContact);
    }

    @GetMapping("/contactform/accepter/{accepter}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ContactGetDTO getContactByAccepter(@PathVariable("accepter") Long accepter) {
        Contact contact = contactService.getContactByAccepter(accepter);
        return DTOMapper.INSTANCE.convertEntityToContactGetDTO(contact);
    }
}
