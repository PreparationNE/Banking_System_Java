package com.NE.Banking_System.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "Request for depositing money into an account")
public class DepositRequest {
    @Schema(description = "ID of the customer", example = "1", required = true)
    private Long customerId;

    @Schema(description = "Amount to deposit", example = "100.50", required = true)
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Amount must be positive with up to 2 decimal places")
    private Double amount;
}