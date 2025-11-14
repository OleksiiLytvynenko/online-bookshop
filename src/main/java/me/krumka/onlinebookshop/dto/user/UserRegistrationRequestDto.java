package me.krumka.onlinebookshop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import me.krumka.onlinebookshop.validation.FieldMatch;

@Data
@FieldMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class UserRegistrationRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    @NotBlank
    @Size(min = 6, max = 20)
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String shippingAddress;
}
