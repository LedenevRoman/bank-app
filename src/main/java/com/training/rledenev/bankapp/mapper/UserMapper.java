package com.training.rledenev.bankapp.mapper;

import com.training.rledenev.bankapp.dto.UserDto;
import com.training.rledenev.bankapp.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToEntity(UserDto userDto);
}
