package jannkasper.spring.controllers;

import jannkasper.spring.api.v1.model.UserDTO;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserResourceAssembler implements ResourceAssembler<UserDTO, Resource<UserDTO>> {

    @Override
    public Resource<UserDTO> toResource(UserDTO userDTO) {
        return new Resource<>(userDTO,
                linkTo(methodOn(UserController.class).getUserResourceById(Long.valueOf(userDTO.getCustomerUrl().replaceAll("\\D+","")))).withSelfRel(),
                linkTo(methodOn(UserController.class).getListOfUsers()).withRel("users"));

    }
}
