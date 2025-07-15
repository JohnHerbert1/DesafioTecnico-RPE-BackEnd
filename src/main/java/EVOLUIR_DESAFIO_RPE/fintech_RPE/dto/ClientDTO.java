package EVOLUIR_DESAFIO_RPE.fintech_RPE.dto;

import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.enums.StatusBloqueio;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClientDTO(String id, @NotBlank String name, @NotBlank String cpf, @JsonFormat(pattern = "yyyy-MM-dd")
@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate dataNacimento,
                         StatusBloqueio statusBloqueio,  @DecimalMin("0.00") BigDecimal limiteCredito) {
}
