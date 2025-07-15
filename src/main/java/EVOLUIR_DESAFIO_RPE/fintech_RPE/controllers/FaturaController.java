package EVOLUIR_DESAFIO_RPE.fintech_RPE.controllers;

import EVOLUIR_DESAFIO_RPE.fintech_RPE.dto.FaturaDTO;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.services.FaturaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faturas")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class FaturaController {

    private final FaturaService faturaService;

    FaturaController(final FaturaService faturaService){
        this.faturaService = faturaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FaturaDTO creat(@RequestBody @Valid final FaturaDTO faturaDTO){
        var creatFatura = faturaService.creat(faturaDTO);
        return creatFatura;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FaturaDTO> getAllFaturas(){
        var todasAsFaturas = faturaService.getAll();
        return todasAsFaturas;
    }

    @GetMapping("/atrasadas")
    @ResponseStatus(HttpStatus.OK)
    public List<FaturaDTO> getAllStatusAtrasado(){
        var todasAsFaturasAtrasadas = faturaService.findByStatus();
        return todasAsFaturasAtrasadas;
    }

    @GetMapping("/clients/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<FaturaDTO> listAllClientFaturas(@PathVariable final String clientId){
        var todasAsFaturasClients = faturaService.listByClient(clientId);
        return todasAsFaturasClients;
    }

    @PostMapping("/{faturaId}/pay")
    @ResponseStatus(HttpStatus.OK)
    public void payFatura(@PathVariable String faturaId) {
        faturaService.registerPayment(faturaId);
    }
}
