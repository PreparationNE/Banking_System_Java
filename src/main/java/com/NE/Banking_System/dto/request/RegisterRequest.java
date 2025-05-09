package com.NE.Banking_System.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request for registering a new customer")
public class RegisterRequest {
    @Schema(description = "Customer's first name", example = "John", required = true)
    private String firstName;

    @Schema(description = "Customer's last name", example = "Doe", required = true)
    private String lastName;

    @Schema(description = "Customer's email address", example = "john.doe@example.com", required = true)
    private String email;

    @Schema(description = "Customer's password", example = "securePassword123", required = true)
    private String password;

    @Schema(description = "Customer's mobile number", example = "0712345678", required = true)
    private String mobile;

    @Schema(description = "Customer's date of birth", example = "1990-01-01", required = true)
    private LocalDate dob;

    @Schema(description = "Initial account balance", example = "1000.00")
    private Double balance;
}