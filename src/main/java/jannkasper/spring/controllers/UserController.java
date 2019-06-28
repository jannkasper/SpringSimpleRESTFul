package jannkasper.spring.controllers;

import jannkasper.spring.api.v1.model.UserDTO;
import jannkasper.spring.domain.Status;
import jannkasper.spring.services.UserService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//  FIND ALL
//  curl -v localhost:8080/

//  FIND ONE - GET  /api/users/4
//  curl -v localhost:8080/api/users/4

//  FIND ONE  - GET  /api/users/99  [ERROR 404]
//  curl -v localhost:8080/api/users/99

//  SAVE - POST    -d {json}
//  curl -v -X POST localhost:8080/api/users/4 -H "Content-type:application/json" -d "{\"login\":\"Andrew\",\"password\":\"andrew\",\"email\":\"andrew@gmail.com\"}"

//  UPDATE - PATCH  /api/users/4
//  curl -v -X PATCH localhost:8080/api/users/4 -H "Content-type:application/json" -d "{\"login\":\"Andrew\",\"password\":\"andrew4\",\"email\":\"andrew@gmail.com\"}"

//  UPDATE - PATCH  /api/users/4    [ERROR 405]
//  curl -v -X PATCH localhost:8080/api/users/4 -H "Content-type:application/json" -d "{\"email\":\"andrew@hotmail.com\"}"

@RestController
public class UserController {

    public static final String BASE_URL = "/api/users";

    private final UserService userService;
    private final UserResourceAssembler assembler;

    public UserController(UserService userService, UserResourceAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    //    @GetMapping({"/users"})
//    @ResponseStatus(HttpStatus.OK)
//    public UserListDTO getListOfUsers() {
//        return new UserListDTO(userService.getAllUsers());
//    }

    @GetMapping({"/users"})
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<UserDTO>> getListOfUsers() {

        List<Resource<UserDTO>> users = userService.getAllUsers()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(users,linkTo(methodOn(UserController.class).getListOfUsers()).withSelfRel());
    }

//    @GetMapping({BASE_URL + "/{id}"})
//    @ResponseStatus(HttpStatus.OK)
//    public UserDTO getUserById (@PathVariable Long id) {
//        return userService.getUserById(id);
//    }

    @GetMapping({BASE_URL + "/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Resource<UserDTO> getUserResourceById (@PathVariable @Min(1) Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return assembler.toResource(userDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser (@Valid @RequestBody UserDTO userDTO){
        userDTO.setStatus(Status.IN_PROGRESS);
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

    @DeleteMapping({BASE_URL + "/{id}/remove"})
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<ResourceSupport> remove(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);

        if(userDTO.getStatus() != Status.REMOVE){
            userDTO.setStatus(Status.REMOVE);
            return ResponseEntity.ok(assembler.toResource(userService.saveUserByDTO(id, userDTO)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't remove a user that is in the " + userDTO.getStatus() + " status"));

    }

    @PutMapping({BASE_URL + "/{id}/active"})
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<ResourceSupport> active(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);

        if(userDTO.getStatus() == Status.IN_PROGRESS){
            userDTO.setStatus(Status.ACTIVE);
            return ResponseEntity.ok(assembler.toResource(userService.saveUserByDTO(id, userDTO)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't active a user that is in the " + userDTO.getStatus() + " status"));

    }


}
