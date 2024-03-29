package dev.notypie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserRegisterDto {

    @JsonProperty("userId")
    @NotBlank(message = "User name must require validate value.")
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    private String userId;

    @JsonProperty("userName")
    @NotBlank(message = "User name must require validate value.")
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    private String userName;

    @JsonProperty("email")
    @NotBlank(message = "valid email address must required.")
    @Email
    private String email;

    @JsonProperty("password")
    @Pattern(regexp = "^[a-zA-Z0-9_#@!]*$")
    private String password;

    @JsonProperty("phoneNumber")
    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "valid phone number must required.")
    private String phoneNumber;

    // Nullable.
    @JsonProperty("country")
    private String country;

    @JsonProperty("streetAddress")
    private String streetAddress;

    @JsonProperty("city")
    private String city;

    @JsonProperty("region")
    private String region;

    @JsonProperty("zipCode")
    private String zipCode;
}
