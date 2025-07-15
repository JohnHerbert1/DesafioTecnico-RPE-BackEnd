package EVOLUIR_DESAFIO_RPE.fintech_RPE.controllers;

import EVOLUIR_DESAFIO_RPE.fintech_RPE.dto.ClientDTO;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.Client;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.services.ClientService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ClientController {

    private final ClientService clienteService;

    public ClientController(final  ClientService clienteService){
        this.clienteService = clienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO creat(@RequestBody @Valid final  ClientDTO requestDTO){
        var creatClient = clienteService.creat(requestDTO);
        return creatClient;
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<ClientDTO> getAllClient(){
        var allClients = clienteService.getAll();
        return allClients;
    }
    @PutMapping("/{clientId}")
    @ResponseStatus(OK)
    public ClientDTO update(@PathVariable final String clientId, @RequestBody @Valid final ClientDTO requestDTO){
        var clientUpdate = clienteService.update(clientId,requestDTO);
        return clientUpdate;
    }

    @GetMapping("/blocked")
    @ResponseStatus(OK)
    public  List<ClientDTO> getClientAllStatusBlock(){
        var clientBlock = clienteService.getAllClientStatusBlock();
        return  clientBlock;
    }


}
