package jannkasper.spring.services;

import jannkasper.spring.api.v1.model.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO createNewUser(UserDTO userDTO);

    UserDTO saveUserByDTO(Long id, UserDTO userDTO);

    UserDTO patchUser(Long id, UserDTO userDTO);

    void deleteUserById(Long id);
}
