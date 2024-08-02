package com.eventdate.msuserservice.model.recors;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @Email(message = "email format is not valid")
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password) {
}
