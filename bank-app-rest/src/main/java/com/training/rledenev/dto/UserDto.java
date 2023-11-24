package com.training.rledenev.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Getter
@Setter
public class UserDto {
    @NotBlank(message = "First name can not be empty")
    @Pattern(regexp = "[A-Za-z]*", message = "First name must contain only letters of the English alphabet")
    private String firstName;

    @NotBlank(message = "Last name can not be empty")
    @Pattern(regexp = "[A-Za-z]*", message = "Last name must contain only letters of the English alphabet")
    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "[A-Za-z0-9\\s.,\\-'/\\\\]+", message = "Address contains invalid characters")
    private String address;

    @Pattern(regexp = "^\\+\\d{1,3}-?\\d{3,14}$", message = "The phone number must starts with '+', "
            + "and contain only numbers and hyphens")
    private String phone;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct})[a-zA-Z\\d\\p{Punct}]*$",
            message = "The password is incorrect. Password is required to contain only English alphabet characters "
            + "at least one uppercase and one lowercase, also one digit and "
            + "one special character")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(firstName, userDto.firstName)
                && Objects.equals(lastName, userDto.lastName)
                && Objects.equals(email, userDto.email)
                && Objects.equals(address, userDto.address)
                && Objects.equals(phone, userDto.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, address, phone);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
