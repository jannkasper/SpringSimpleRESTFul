package jannkasper.spring.controllers;

import jannkasper.spring.api.v1.model.UserDTO;
import jannkasper.spring.api.v1.model.UserListDTO;
import jannkasper.spring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//  FIND ALL
//  curl -v localhost:8080/

//  FIND ONE - GET  /api/users/4
//  curl -v localhost:8080/api/users/4

//  FIND ONE  - GET  /api/users/99  [TEST 404]
//  curl -v localhost:8080/api/users/99

//  SAVE - POST    -d {json}
//  curl -v -X POST localhost:8080/api/users/4 -H "Content-type:application/json" -d "{\"login\":\"Andrew\",\"password\":\"andrew4\",\"email\":\"andrew@gmail.com\"}"

//  UPDATE - PATCH  /api/users/4
//  curl -v -X PATCH localhost:8080/api/users/4 -H "Content-type:application/json" -d "{\"login\":\"Andrew\",\"password\":\"andrew4\",\"email\":\"andrew@gmail.com\"}"

//  UPDATE - PATCH  /api/users/4    [TEST 405]
//  curl -v -X PATCH localhost:8080/api/users/4 -H "Content-type:application/json" -d "{\"email\":\"andrew@hotmail.com\"}"

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

//    @PatchMapping({BASE_URL + "/{id}"})
//    @ResponseStatus(HttpStatus.OK)
//    public UserDTO patchCustomer(@PathVariable Long id, @RequestBody UserDTO userDTO){
//        return userService.patchUser(id, userDTO);
//    }

    @PatchMapping({BASE_URL + "/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public UserDTO patchCustomer(@PathVariable Long id, @RequestBody Map<String, String> update){
        UserDTO userDTO = new UserDTO();
        String login = update.get("login");

        if (!StringUtils.isEmpty(login)) {
            userDTO.setLogin(login);
        } else {
            throw new UserUnSupportedFieldPathException(update.keySet());
        }

        String password = update.get("password");

        if (!StringUtils.isEmpty(password)) {
            userDTO.setPassword(password);
        }

        String email = update.get("email");

        if (!StringUtils.isEmpty(email)) {
            userDTO.setEmail(email);
        }

        return userService.patchUser(id, userDTO);
    }

    @DeleteMapping({BASE_URL + "/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
    }

}
