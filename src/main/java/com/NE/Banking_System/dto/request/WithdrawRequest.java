package com.NE.Banking_System.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request for withdrawing money from an account")
public class WithdrawRequest {
    @Schema(description = "ID of the customer", example = "1", required = true)
    private Long customerId;

    @Schema(description = "Amount to withdraw", example = "50.00", required = true)
    private Double amount;
}