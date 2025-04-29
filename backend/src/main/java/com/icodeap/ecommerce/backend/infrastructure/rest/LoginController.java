package com.icodeap.ecommerce.backend.infrastructure.rest;

import com.icodeap.ecommerce.backend.application.UserService;
import com.icodeap.ecommerce.backend.domain.model.User;
import com.icodeap.ecommerce.backend.infrastructure.dto.JWTClient;
import com.icodeap.ecommerce.backend.infrastructure.dto.UserDTO;
import com.icodeap.ecommerce.backend.infrastructure.jwt.JWTGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/security")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final UserService userService;

    public LoginController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try {
            // Buscar el usuario por su email
            User user = userService.finByEmail(userDTO.username());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo electrónico o contraseña no válidos.");
            }

            // Verificar si el correo ha sido validado
            if (!user.isCorreoVerificado()) {
                throw new DisabledException("El correo electrónico no ha sido verificado.");
            }
            LocalDateTime fechaExpiracion = user.getFechaExpiracion();
            if (fechaExpiracion == null) {
                log.warn("Fecha de expiración es null para el usuario {}", userDTO.username());
            }
            if (fechaExpiracion != null && fechaExpiracion.isBefore(LocalDateTime.now())) {
                throw new DisabledException("El token ha expirado.");
            }

            // Autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password())
            );

            // Establecer el contexto de autenticación
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Rol de usuario: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().toString());

            // Generar el token JWT
            String token = jwtGenerator.getToken(userDTO.username());
            JWTClient jwtClient = new JWTClient(user.getId(), token, user.getUserType().toString());

            return ResponseEntity.ok(jwtClient);
        } catch (DisabledException e) {
            // Si el correo no ha sido verificado, lanzar una respuesta con un mensaje adecuado
            log.error("Login error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El correo electrónico no ha sido verificado. Por favor, revisa tu bandeja de entrada.");
        } catch (BadCredentialsException e) {
            // Si las credenciales son incorrectas, devolver un mensaje de error
            log.error("Login error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo electrónico o contraseña no válidos.");
        } catch (Exception e) {
            // Si ocurre cualquier otro error, manejarlo aquí
            log.error("Login error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error en el inicio de sesión.");
        }

    }
}
