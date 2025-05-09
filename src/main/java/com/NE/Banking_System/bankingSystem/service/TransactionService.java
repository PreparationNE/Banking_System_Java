package com.NE.Banking_System.service;

import com.NE.Banking_System.dto.response.TransactionResponse;
import com.NE.Banking_System.entity.BankingTransaction;
import com.NE.Banking_System.entity.Customer;
import com.NE.Banking_System.entity.TransactionType;
import com.NE.Banking_System.exceptions.BusinessRuleException;
import com.NE.Banking_System.exceptions.ResourceNotFoundException;
import com.NE.Banking_System.repository.BankingTransactionRepository;
import com.NE.Banking_System.repository.CustomerRepository;
import com.NE.Banking_System.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final CustomerRepository customerRepository;
    private final BankingTransactionRepository transactionRepository;
    private final EmailService emailService;
    private final MessageRepository messageRepository;

    @Transactional
    public BankingTransaction performDeposit(Long customerId, Double amount) {
        if (amount <= 0) {
            throw new BusinessRuleException("Deposit amount must be positive");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId));


        customer.setBalance(customer.getBalance() + amount);
        customer.setLastUpdateDateTime(LocalDateTime.now());

        BankingTransaction transaction = new BankingTransaction();
        transaction.setCustomer(customer);
        transaction.setAccount(customer.getAccountNumber());
        transaction.setAmount(amount);
        transaction.setType(TransactionType.SAVING);
        transaction.setBankingDateTime(LocalDateTime.now());

        BankingTransaction savedTransaction = transactionRepository.save(transaction);
        customerRepository.save(customer);

        sendNotification(customer, amount, "SAVING");

        return savedTransaction;
    }

    @Transactional
    public BankingTransaction performWithdrawal(Long customerId, Double amount) {
        if (amount <= 0) {
            throw new BusinessRuleException("Withdrawal amount must be positive");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId));

        if (customer.getBalance() < amount) {
            throw new BusinessRuleException("Insufficient balance for withdrawal");
        }

        customer.setBalance(customer.getBalance() - amount);
        customer.setLastUpdateDateTime(LocalDateTime.now());

        BankingTransaction transaction = new BankingTransaction();
        transaction.setCustomer(customer);
        transaction.setAccount(customer.getAccountNumber());
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setBankingDateTime(LocalDateTime.now());

        BankingTransaction savedTransaction = transactionRepository.save(transaction);
        customerRepository.save(customer);

        sendNotification(customer, amount, "WITHDRAW");

        return savedTransaction;
    }

    public List<BankingTransaction> getTransactionsByCustomerId(Long customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }

    @Transactional
    public BankingTransaction performTransfer(Long fromCustomerId, Long toAccountNumber, Double amount) {
        if (amount <= 0) {
            throw new BusinessRuleException("Transfer amount must be positive");
        }

        Customer fromCustomer = customerRepository.findById(fromCustomerId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender customer", fromCustomerId));

        Customer toCustomer = customerRepository.findByAccountNumber(String.valueOf(toAccountNumber))
                .orElseThrow(() -> new ResourceNotFoundException("Recipient account", toAccountNumber));

        if (fromCustomer.getBalance() < amount) {
            throw new BusinessRuleException("Insufficient balance for transfer");
        }

        if (fromCustomer.getId().equals(toCustomer.getId())) {
            throw new BusinessRuleException("Cannot transfer to the same account");
        }

        // Deduct from sender
        fromCustomer.setBalance(fromCustomer.getBalance() - amount);
        fromCustomer.setLastUpdateDateTime(LocalDateTime.now());

        // Add to receiver
        toCustomer.setBalance(toCustomer.getBalance() + amount);
        toCustomer.setLastUpdateDateTime(LocalDateTime.now());

        // Create transaction for sender
        BankingTransaction transaction = new BankingTransaction();
        transaction.setCustomer(fromCustomer);
        transaction.setAccount(fromCustomer.getAccountNumber());
        transaction.setAmount(amount);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setBankingDateTime(LocalDateTime.now());

        BankingTransaction savedTransaction = transactionRepository.save(transaction);
        customerRepository.save(fromCustomer);
        customerRepository.save(toCustomer);

        sendNotification(fromCustomer, amount, "TRANSFER (sent to " + toCustomer.getAccountNumber() + ")");
        sendNotification(toCustomer, amount, "TRANSFER (received from " + fromCustomer.getAccountNumber() + ")");

        return savedTransaction;
    }

    public TransactionResponse mapToDTO(BankingTransaction transaction) {
        TransactionResponse dto = new TransactionResponse();
        dto.setId(transaction.getId());
        dto.setAccount(transaction.getAccount());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType().name());
        dto.setBankingDateTime(transaction.getBankingDateTime());

        Customer customer = transaction.getCustomer();
        dto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
        dto.setCustomerAccount(customer.getAccountNumber());
        dto.setCustomerBalance(customer.getBalance());

        return dto;
    }

    private void sendNotification(Customer customer, Double amount, String transactionType) {
        String messageContent = String.format(
                "Dear %s %s, Your %s of %.2f on your account %s has been Completed Successfully.",
                customer.getFirstName(),
                customer.getLastName(),
                transactionType,
                amount,
                customer.getAccountNumber()
        );


        // Send email
        emailService.sendEmail(
                customer.getEmail(),
                "Transaction Notification",
                messageContent
        );
    }
    public List<TransactionResponse> getCustomerTransactions(Long customerId) {
        return transactionRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
}
