package jannkasper.spring.services;

import jannkasper.spring.api.v1.mapper.UserMapper;
import jannkasper.spring.api.v1.mapper.UserMapperImpl;
import jannkasper.spring.api.v1.model.UserDTO;
import jannkasper.spring.domain.User;
import jannkasper.spring.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    UserMapper userMapper;

    UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userMapper = new UserMapperImpl();

        userService = new UserServiceImpl(userRepository, userMapper);

    }

    @Test
    public void getAllUsers() throws Exception {
        User user1 = new User();
        user1.setId(1l);
        user1.setLogin("Michael");
        user1.setPassword("michael1");
        user1.setEmail("michael@gmail.com");

        User user2 = new User();
        user2.setId(2l);
        user2.setLogin("Sam");
        user2.setPassword("sam2");
        user2.setEmail("sam@hotmail.com");

        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTO> userDTOList = userService.getAllUsers();

        assertEquals(2, userDTOList.size());
    }

    @Test
    public void getUserById() throws Exception{
        User user1 = new User();
        user1.setId(1l);
        user1.setLogin("Michael");
        user1.setPassword("michael1");
        user1.setEmail("michael@gmail.com");

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(user1));

        UserDTO userDTO = userService.getUserById(2L);

        assertEquals("Michael", userDTO.getLogin());


    }

    @Test
    public void createNewUser() throws Exception{
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("Michael");
        userDTO.setPassword("michael1");
        userDTO.setEmail("michael@gmail.com");

        User userSaved = new User();
        userSaved.setLogin(userDTO.getLogin());
        userSaved.setPassword(userDTO.getPassword());
        userSaved.setEmail(userDTO.getEmail());
        userSaved.setId(1l);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(userSaved);

        UserDTO savedDTO = userService.createNewUser(userDTO);

        assertEquals(userDTO.getLogin(), savedDTO.getLogin());
        assertEquals("/api/v1/customers/1", savedDTO.getCustomerUrl());
    }

    @Test
    public void saveUserByDTO() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("Michael");
        userDTO.setPassword("michael1");
        userDTO.setEmail("michael@gmail.com");

        User userSaved = new User();
        userSaved.setLogin(userDTO.getLogin());
        userSaved.setPassword(userDTO.getPassword());
        userSaved.setEmail(userDTO.getEmail());
        userSaved.setId(1l);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(userSaved);

        UserDTO savedDTO = userService.saveUserByDTO(1L, userDTO);

        assertEquals(userDTO.getLogin(), savedDTO.getLogin());
        assertEquals("/api/v1/customers/1", savedDTO.getCustomerUrl());
    }

    @Test
    public void patchUser() {
    }

    @Test
    public void deleteUserById() throws Exception{
    }
}