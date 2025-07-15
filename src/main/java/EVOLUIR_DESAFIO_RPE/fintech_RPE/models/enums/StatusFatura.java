package EVOLUIR_DESAFIO_RPE.fintech_RPE.models.enums;

public enum StatusFatura {

    P("Paga"),A("Atrasada"),B("Aberta");

    private String status;

    StatusFatura(String paga) {
        this.status = paga;
    }

    public String getStatus() {return status;}
}
