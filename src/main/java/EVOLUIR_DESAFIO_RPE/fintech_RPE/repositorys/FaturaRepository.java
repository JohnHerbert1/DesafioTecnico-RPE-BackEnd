package EVOLUIR_DESAFIO_RPE.fintech_RPE.repositorys;

import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.Fatura;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.enums.StatusFatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaturaRepository extends JpaRepository <Fatura,String>{

      List<Fatura> findByStatusFatura(StatusFatura statusFatura);
}
