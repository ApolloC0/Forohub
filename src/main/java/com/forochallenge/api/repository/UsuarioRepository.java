package com.forochallenge.api.repository;

import com.forochallenge.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método básico para buscar por email (case sensitive)
    Optional<Usuario> findByEmail(String email);

    // Método para verificar existencia de email (case insensitive)
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Usuario u WHERE LOWER(u.email) = LOWER(:email)")
    boolean existsByEmail(@Param("email") String email);

    // Método alternativo con proyección para solo obtener el email
    @Query("SELECT u.email FROM Usuario u WHERE u.id = :id")
    Optional<String> findEmailById(@Param("id") Long id);

    // Método para autenticación que ignora mayúsculas/minúsculas
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<Usuario> findByEmailIgnoreCase(@Param("email") String email);

    // Método para verificar existencia por ID
    boolean existsById(Long id);
}
