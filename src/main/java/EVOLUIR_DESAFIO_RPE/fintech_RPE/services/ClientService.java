package EVOLUIR_DESAFIO_RPE.fintech_RPE.services;

import EVOLUIR_DESAFIO_RPE.fintech_RPE.dto.ClientDTO;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.exception.ClientExistException;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.exception.ClientNotFoundException;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.Client;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.enums.StatusBloqueio;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.repositorys.ClienteRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);
    private final ClienteRepository clienteRepository;

    public ClientService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClientDTO creat(ClientDTO requestDTO){

        if(clienteRepository.findByCpf(requestDTO.cpf()) != null){
            log.error("[Client Service]:Client com o cpf informado já existe" + requestDTO.cpf());
            throw  new ClientExistException("Client informado com cpf informado já existe" + requestDTO.cpf());
        }
        var entity = toEntity(requestDTO);
        var save = clienteRepository.save(entity);

       return toDTO(save);
    }

    public ClientDTO update(String id,ClientDTO requestDTO){
        var client = findClientById(id);

        client.setName(requestDTO.name());
        client.setCpf(requestDTO.cpf());
        client.setDataNacimento(requestDTO.dataNacimento());
        client.setStatusBloqueio(requestDTO.statusBloqueio());

        var updateClient = clienteRepository.save(client);
        log.info("{Client Service]: O client do id:" + updateClient.getId());
        return requestDTO;
    }

    public List<ClientDTO> getAll(){
        log.info("[Client Service]: Listando todos os clients");
        return clienteRepository.findAll().stream().map(this::toDTO).toList();
    }

    public  List<ClientDTO> getAllClientStatusBlock(){
        return  clienteRepository.findAllByStatusBloqueio(StatusBloqueio.BLOQUEIO).stream().map(this::toDTO).toList();
    }


    //utils for class service:
    private Client findClientById(String id){
        return clienteRepository.findById(id).orElseThrow(() -> {
           log.error("[Client Service]: Client com informado nao encontrado" + id);
           return new ClientNotFoundException("Client informado não encontrado: " + id);
        });
    }

    private  Client findClientByCPF(String cpf){
        return Optional.ofNullable(clienteRepository.findByCpf(cpf)).orElseThrow(() -> {
            log.error("[Client Service]: Client com o cpf procurado não foi encontrado" + cpf);
        return new ClientNotFoundException("Client com o cpf procurado não foi encontrado"+ cpf);
        });
    }

    private Client toEntity(ClientDTO dto) {
        var c = new Client();
        BeanUtils.copyProperties(dto, c);
        return c;
    }

    private ClientDTO toDTO(Client c) {
        return new ClientDTO(
                c.getId(),
                c.getName(),
                c.getCpf(),
                c.getDataNacimento(),
                c.getStatusBloqueio(),
                c.getLimiteCredito()
        );
    }

}
