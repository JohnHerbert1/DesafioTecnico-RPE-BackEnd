package EVOLUIR_DESAFIO_RPE.fintech_RPE.dto;

import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.enums.StatusFatura;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record FaturaDTO(String id, String client, @JsonFormat(pattern = "yyyy-MM-dd")
@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimento, @JsonFormat(pattern = "yyyy-MM-dd")
@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPagamento, @DecimalMin("0.00") BigDecimal valor, StatusFatura statusFatura) {
}
