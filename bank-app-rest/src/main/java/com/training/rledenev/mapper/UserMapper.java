package com.training.rledenev.mapper;

import com.training.rledenev.dto.UserDto;
import com.training.rledenev.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToEntity(UserDto userDto);
}
