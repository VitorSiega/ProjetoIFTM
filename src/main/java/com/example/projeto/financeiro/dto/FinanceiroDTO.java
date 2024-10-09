package com.example.projeto.financeiro.dto;

import com.example.projeto.login.model.ModelUser;

public record FinanceiroDTO(ModelUser user, Float valor, String status) {
    
}
