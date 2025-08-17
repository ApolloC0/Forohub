package com.forochallenge.api.controller;

import com.forochallenge.api.entity.Topico;
import com.forochallenge.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import com.forochallenge.api.dto.TopicoRequest;
import com.forochallenge.api.dto.TopicoResponse;
import com.forochallenge.api.service.TopicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicoController {

    private final TopicoService topicoService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<Topico> crearTopico(
            @RequestBody TopicoRequest request,
            @RequestHeader("Authorization") String authHeader) {

        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);

        Topico topico = topicoService.createTopico(request, userEmail);
        return ResponseEntity.ok(topico);
    }
}