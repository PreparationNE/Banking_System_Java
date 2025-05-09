package com.NE.Banking_System.controller;

import com.NE.Banking_System.dto.response.CustomerDashboardResponse;
import com.NE.Banking_System.dto.request.RegisterRequest;
import com.NE.Banking_System.dto.response.RegisterResponse;
import com.NE.Banking_System.dto.response.TransactionResponse;
import com.NE.Banking_System.entity.Customer;
import com.NE.Banking_System.entity.Message;
import com.NE.Banking_System.service.CustomerService;
import com.NE.Banking_System.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.NE.Banking_System.service.AuthenticationService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Management", description = "Customer related operations")
public class CustomerController {
    private final CustomerService customerService;
    private final TransactionService transactionService;
    private final AuthenticationService authenticationService;


    @Operation(
            summary = "Get customer by ID",
            description = "Retrieve customer details by customer ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer found"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    @GetMapping("/api/customers/{id}")
    public ResponseEntity<Customer> getCustomer(
            @Parameter(description = "ID of the customer to be retrieved") @PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @Operation(
            summary = "Get customer by account number",
            description = "Retrieve customer details by account number",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer found"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<Customer> getCustomerByAccount(
            @Parameter(description = "Account number of the customer") @PathVariable String accountNumber) {
        return ResponseEntity.ok(customerService.getCustomerByAccountNumber(accountNumber));
    }

    @Operation(
            summary = "Get customer dashboard",
            description = "Get dashboard information for a customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dashboard data retrieved"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    @GetMapping("/{customerId}/dashboard")
    public ResponseEntity<CustomerDashboardResponse> getCustomerDashboard(
            @Parameter(description = "ID of the customer") @PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerDashboard(customerId));
    }

    @Operation(
            summary = "Get customer transactions",
            description = "Get all transactions for a customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transactions retrieved"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    @GetMapping("/{customerId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getCustomerTransactions(
            @Parameter(description = "ID of the customer") @PathVariable Long customerId) {
        return ResponseEntity.ok(transactionService.getCustomerTransactions(customerId));
    }

    @Operation(
            summary = "Get customer messages",
            description = "Get all messages for a customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Messages retrieved"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    @GetMapping("/{customerId}/messages")
    public ResponseEntity<List<Message>> getCustomerMessages(
            @Parameter(description = "ID of the customer") @PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerMessages(customerId));
    }

    @Operation(
            summary = "Delete customer message",
            description = "Delete a specific message by its ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Message deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Message not found")
            }
    )
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Void> deleteCustomerMessage(
            @Parameter(description = "ID of the message to delete") @PathVariable Long messageId) {
        customerService.deleteCustomerMessage(messageId);
        return ResponseEntity.noContent().build();
    }
}