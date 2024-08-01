package com.eventdate.msuserservice.model.recors;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserDto(
        @NotNull(message = "Name is required.")
        @Size(max = 50, message = "Name must not exceed 50 characters.")
        String name,
        @NotNull(message = "Last name is required.")
        @Size(max = 50, message = "Last name must not exceed 50 characters.")
        String lastName,
        @Email(regexp = ".+[@].+[\\.].+",message = "email format is not valid")
        String email,
        @Pattern(regexp = "^[a-zA-Z0-9]{8}$", message = "password must have 8 characters combining numbers and letters")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+", message ="password should have at least one uppercase letter, one lowercase letter and one number")
        String password,
        LocalDate birthDate) {
}
