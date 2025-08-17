package com.forochallenge.api.repository;

import com.forochallenge.api.entity.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findAllByOrderByFechaCreacionDesc();
}
