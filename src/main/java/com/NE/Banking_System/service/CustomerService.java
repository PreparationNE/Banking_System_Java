package com.NE.Banking_System.service;

import com.NE.Banking_System.dto.response.CustomerDashboardResponse;
import com.NE.Banking_System.entity.BankingTransaction;
import com.NE.Banking_System.entity.Customer;
import com.NE.Banking_System.entity.Message;
import com.NE.Banking_System.repository.CustomerRepository;
import com.NE.Banking_System.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final EmailService emailService;
    private final MessageRepository messageRepository;
    private final TransactionService transactionService; // Add this dependency


    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer getCustomerByAccountNumber(String accountNumber) {
        return customerRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public CustomerDashboardResponse getCustomerDashboard(Long customerId) {
        Customer customer = getCustomerById(customerId);

        // Get last 5 transactions - using instance transactionService
        List<BankingTransaction> transactions = transactionService.getTransactionsByCustomerId(customerId)
                .stream()
                .limit(5)
                .toList();

        // Get last 3 messages
        List<Message> messages = messageRepository.findByCustomerId(customerId)
                .stream()
                .limit(3)
                .toList();

        return CustomerDashboardResponse.builder()
                .fullName(customer.getFirstName() + " " + customer.getLastName())
                .accountNumber(customer.getAccountNumber())
                .currentBalance(customer.getBalance())
                .lastUpdate(customer.getLastUpdateDateTime())
                .recentTransactions(transactions.stream()
                        .map(t -> CustomerDashboardResponse.RecentTransaction.builder()
                                .id(t.getId())
                                .amount(t.getAmount())
                                .type(t.getType().name())
                                .dateTime(t.getBankingDateTime())
                                .build())
                        .toList())
                .recentMessages(messages.stream()
                        .map(m -> CustomerDashboardResponse.MessagePreview.builder()
                                .id(m.getId())
                                .preview(m.getMessage().length() > 50 ?
                                        m.getMessage().substring(0, 50) + "..." :
                                        m.getMessage())
                                .dateTime(m.getDateTime())
                                .build())
                        .toList())
                .build();
    }

    public List<Message> getCustomerMessages(Long customerId) {
        return messageRepository.findByCustomerId(customerId);
    }

    public void deleteCustomerMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }
}