package com.forochallenge.api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.forochallenge.api.dto.TopicoRequest;
import com.forochallenge.api.dto.TopicoResponse;
import com.forochallenge.api.entity.Topico;
import com.forochallenge.api.entity.Usuario;
import com.forochallenge.api.repository.TopicoRepository;
import com.forochallenge.api.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Topico createTopico(TopicoRequest request, String userEmail) {
        // Busca por email en lugar de ID
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + userEmail));

        Topico topico = Topico.builder()
                .titulo(request.getTitulo())
                .mensaje(request.getMensaje())
                .autor(usuario)
                .fechaCreacion(LocalDateTime.now())
                .build();

        return topicoRepository.save(topico);
    }
}