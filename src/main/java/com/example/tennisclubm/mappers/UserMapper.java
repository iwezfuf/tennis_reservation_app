package com.example.tennisclubm.mappers;

import com.example.tennisclubm.dto.UserDTOview;
import com.example.tennisclubm.dto.UserDTOcreate;
import com.example.tennisclubm.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTOview toDTO(User user);

    User toEntity(UserDTOview userDTOview);

    User toEntity(UserDTOcreate userDTOcreate);
}
