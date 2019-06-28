package jannkasper.spring.controllers;

import jannkasper.spring.api.v1.model.UserDTO;
import jannkasper.spring.domain.Status;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserResourceAssembler implements ResourceAssembler<UserDTO, Resource<UserDTO>> {

    @Override
    public Resource<UserDTO> toResource(UserDTO userDTO) {
        Resource<UserDTO> userResource = new Resource<>(userDTO,
                linkTo(methodOn(UserController.class).getUserResourceById(Long.valueOf(userDTO.getCustomerUrl().replaceAll("\\D+","")))).withSelfRel(),
                linkTo(methodOn(UserController.class).getListOfUsers()).withRel("users"));

        if(userDTO.getStatus()== Status.IN_PROGRESS) {
            userResource.add(linkTo(methodOn(UserController.class).remove(Long.valueOf(userDTO.getCustomerUrl().replaceAll("\\D+","")))).withRel("remove"));
            userResource.add(linkTo(methodOn(UserController.class).active(Long.valueOf(userDTO.getCustomerUrl().replaceAll("\\D+","")))).withRel("active")); }

        if(userDTO.getStatus()==Status.ACTIVE){
            userResource.add(linkTo(methodOn(UserController.class).remove(Long.valueOf(userDTO.getCustomerUrl().replaceAll("\\D+","")))).withRel("remove"));

        }

        return userResource;

    }
}
