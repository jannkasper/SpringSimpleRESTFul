package jannkasper.spring.controllers;

import jannkasper.spring.api.v1.model.UserDTO;
import jannkasper.spring.api.v1.model.UserListDTO;
import jannkasper.spring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    public static final String BASE_URL = "/api/users";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserListDTO getListOfUsers() {
        return new UserListDTO(userService.getAllUsers());
    }

    @GetMapping({BASE_URL + "/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById (@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser (@RequestBody UserDTO userDTO){
        return userService.createNewUser(userDTO);
    }

    @PutMapping({BASE_URL + "/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser (@PathVariable Long id, @RequestBody UserDTO userDTO){
        return userService.saveUserByDTO(id, userDTO);
    }

    @PatchMapping({BASE_URL + "/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public UserDTO patchCustomer(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return null;
    }

    @DeleteMapping({BASE_URL + "/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
    }

}
