package com.icodeap.ecommerce.backend.infrastructure.rest;

import com.icodeap.ecommerce.backend.application.UserService;
import com.icodeap.ecommerce.backend.domain.model.User;
import com.icodeap.ecommerce.backend.domain.port.IUserRepository;
import com.icodeap.ecommerce.backend.infrastructure.adapter.IUserCrudRepository;
import com.icodeap.ecommerce.backend.infrastructure.adapter.UserRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200")

public class UserController {

    private final UserService userService;
    private final UserRepositoryImpl userRepository;

    public UserController(UserService userService, UserRepositoryImpl userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @PostMapping
    public User save(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id){
        return userService.findById(id);
    }

    @GetMapping("/users/email/{email}")
    public User finByEmail(@PathVariable String email) {
        return userService.finByEmail(email);
    }

    // Endpoint para verificar el correo del usuario
    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token) {
        boolean success = userRepository.verificarUsuario(token);
        if (success) {
            return ResponseEntity.ok().body(new ResponseMessage("Correo verificado con éxito."));
        } else {
            return ResponseEntity.badRequest().body(new ResponseMessage("El token ha expirado o es inválido."));
        }
    }

    // Clase auxiliar para la respuesta
    public class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


}
