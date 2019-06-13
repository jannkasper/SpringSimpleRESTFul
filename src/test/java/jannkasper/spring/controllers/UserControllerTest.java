package jannkasper.spring.controllers;

import jannkasper.spring.api.v1.model.UserDTO;
import jannkasper.spring.services.ResourceNotFoundException;
import jannkasper.spring.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractRestControllerTest{

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getListOfUsers() throws Exception {
        UserDTO user1 = new UserDTO();
        user1.setLogin("Michael");
        user1.setPassword("michael1");
        user1.setEmail("michael@gmail.com");
        user1.setCustomerUrl(UserController.BASE_URL + "/1");

        UserDTO user2 = new UserDTO();
        user2.setLogin("Sam");
        user2.setPassword("sam2");
        user2.setEmail("sam@hotmail.com");
        user2.setCustomerUrl(UserController.BASE_URL + "/2");

        Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(user1,user2));


        mockMvc.perform(get(UserController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users",hasSize(2)));


    }

    @Test
    public void getUserById() throws Exception {
        UserDTO user1 = new UserDTO();
        user1.setLogin("Michael");
        user1.setPassword("michael1");
        user1.setEmail("michael@gmail.com");
        user1.setCustomerUrl(UserController.BASE_URL + "/1");

        Mockito.when(userService.getUserById(Mockito.anyLong())).thenReturn(user1);

        mockMvc.perform(get(UserController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login", equalTo("Michael")));
    }

    @Test
    public void createUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setLogin("Michael");
        user.setPassword("michael1");
        user.setEmail("michael@gmail.com");

        UserDTO returnDTO = new UserDTO();
        returnDTO.setLogin(user.getLogin());
        returnDTO.setPassword(user.getPassword());
        returnDTO.setEmail(user.getEmail());
        returnDTO.setCustomerUrl(UserController.BASE_URL + "/1");

        Mockito.when(userService.createNewUser(user)).thenReturn(returnDTO);

        mockMvc.perform(post(UserController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.login", equalTo("Michael")))
                .andExpect(jsonPath("$.customer_url", equalTo(UserController.BASE_URL + "/1")));
    }

    @Test
    public void updateUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setLogin("Michael");

        UserDTO returnDTO = new UserDTO();
        returnDTO.setLogin(user.getLogin());
        returnDTO.setPassword("michael1");
        returnDTO.setEmail("michael@gmail.com");
        returnDTO.setCustomerUrl(UserController.BASE_URL + "/1");

        Mockito.when(userService.saveUserByDTO(Mockito.anyLong(), Mockito.any(UserDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(UserController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login", equalTo("Michael")))
                .andExpect(jsonPath("$.password", equalTo("michael1")))
                .andExpect(jsonPath("$.email", equalTo("michael@gmail.com")))
                .andExpect(jsonPath("$.customer_url", equalTo(UserController.BASE_URL + "/1")));
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete(UserController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).deleteUserById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(userService.getUserById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(UserController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}