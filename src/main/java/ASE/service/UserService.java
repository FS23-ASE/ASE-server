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

  public List<User> getUsers() {
    return this.userRepository.findAll();
  }

  public User createUser(User newUser) {
    newUser.setToken(UUID.randomUUID().toString());
    checkIfUserExists(newUser);
    // saves the given entity but data is only persisted in the database once
    // flush() is called
    newUser = userRepository.save(newUser);
    userRepository.flush();

    log.debug("Created Information for User: {}", newUser);
    return newUser;
  }

  public User UserLogin(User newUser){

      return checkIfPasswordCorrects(newUser);
  }

  public User getUserbyid(long id){
      User user=userRepository.findById(id);
      return user;
  }

  public User update(User user, User userinput){

      if(userinput.getUsername()!=null)
          user.setUsername(userinput.getUsername());
      if(userinput.getEmail()!=null)
          user.setEmail(userinput.getEmail());
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
