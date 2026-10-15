package com.github.CarolinaSeiko.api_registro_carolina_558839_m1.dto;

import com.github.CarolinaSeiko.api_registro_carolina_558839_m1.entities.CondicaoClimatica;
import com.github.CarolinaSeiko.api_registro_carolina_558839_m1.entities.RegistroMeteorologico;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class RegistroResponseDTO {

    private Long registroId;
    private String estacaoNome;
    private LocalDate dataRegistro;
    private BigDecimal temperatura;
    private BigDecimal percentualUmidade;
    private BigDecimal precipitacao;
    private CondicaoClimatica condicaoClimatica;

    public RegistroResponseDTO(RegistroMeteorologico registro) {
        this.registroId = registro.getRegistroId();
        this.estacaoNome = registro.getEstacaoNome();
        this.dataRegistro = registro.getDataRegistro();
        this.temperatura = registro.getTemperatura();
        this.percentualUmidade = registro.getPercentualUmidade();
        this.precipitacao = registro.getPrecipitacao();
        this.condicaoClimatica = registro.getCondicaoClimatica();
    }
}
