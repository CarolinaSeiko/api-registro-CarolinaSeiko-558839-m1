package com.github.CarolinaSeiko.api_registro_carolina_558839_m1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_registro_meteorologico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroMeteorologico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registroId;

    private String estacaoNome;

    private LocalDate dataRegistro;

    private BigDecimal temperatura;

    private BigDecimal percentualUmidade;

    private BigDecimal precipitacao;

    @Enumerated(EnumType.STRING)
    private CondicaoClimatica condicaoClimatica;
}
