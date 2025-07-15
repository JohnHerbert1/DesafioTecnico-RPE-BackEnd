package EVOLUIR_DESAFIO_RPE.fintech_RPE.models;

import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.enums.StatusBloqueio;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "tb_Clients")
public class Client {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @Column
    private String name;

    @Column(name = "cpf", nullable = false, unique = true, updatable = false)
    @CPF
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNacimento;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusBloqueio statusBloqueio;

    @Column
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal limiteCredito;

    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Fatura> faturas = new ArrayList<>();

    public String getId() {return id;}

    public void setName(String name) {this.name = name;}

    public String getName() {return name;}

    public void setCpf(String cpf) {this.cpf = cpf;}

    public String getCpf() {return cpf;}

    public void setDataNacimento(LocalDate dataNacimento) {
        this.dataNacimento = dataNacimento;
    }

    public LocalDate getDataNacimento() {
        return dataNacimento;
    }

    public void setStatusBloqueio(StatusBloqueio statusBloqueio) {this.statusBloqueio = statusBloqueio;}

    public StatusBloqueio getStatusBloqueio() {return statusBloqueio;}

    public void setId(String id) {this.id = id;}

    public BigDecimal getLimiteCredito() {return limiteCredito;}

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public void setFaturas(List<Fatura> faturas) {
        this.faturas = faturas;
    }

    public List<Fatura> getFaturas() {
        return faturas;
    }


}
