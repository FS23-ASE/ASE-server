package ASE.service;

import ASE.entity.User;
import ASE.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private User testUser2;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUsername");
        testUser.setPassword("testpassword");

        testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername");

        // when -> any object is being save in the userRepository -> return the dummy
        // testUser
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser2);

    }

    @Test
    public void createUser_validInputs_success() {
        // when -> any object is being save in the userRepository -> return the dummy
        // testUser
        User createdUser = userService.createUser(testUser);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testUser.getUsername(), createdUser.getUsername());
    }

    @Test
    public void createUser_duplicateInputs_throwsException() {
        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

        // then -> attempt to create second user with same user -> check that an error
        // is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
    }

    @Test
    void getUsers() {
        User createdUser = userService.createUser(testUser);
        User createdUser2 = userService.createUser(testUser2);
        List<User> users = new ArrayList<>();
        users.add(createdUser);
        users.add(createdUser2);
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getUsers();

        // then
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();

    }

    @Test
    void userLogin_validInputs_success() {
        // given
        String username = "testUsername";
        String password = "testPassword";
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(testUser);

        // when
        User loggedInUser = userService.UserLogin(testUser);

        // then
        assertEquals(testUser.getId(), loggedInUser.getId());
        assertEquals(testUser.getUsername(), loggedInUser.getUsername());
        assertEquals(testUser.getToken(), loggedInUser.getToken());
    }


    @Test
    public void testGetUserById() {
        // given
        long id = 1L;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(user);

        // when
        User result = userService.getUserbyid(id);

        // then
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testUpdateUser() {
        // create a User object to update
        User user = new User();
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");

        // create a User object with updated data
        User userToUpdate = new User();
        userToUpdate.setUsername("janedoe");
        userToUpdate.setEmail("janedoe@example.com");

        // call the update method
        User updatedUser = userService.update(user, userToUpdate);

        // check that the updated User object has the correct values
        assertEquals(updatedUser.getUsername(), "janedoe");
        assertEquals(updatedUser.getEmail(), "janedoe@example.com");
    }
}
