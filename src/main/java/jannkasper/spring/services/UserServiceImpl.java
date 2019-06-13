package jannkasper.spring.services;

import jannkasper.spring.api.v1.mapper.UserMapper;
import jannkasper.spring.api.v1.model.UserDTO;
import jannkasper.spring.controllers.UserController;
import jannkasper.spring.domain.User;
import jannkasper.spring.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> {
                    UserDTO userDTO = userMapper.userToUserDTO(user);
                    userDTO.setCustomerUrl(getCustomerUrl(user.getId()));
                    return userDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository
                .findById(id)
                .map(userMapper::userToUserDTO)
                .map(userDTO -> {
                    userDTO.setCustomerUrl(getCustomerUrl(id));
                    return userDTO;
                })
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public UserDTO createNewUser(UserDTO userDTO) {
        return saveAndReturnDTO(userMapper.userDtoToUser(userDTO));
    }

    private UserDTO saveAndReturnDTO(User user){
        User savedUser = userRepository.save(user);

        UserDTO returnDTO = userMapper.userToUserDTO(savedUser);

        returnDTO.setCustomerUrl(getCustomerUrl(savedUser.getId()));

        return returnDTO;
    }

    @Override
    public UserDTO saveUserByDTO(Long id, UserDTO userDTO) {
        User user = userMapper.userDtoToUser(userDTO);
        user.setId(id);

        return saveAndReturnDTO(user);
    }

    @Override
    public UserDTO patchUser(Long id, UserDTO userDTO) {
        return userRepository
                .findById(id)
                .map(user -> {
                    if(userDTO.getLogin() != null){
                        user.setLogin(userDTO.getLogin());
                    }
                    if(userDTO.getPassword() != null){
                        user.setPassword(userDTO.getPassword());
                    }
                    if(userDTO.getEmail() != null){
                        user.setEmail(userDTO.getEmail());
                    }
                    UserDTO returnDTO = userMapper.userToUserDTO(userRepository.save(user));
                    returnDTO.setCustomerUrl(getCustomerUrl(user.getId()));

                    return userDTO;
                })
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);

    }

    private String getCustomerUrl(Long id) {
        return UserController.BASE_URL + "/" + id;
    }
}
