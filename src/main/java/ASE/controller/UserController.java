package ASE.controller;

import ASE.entity.User;
import ASE.rest.dto.UserGetDTO;
import ASE.rest.dto.UserPostDTO;
import ASE.rest.mapper.DTOMapper;
import ASE.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ASE.repository.UserRepository;


import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserGetDTO> getAllUsers() {
        // fetch all users in the internal representation
        List<User> users = userService.getUsers();
        List<UserGetDTO> userGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (User user : users) {
            userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
        }
        return userGetDTOs;
    }


    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO getUser(@PathVariable("id") long id) {
        User user = userService.getUserbyid(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user!");
        }
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
    }


    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // create user
        User createdUser = userService.createUser(userInput);
        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
    }


    @PutMapping("/users/login")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User checkPassword(@RequestBody UserPostDTO userPostDTO) {
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        return userService.UserLogin(userInput);
    }


    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateUser(@PathVariable("id") long id, @RequestBody UserPostDTO userPostDTO) {

        User user = userService.getUserbyid(id);
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user!Please Login!");
        }
        else {
            userService.update(user, userInput);
        }
    }

}
