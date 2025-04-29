package com.icodeap.ecommerce.backend.domain.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String cellphone;
    private String password;
    private String tokenPassword;
    private UserType userType;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdate;

    private boolean correoVerificado = false; // Por defecto, no verificado
    private String verificationToken; // Token de verificación
    private LocalDateTime fechaExpiracion; // Fecha de expiración del token de verificación


}
