package com.icodeap.ecommerce.backend.infrastructure.Entity;

import com.icodeap.ecommerce.backend.domain.model.UserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")

@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String address;
    private String cellphone;
    private String password;
    @Column(name = "tokenPassword")
    private String tokenPassword;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdate;

    @Setter
    @Getter
    @Column(nullable = false)
    private boolean correoVerificado = false; // Por defecto, no verificado

    private String verificationToken; // Token de verificación

    private LocalDateTime fechaExpiracion; // Fecha de expiración del token de verificación

}
