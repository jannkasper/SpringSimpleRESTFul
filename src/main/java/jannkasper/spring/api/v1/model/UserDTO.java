package jannkasper.spring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String login;
    private String password;
    private String email;

    @JsonProperty("customer_url")
    private String customerUrl;
}