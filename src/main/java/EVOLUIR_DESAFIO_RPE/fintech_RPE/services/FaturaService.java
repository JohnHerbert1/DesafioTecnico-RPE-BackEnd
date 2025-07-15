package EVOLUIR_DESAFIO_RPE.fintech_RPE.services;


import EVOLUIR_DESAFIO_RPE.fintech_RPE.dto.FaturaDTO;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.exception.ClientNotFoundException;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.exception.FaturaNotExisteException;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.Client;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.Fatura;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.enums.StatusBloqueio;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.enums.StatusFatura;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.repositorys.ClienteRepository;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.repositorys.FaturaRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FaturaService {

    private static final Logger log = LoggerFactory.getLogger(FaturaService.class);
    private final FaturaRepository faturaRepository;
    private final ClienteRepository clienteRepository;

    public FaturaService(FaturaRepository repo, ClienteRepository clienteRepository){
        this.faturaRepository = repo;
        this.clienteRepository = clienteRepository;
    }

    public FaturaDTO creat(FaturaDTO requestDTO){
        Client client =clienteRepository.findById(requestDTO.client()).orElseThrow(() -> {
            log.error("[Fatura Service]A fatura não pode ser criada,pois o cliente não existe: " + requestDTO.client());
            return new ClientNotFoundException("A fatura não pode ser criada,pois o cliente não existe: "+ requestDTO.client());
        });

        var entity = toEntity(requestDTO);
        Fatura faturaSave = faturaRepository.save(entity);

        client.getFaturas().add(faturaSave);

        return  requestDTO;
    }

    @Transactional
    public void registerPayment(String faturaId){
        Fatura f = faturaRepository.findById(faturaId).orElseThrow(()-> {
           return new FaturaNotExisteException("Não a fatura com esse id:" + faturaId);
        });

        f.registrarPagamento(LocalDate.now());
        f.setStatusFatura(StatusFatura.P);
        faturaRepository.save(f);

    }

    @Scheduled(cron = "0 0 12 * * *")
    @Transactional
    public void verificarStatusDaFatura(){

        LocalDate hoje = LocalDate.now();

        List<Fatura> faturasStatusAberto = faturaRepository.findByStatusFatura(StatusFatura.B);

        for(Fatura f: faturasStatusAberto){
            if(hoje.isAfter(f.getDataVencimento().plusDays(3))){
                f.setStatusFatura(StatusFatura.A);
                faturaRepository.save(f);

                Client c = f.getCliente();
                c.setStatusBloqueio(StatusBloqueio.BLOQUEIO);
                c.setLimiteCredito(BigDecimal.ZERO);
                clienteRepository.save(c);
            }
        }

    }

    public List<FaturaDTO> getAll(){
        log.info("Listando todas as faturas:");
        return faturaRepository.findAll().stream().map(this::toDTO).toList();
    }

    public List<FaturaDTO> findByStatus(){
        return faturaRepository.findByStatusFatura(StatusFatura.A).stream().map(this::toDTO).toList();
    }

    public List<FaturaDTO> listByClient(String clientId) {
        Client client = clienteRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado: " + clientId));

        return client.getFaturas().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private Fatura toEntity(FaturaDTO requestDTO){
        var f = new Fatura();
        BeanUtils.copyProperties(requestDTO, f);
        return f;
    }

    private FaturaDTO toDTO(Fatura fatura){
      return new FaturaDTO(fatura.getId()
       ,fatura.getCliente().getId(),fatura.getDataVencimento(),fatura.getDataPagamento(),fatura.getValor(),fatura.getStatusFatura());
    }
}
