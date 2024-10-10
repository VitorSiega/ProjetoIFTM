package com.example.projeto.financeiro.dto;

import java.time.LocalDate;

public record FinanceiroDTO(
        Long id,
        LocalDate dia_pago,
        Double valorPago,
        String status) {

}
