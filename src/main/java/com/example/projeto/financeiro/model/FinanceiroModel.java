package com.example.projeto.financeiro.model;

import java.time.LocalDate;

import com.example.projeto.login.model.ModelUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "financeiro")
@Entity
public class FinanceiroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "operador_id", nullable = false)
    private ModelUser user;

    @Column(name = "mes_atual", nullable = false, columnDefinition = "varchar(20)")
    private String mesAtual;

    @Column(name = "ano_atual", nullable = false)
    private int anoAtual;

    @Column(name = "dia_pago", nullable = true, columnDefinition = "date")
    private LocalDate diaPago;

    @Column(name = "valor_pago", nullable = false)
    Double valorPago;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(25)")
    private String status;

}
