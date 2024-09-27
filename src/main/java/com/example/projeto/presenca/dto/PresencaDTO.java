package com.example.projeto.presenca.dto;

import java.time.LocalDate;

public record PresencaDTO(Long userId, LocalDate data, String status) {
}