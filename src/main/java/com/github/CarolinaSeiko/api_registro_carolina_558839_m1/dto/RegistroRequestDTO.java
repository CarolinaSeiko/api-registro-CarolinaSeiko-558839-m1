package com.github.CarolinaSeiko.api_registro_carolina_558839_m1.dto;

import com.github.CarolinaSeiko.api_registro_carolina_558839_m1.entities.CondicaoClimatica;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroRequestDTO {

    @NotBlank(message = "O nome da estacao e obrigatorio")
    @Size(min = 3, max = 100, message = "O nome da estacao deve ter entre 3 e 100 caracteres")
    private String estacaoNome;

    @NotNull(message = "A data do registro e obrigatoria")
    @PastOrPresent(message = "A data do registro nao pode ser futura")
    private LocalDate dataRegistro;

    @NotNull(message = "A temperatura e obrigatoria")
    private BigDecimal temperatura;

    @NotNull(message = "O percentual de umidade e obrigatorio")
    @DecimalMin(value = "0.0", message = "A umidade nao pode ser menor que 0")
    @DecimalMax(value = "100.0", message = "A umidade nao pode ser maior que 100")
    private BigDecimal percentualUmidade;

    @NotNull(message = "A precipitacao e obrigatoria")
    @DecimalMin(value = "0.0", message = "A precipitacao nao pode ser negativa")
    private BigDecimal precipitacao;

    @NotNull(message = "A condicao climatica e obrigatoria")
    private CondicaoClimatica condicaoClimatica;
}
