package jannkasper.spring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jannkasper.spring.customValidator.Password;
import jannkasper.spring.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotEmpty(message = "Please provide a login")
    private String login;

    @Password
    @NotEmpty(message = "Please provide a password")
    private String password;

    @NotEmpty(message = "Please provide an email")
    @Email
    private String email;
    private Status status;

    @JsonProperty("customer_url")
    private String customerUrl;
}
