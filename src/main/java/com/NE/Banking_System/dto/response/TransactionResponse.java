package com.NE.Banking_System.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Transaction response data")
public class TransactionResponse {
    @Schema(description = "Transaction ID", example = "1")
    private Long id;

    @Schema(description = "Account number", example = "ACC123456")
    private String account;

    @Schema(description = "Transaction amount", example = "100.00")
    private Double amount;

    @Schema(description = "Transaction type", example = "SAVING")
    private String type;

    @Schema(description = "Transaction date and time", example = "2023-01-01T10:15:30")
    private LocalDateTime bankingDateTime;

    @Schema(description = "Customer name", example = "John Doe")
    private String customerName;

    @Schema(description = "Customer account number", example = "ACC123456")
    private String customerAccount;

    @Schema(description = "Customer balance after transaction", example = "1500.00")
    private Double customerBalance;
}