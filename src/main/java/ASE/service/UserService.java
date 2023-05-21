package ASE.service;

import ASE.entity.User;
import ASE.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
      this.userRepository = userRepository;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return the list of users
     */
    public List<User> getUsers() {
      return this.userRepository.findAll();
    }

    /**
     * Creates a new user.
     *
     * @param newUser  the user to create
     * @return the created user
     */
    public User createUser(User newUser) {
      newUser.setToken(UUID.randomUUID().toString());
      checkIfUserExists(newUser);
      newUser = userRepository.save(newUser);
      userRepository.flush();

      log.debug("Created Information for User: {}", newUser);
      return newUser;
    }

    /**
     * Performs user login.
     *
     * @param newUser  the user to perform login
     * @return the logged-in user
     */
    public User UserLogin(User newUser){

        return checkIfPasswordCorrects(newUser);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id  the ID of the user to retrieve
     * @return the retrieved user
     */
    public User getUserbyid(long id){
        return userRepository.findById(id);
    }

    /**
     * Updates a user's information.
     *
     * @param user       the original user object
     * @param userinput  the updated user object
     * @return the updated user
     */
    public User update(User user, User userinput){

        if(userinput.getUsername()!=null)
            user.setUsername(userinput.getUsername());
        if(userinput.getEmail()!=null)
            user.setEmail(userinput.getEmail());
        if(userinput.getPassword()!=null)
            user.setPassword(userinput.getPassword());
        if(userinput.getAddress()!=null)
            user.setAddress(userinput.getAddress());

        return user;
    }



    /**
     * This is a helper method that will check the uniqueness criteria of the
     * username and the name
     * defined in the User entity. The method will do nothing if the input is unique
     * and throw an error otherwise.
     *
     * @param userToBeCreated
     * @throws org.springframework.web.server.ResponseStatusException
     * @see User
     */
    private void checkIfUserExists(User userToBeCreated) {
      User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
      User userByName = userRepository.findByEmail(userToBeCreated.getEmail());

      String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created! If you already have an account, please log in.";
      if (userByUsername != null && userByName != null) {
        throw new ResponseStatusException(HttpStatus.CONFLICT,
            String.format(baseErrorMessage, "username and the email", "are"));
      } else if (userByUsername != null) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage, "username", "is"));
      } else if (userByName != null) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage, "email", "is"));
      }
    }

    /**
     * This is a helper method that checks if the password matches the user's stored password.
     * If the password does not match, an error is thrown.
     *
     * @param userToBeChecked  the user to be checked
     * @return the validated user
     */
    private User checkIfPasswordCorrects(User userToBeChecked){
        User userByUsername = userRepository.findByUsername(userToBeChecked.getUsername());
        if(userByUsername==null)
          {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unregistered User!");}
        else if(!userByUsername.getPassword().equals(userToBeChecked.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Password!");
        }
        return  userByUsername;
    }


}
