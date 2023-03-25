package ASE.controller;

import ASE.entity.User;
import ASE.rest.dto.UserPostDTO;
import ASE.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        // given
        User user = new User();
        user.setUsername("firstname@lastname");

        List<User> allUsers = Collections.singletonList(user);

        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(userService.getUsers()).willReturn(allUsers);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())));
    }

    @Test
    public void createUser_validInput_userCreated() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setToken("1");

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUsername("testUsername");

        given(userService.createUser(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    public void createUser_invalidInput_usernameTaken() throws Exception {
        // given
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUsername("testUsername");

        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setUsername("testUsername");
        createdUser.setToken("1");

        // given
        UserPostDTO duplicateUserPostDTO = new UserPostDTO();
        duplicateUserPostDTO.setUsername("testUsername");

        given(userService.createUser(Mockito.any())).willThrow(
                new ResponseStatusException(HttpStatus.CONFLICT, "The username already exists."));

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postDuplicateUserRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(duplicateUserPostDTO));

        // then
        mockMvc.perform(postDuplicateUserRequest)
                .andExpect(status().isConflict());
    }

    @Test
    public void updateUser_validInput_userUpdated() throws Exception {
        // given
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("testUsername");
        existingUser.setToken("1");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("testUsername2");

        given(userService.getUserbyid(Mockito.anyLong())).willReturn(existingUser);

        given(userService.update(existingUser,updatedUser)).willReturn(existingUser);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedUser));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void updateUser_invalidInput_userNotFound() throws Exception {
        // given
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("testUsername2");

        given(userService.update(updatedUser, updatedUser))
                .willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "The user does not exist."));

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedUser));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUser_validInput_userReturned() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setToken("1");

        given(userService.getUserbyid(Mockito.anyLong())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder getRequest = get("/users/1")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    public void getUser_invalidInput_userNotFound() throws Exception {
        // given
        given(userService.getUserbyid(Mockito.anyLong())).willReturn(null);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder getRequest = get("/users/1")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isNotFound());
    }





    /**
     * Helper Method to convert userPostDTO into a JSON string such that the input
     * can be processed
     * Input will look like this: {"name": "Test User", "username": "testUsername"}
     *
     * @param object
     * @return string
     */
    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}
