package com.NE.Banking_System.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Authentication request containing login credentials")
public class AuthenticationRequest {
    @Schema(description = "User's email address", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "User's password", example = "securePassword123", required = true)
    private String password;
}