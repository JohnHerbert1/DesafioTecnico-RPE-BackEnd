package EVOLUIR_DESAFIO_RPE.fintech_RPE.models.enums;

public enum StatusBloqueio {
    ATIVO("A"),BLOQUEIO("B");


    private String status;

    StatusBloqueio(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
