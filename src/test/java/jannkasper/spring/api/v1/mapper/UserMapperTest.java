package jannkasper.spring.api.v1.mapper;

import jannkasper.spring.api.v1.model.UserDTO;
import jannkasper.spring.domain.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserMapperTest {

    public static final  String LOGIN = "Megan";
    public static final String PASSWORD = "megan10";
    public static final String EMAIL = "megan@hotmail.com";
    UserMapper userMapper = new UserMapperImpl();
//    UserMapper userMapper = UserMapper.INSTANCE;

    @Test
    public void userToUserDTO() throws Exception {
        User user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);

        UserDTO userDTO = userMapper.userToUserDTO(user);

        assertEquals(LOGIN, userDTO.getLogin());
        assertEquals(PASSWORD, userDTO.getPassword());
        assertEquals(EMAIL, userDTO.getEmail());
    }

}