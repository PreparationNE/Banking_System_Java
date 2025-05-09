package com.NE.Banking_System.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request for transferring money between accounts")
public class TransferRequest {
    @Schema(description = "ID of the sender customer", example = "1", required = true)
    private Long fromCustomerId;

    @Schema(description = "Account number of the recipient", example = "ACC123456", required = true)
    private Long toAccountNumber;

    @Schema(description = "Amount to transfer", example = "50.00", required = true)
    private Double amount;
}