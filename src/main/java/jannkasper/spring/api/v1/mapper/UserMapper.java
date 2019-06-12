package jannkasper.spring.api.v1.mapper;

import jannkasper.spring.api.v1.model.UserDTO;
import jannkasper.spring.domain.User;
import org.mapstruct.MapperConfig;

@MapperConfig
public interface UserMapper {

//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO (User user);

    User userDtoToUser (UserDTO userDTO);
}
