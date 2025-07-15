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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FaturaServiceTest {

    @Mock private FaturaRepository faturaRepository;
    @Mock private ClienteRepository clienteRepository;
    @InjectMocks private FaturaService service;

    private Client client;
    private Fatura fatura;
    private FaturaDTO dto;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId("cli-1");
        client.setStatusBloqueio(StatusBloqueio.ATIVO);
        client.setLimiteCredito(new BigDecimal("5000"));

        fatura = new Fatura();
        fatura.setId("fat-1");
        fatura.setCliente(client);
        fatura.setDataVencimento(LocalDate.of(2025, 7, 5));
        fatura.setValor(new BigDecimal("200"));
        fatura.setStatusFatura(StatusFatura.B);

        dto = new FaturaDTO(
                fatura.getId(),
                client.getId(),
                fatura.getDataVencimento(),
                fatura.getDataPagamento(),
                fatura.getValor(),
                fatura.getStatusFatura()
        );
    }


    @Test
    void devePersistirFaturaEAssociarAoClienteQuandoClienteExiste() {
        when(clienteRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(faturaRepository.save(any(Fatura.class))).thenAnswer(inv -> inv.getArgument(0));

        FaturaDTO resultado = service.creat(dto);

        assertEquals(dto, resultado);
        verify(faturaRepository).save(any(Fatura.class));
        assertEquals(1, client.getFaturas().size());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        when(clienteRepository.findById(client.getId())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> service.creat(dto));
    }


    @Test
    void deveAtualizarPagamentoEStatusQuandoRegistroPago() {
        when(faturaRepository.findById(fatura.getId())).thenReturn(Optional.of(fatura));
        LocalDate dataMock = LocalDate.of(2025, 7, 10);

        try (MockedStatic<LocalDate> mockDate = Mockito.mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
            mockDate.when(LocalDate::now).thenReturn(dataMock);
            service.registerPayment(fatura.getId());
        }

        assertEquals(StatusFatura.P, fatura.getStatusFatura());
        assertEquals(dataMock, fatura.getDataPagamento());
        verify(faturaRepository).save(fatura);
    }

    @Test
    void deveLancarExcecaoQuandoFaturaNaoExistir() {
        when(faturaRepository.findById("invalid-id")).thenReturn(Optional.empty());
        assertThrows(FaturaNotExisteException.class, () -> service.registerPayment("invalid-id"));
    }


    @Test
    void deveBloquearClienteQuandoAtrasoMaiorQueTresDias() {
        Fatura atrasada = new Fatura();
        atrasada.setId("late");
        atrasada.setCliente(client);
        atrasada.setDataVencimento(LocalDate.of(2025, 7, 1));
        atrasada.setStatusFatura(StatusFatura.B);
        when(faturaRepository.findByStatusFatura(StatusFatura.B)).thenReturn(List.of(atrasada));

        LocalDate dataHoje = LocalDate.of(2025, 7, 5);
        try (MockedStatic<LocalDate> mockDate = Mockito.mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
            mockDate.when(LocalDate::now).thenReturn(dataHoje);
            service.verificarStatusDaFatura();
        }

        assertEquals(StatusFatura.A, atrasada.getStatusFatura());
        assertEquals(StatusBloqueio.BLOQUEIO, client.getStatusBloqueio());
        verify(faturaRepository).save(atrasada);
        verify(clienteRepository).save(client);
    }

    @Test
    void naoBloquearClienteQuandoAtrasoIgualTresDias() {
        Fatura limite = new Fatura();
        limite.setId("limit");
        limite.setCliente(client);
        limite.setDataVencimento(LocalDate.of(2025, 7, 2));
        limite.setStatusFatura(StatusFatura.B);
        when(faturaRepository.findByStatusFatura(StatusFatura.B)).thenReturn(List.of(limite));

        LocalDate dataHoje = LocalDate.of(2025, 7, 5);
        try (MockedStatic<LocalDate> mockDate = Mockito.mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
            mockDate.when(LocalDate::now).thenReturn(dataHoje);
            service.verificarStatusDaFatura();
        }


        assertEquals(StatusFatura.B, limite.getStatusFatura());
        assertEquals(StatusBloqueio.ATIVO, client.getStatusBloqueio());
        verify(faturaRepository, never()).save(any());
        verify(clienteRepository, never()).save(any());
    }



    @Test
    void deveListarFaturasDoClienteQuandoExistir() {

        Fatura outra = new Fatura();
        outra.setId("fat-2");
        outra.setCliente(client);
        outra.setDataVencimento(LocalDate.of(2025, 8, 1));
        outra.setStatusFatura(StatusFatura.B);
        client.getFaturas().addAll(List.of(fatura, outra));

        when(clienteRepository.findById(client.getId())).thenReturn(Optional.of(client));

        List<FaturaDTO> list = service.listByClient(client.getId());
        assertEquals(2, list.size());
        assertTrue(list.stream().map(FaturaDTO::id).collect(Collectors.toList()).containsAll(List.of("fat-1", "fat-2")));
    }


    @Test
    void deveRetornarTodasAsFaturasComoDTOs() {
        when(faturaRepository.findAll()).thenReturn(List.of(fatura));
        assertEquals(1, service.getAll().size());
    }

    @Test
    void deveRetornarFaturasAtrasadasQuandoFiltrarPorStatus() {
        fatura.setStatusFatura(StatusFatura.A);
        when(faturaRepository.findByStatusFatura(StatusFatura.A)).thenReturn(List.of(fatura));
        assertEquals(1, service.findByStatus().size());
    }
}
