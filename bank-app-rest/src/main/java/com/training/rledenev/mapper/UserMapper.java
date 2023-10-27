package com.training.rledenev.mapper;

import com.training.rledenev.dto.UserDto;
import com.training.rledenev.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToEntity(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto mapToDto(User user);
}
