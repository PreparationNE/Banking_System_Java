package com.NE.Banking_System.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import jakarta.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[A-Za-z]{2,50}$", message = "First name must be 2-50 alphabetic characters")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]{2,50}$", message = "Last name must be 2-50 alphabetic characters")
    private String lastName;

    @Pattern(
            regexp = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$",
            message = "Email must contain @ and a valid domain"
    )
    @Column(unique = true)
    private String email;


    private String password;

    @Pattern(
            regexp = "^\\d{10}$",
            message = "Phone number must be exactly 10 digits"
    )
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    private String mobile;
    private LocalDate dob;

    @Column(unique = true)
    private String accountNumber;

    private Double balance;
    private LocalDateTime lastUpdateDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    // UserDetails methods implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

}