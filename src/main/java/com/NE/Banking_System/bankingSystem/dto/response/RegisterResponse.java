package com.NE.Banking_System.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response after successful registration")
public class RegisterResponse {
    @Schema(description = "Success message", example = "You have successfully created an account")
    private String message;

    @Schema(description = "JWT token for the registered user", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}