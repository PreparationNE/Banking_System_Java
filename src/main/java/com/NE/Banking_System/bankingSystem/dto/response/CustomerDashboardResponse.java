package com.NE.Banking_System.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response DTO for customer's dashboard information")
public class CustomerDashboardResponse {

    @Schema(description = "Full name of the customer", example = "John Doe")
    private String fullName;

    @Schema(description = "Customer's bank account number", example = "ACC123456789")
    private String accountNumber;

    @Schema(description = "Current balance in the account", example = "1000.50")
    private Double currentBalance;

    @Schema(description = "Date and time of the last update", example = "2024-12-31T10:15:30")
    private LocalDateTime lastUpdate;

    @Schema(description = "List of the customer's most recent transactions")
    private List<RecentTransaction> recentTransactions;

    @Schema(description = "List of the customer's most recent messages")
    private List<MessagePreview> recentMessages;

    @Data
    @Builder
    @Schema(description = "Details of a recent transaction")
    public static class RecentTransaction {
        @Schema(description = "Transaction ID", example = "101")
        private Long id;

        @Schema(description = "Transaction amount", example = "250.00")
        private Double amount;

        @Schema(description = "Transaction type", example = "SAVING or WITHDRAW", allowableValues = {"SAVING", "WITHDRAW", "TRANSFER"})
        private String type;

        @Schema(description = "Transaction timestamp", example = "2024-12-30T09:45:00")
        private LocalDateTime dateTime;
    }

    @Data
    @Builder
    @Schema(description = "Preview of a recent message")
    public static class MessagePreview {
        @Schema(description = "Message ID", example = "201")
        private Long id;

        @Schema(description = "Short preview of the message", example = "Your account was credited...")
        private String preview;

        @Schema(description = "Message timestamp", example = "2024-12-30T08:20:00")
        private LocalDateTime dateTime;
    }
}
