package com.NE.Banking_System.service;

import com.NE.Banking_System.dto.request.AuthenticationRequest;
import com.NE.Banking_System.dto.response.AuthenticationResponse;
import com.NE.Banking_System.dto.response.RegisterResponse;
import com.NE.Banking_System.dto.request.RegisterRequest;
import com.NE.Banking_System.entity.Customer;
import com.NE.Banking_System.entity.Role;
import com.NE.Banking_System.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder; // Removed static modifier
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(RegisterRequest request) { // Removed static modifier
        var customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountNumber(generateAccountNumber())
                .balance(request.getBalance())
                .dob(request.getDob())
                .lastUpdateDateTime(LocalDateTime.now())
                .role(Role.USER)
                .build();

        customerRepository.save(customer); // Changed from static call to instance call
        return RegisterResponse.builder().build();// Include role in response
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(new HashMap<>(), customer);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private String generateAccountNumber() { // Removed static modifier
        return "ACC" + System.currentTimeMillis();
    }
}