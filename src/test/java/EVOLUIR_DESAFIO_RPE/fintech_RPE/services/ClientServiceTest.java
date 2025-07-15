package EVOLUIR_DESAFIO_RPE.fintech_RPE.services;

import EVOLUIR_DESAFIO_RPE.fintech_RPE.dto.ClientDTO;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.exception.ClientExistException;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.exception.ClientNotFoundException;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.Client;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.models.enums.StatusBloqueio;
import EVOLUIR_DESAFIO_RPE.fintech_RPE.repositorys.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClienteRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private ClientDTO clientDTO;

    @BeforeEach
    void prepararDados() {
        client = new Client();
        client.setId("1");
        client.setName("John");
        client.setCpf("12345678900");
        client.setDataNacimento(LocalDate.of(1990, 1, 1));
        client.setStatusBloqueio(StatusBloqueio.BLOQUEIO);

        clientDTO = new ClientDTO(
                "1",
                "John",
                "12345678900",
                LocalDate.of(1990, 1, 1),
                StatusBloqueio.BLOQUEIO,
                null
        );
    }

    @Test
    void deveCriarClienteQuandoCpfNaoExiste() {
        when(clientRepository.findByCpf("12345678900")).thenReturn(null);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientDTO criado = clientService.creat(clientDTO);

        assertNotNull(criado);
        assertEquals("John", criado.name());
        verify(clientRepository, times(1)).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoCpfExisteAoCriar() {
        when(clientRepository.findByCpf("12345678900")).thenReturn(client);

        assertThrows(ClientExistException.class, () -> clientService.creat(clientDTO));
    }

    @Test
    void deveAtualizarClienteQuandoExistir() {
        when(clientRepository.findById("1")).thenReturn(Optional.of(client));
        when(clientRepository.save(any())).thenReturn(client);

        ClientDTO atualizado = clientService.update("1", clientDTO);

        assertNotNull(atualizado);
        assertEquals("John", atualizado.name());
        verify(clientRepository, times(1)).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoAtualizarClienteNaoEncontrado() {
        when(clientRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.update("999", clientDTO));
    }

    @Test
    void deveRetornarTodosClientes() {
        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<ClientDTO> todos = clientService.getAll();

        assertEquals(1, todos.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void deveRetornarClientesComStatusBloqueado() {
        when(clientRepository.findAllByStatusBloqueio(StatusBloqueio.BLOQUEIO)).thenReturn(List.of(client));

        List<ClientDTO> bloqueados = clientService.getAllClientStatusBlock();

        assertEquals(1, bloqueados.size());
        assertEquals(StatusBloqueio.BLOQUEIO, bloqueados.get(0).statusBloqueio());
    }
}
