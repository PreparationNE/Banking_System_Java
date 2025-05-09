package com.NE.Banking_System.controller;

import com.NE.Banking_System.dto.request.DepositRequest;
import com.NE.Banking_System.dto.response.TransactionResponse;
import com.NE.Banking_System.dto.request.TransferRequest;
import com.NE.Banking_System.dto.request.WithdrawRequest;
import com.NE.Banking_System.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Transaction Management", description = "Banking transaction operations")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(
            summary = "Deposit money",
            description = "Deposit money into a customer's account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Deposit successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@RequestBody DepositRequest request) {
        var transaction = transactionService.performDeposit(request.getCustomerId(), request.getAmount());
        return ResponseEntity.ok(transactionService.mapToDTO(transaction));
    }

    @Operation(
            summary = "Withdraw money",
            description = "Withdraw money from a customer's account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Withdrawal successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid input or insufficient funds"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody WithdrawRequest request) {
        var transaction = transactionService.performWithdrawal(request.getCustomerId(), request.getAmount());
        return ResponseEntity.ok(transactionService.mapToDTO(transaction));
    }

    @Operation(
            summary = "Transfer money",
            description = "Transfer money between accounts",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transfer successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid input or insufficient funds"),
                    @ApiResponse(responseCode = "404", description = "Customer/account not found")
            }
    )
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody TransferRequest request) {
        var transaction = transactionService.performTransfer(
                request.getFromCustomerId(),
                request.getToAccountNumber(),
                request.getAmount());
        return ResponseEntity.ok(transactionService.mapToDTO(transaction));
    }

    @Operation(
            summary = "Get customer transactions",
            description = "Get all transactions for a specific customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transactions retrieved"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TransactionResponse>> getCustomerTransactions(
            @Parameter(description = "ID of the customer") @PathVariable Long customerId) {
        var transactions = transactionService.getTransactionsByCustomerId(customerId);
        var dtos = transactions.stream()
                .map(transactionService::mapToDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}