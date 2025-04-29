package com.icodeap.ecommerce.backend.infrastructure.adapter;

import com.icodeap.ecommerce.backend.domain.model.User;
import com.icodeap.ecommerce.backend.domain.port.IUserRepository;
import com.icodeap.ecommerce.backend.infrastructure.Entity.UserEntity;
import com.icodeap.ecommerce.backend.infrastructure.emailverification.service.EmailServices;
import com.icodeap.ecommerce.backend.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final IUserCrudRepository iUserCrudRepository;
    private final UserMapper userMapper;
    private final EmailServices emailService;

    // Constructor
    public UserRepositoryImpl(IUserCrudRepository iUserCrudRepository, UserMapper userMapper, EmailServices emailService) {
        this.iUserCrudRepository = iUserCrudRepository;
        this.userMapper = userMapper;
        this.emailService = emailService;
    }

    // Método para registrar un nuevo usuario
    @Override
    public User save(User user) {
        // Generamos un token de verificación único
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);

        // Establecemos la fecha de expiración (2 minutos en este ejemplo)
        LocalDateTime fechaExpiracion = LocalDateTime.now().plusMinutes(2);
        user.setFechaExpiracion(fechaExpiracion);

        // Convertimos el usuario a la entidad y lo guardamos en la base de datos
        UserEntity userEntity = userMapper.touserEntity(user);
        userEntity = iUserCrudRepository.save(userEntity);

        // Convertimos la entidad guardada a un objeto de tipo User
        User savedUser = userMapper.toUser(userEntity);

        // Enviamos el correo con el token al usuario
        try {
            emailService.sendVerificationEmail(savedUser.getEmail(), verificationToken);
        } catch (Exception e) {
            // Manejar el caso de error al enviar el correo
            System.err.println("Error al enviar el correo de verificación: " + e.getMessage());
        }

        return savedUser; // Retornamos el usuario guardado
    }


    // Método para verificar el token de verificación y activar el usuario
    public boolean verificarUsuario(String token) {
        // Buscar al usuario usando el token de verificación
        UserEntity userEntity = iUserCrudRepository.findByVerificationToken(token);

        if (userEntity != null) {
            // Verificamos si el token ha expirado
            if (userEntity.getFechaExpiracion().isBefore(LocalDateTime.now())) {
                return false; // El token ha expirado
            }

            // Si el token es válido, marcamos el correo como verificado
            userEntity.setCorreoVerificado(true);
            userEntity.setVerificationToken(null);  // Limpiamos el token para evitar el uso posterior
            userEntity.setFechaExpiracion(null);    // Limpiamos la fecha de expiración
            iUserCrudRepository.save(userEntity);  // Guardamos los cambios
            return true; // El correo ha sido verificado exitosamente
        }
        return false; // El token es inválido
    }

    // Método para encontrar un usuario por correo electrónico
    @Override
    public User findByEmail(String email) {
        return userMapper.toUser(iUserCrudRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Usuario con email: " + email + " no encontrado")
        ));
    }

    // Método para encontrar un usuario por ID
    @Override
    public User findById(Integer id) {
        return userMapper.toUser(iUserCrudRepository.findById(id).get());
    }

    // Método para verificar si un usuario existe por su correo electrónico
    public boolean existsByEmail(String email) {
        return iUserCrudRepository.existsByEmail(email);
    }

    // Método para encontrar un usuario por el token de recuperación de contraseña
    public User findByTokenPassword(String tokenPassword) {
        return userMapper.toUser(iUserCrudRepository.findByTokenPassword(tokenPassword).orElseThrow(
                () -> new RuntimeException("Token de recuperación de contraseña no válido")
        ));
    }
}
