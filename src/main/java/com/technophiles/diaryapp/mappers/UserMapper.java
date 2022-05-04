package com.technophiles.diaryapp.mappers;

import com.technophiles.diaryapp.dtos.UserDTO;
import com.technophiles.diaryapp.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}
