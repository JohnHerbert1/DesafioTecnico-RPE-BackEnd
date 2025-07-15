package EVOLUIR_DESAFIO_RPE.fintech_RPE.repositorys;

import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.Client;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.enums.StatusBloqueio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ClienteRepository extends JpaRepository<Client,String > {

    Client findByCpf(final String cpf);
    List<Client> findAllByStatusBloqueio(StatusBloqueio status);
}
