package com.forochallenge.api.service;

import lombok.RequiredArgsConstructor;
import com.forochallenge.api.dto.AuthRequest;
import com.forochallenge.api.dto.AuthResponse;
import com.forochallenge.api.entity.Role;
import com.forochallenge.api.entity.Usuario;
import com.forochallenge.api.exception.EmailAlreadyExistsException;
import com.forochallenge.api.repository.UsuarioRepository;
import com.forochallenge.api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(Usuario request) {
        // 1. Validar que el email no existe
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("El correo " + request.getEmail() + " ya está registrado");
        }

        // 2. Construir y guardar el nuevo usuario
        var usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER) // Asignar rol por defecto
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 3. Generar token JWT
        var jwtToken = jwtService.generateToken(usuarioGuardado);

        // 4. Retornar respuesta
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional(readOnly = true)
    public AuthResponse authenticate(AuthRequest request) {
        // 1. Autenticar credenciales
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Obtener usuario de la base de datos
        var usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 3. Generar token JWT
        var jwtToken = jwtService.generateToken(usuario);

        // 4. Retornar respuesta
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    // Método adicional para verificar existencia de email
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}