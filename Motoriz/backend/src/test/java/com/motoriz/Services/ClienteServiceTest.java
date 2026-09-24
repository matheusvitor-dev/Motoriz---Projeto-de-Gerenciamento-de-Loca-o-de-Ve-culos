package com.motoriz.Services;

import com.motoriz.Dtos.ClienteRequestDTO;
import com.motoriz.Dtos.ClienteResponseDTO;
import com.motoriz.Exceptions.ResourceNotFoundException;
import com.motoriz.Models.Cliente;
import com.motoriz.Repositories.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private static final UUID ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final UUID UNKNOWN_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private Cliente clienteBase() {
        return Cliente.builder()
                .id(ID)
                .nome("João Silva")
                .cpf("123.456.789-00")
                .cnh("12345678900")
                .telefone("(11) 99999-9999")
                .email("joao@email.com")
                .dataCadastro(LocalDateTime.of(2024, 1, 15, 10, 30))
                .build();
    }

    private ClienteRequestDTO requestBase() {
        return new ClienteRequestDTO("João Silva", "123.456.789-00", "12345678900", "(11) 99999-9999", "joao@email.com");
    }

    // --- findAll ---

    @Test
    void findAll_deveRetornarListaDeClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(clienteBase()));

        List<ClienteResponseDTO> resultado = clienteService.findAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).nome()).isEqualTo("João Silva");
        assertThat(resultado.get(0).cpf()).isEqualTo("123.456.789-00");
    }

    @Test
    void findAll_deveRetornarListaVaziaQuandoNaoHaClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of());

        assertThat(clienteService.findAll()).isEmpty();
    }

    // --- findById ---

    @Test
    void findById_deveRetornarClienteQuandoEncontrado() {
        when(clienteRepository.findById(ID)).thenReturn(Optional.of(clienteBase()));

        ClienteResponseDTO resultado = clienteService.findById(ID);

        assertThat(resultado.id()).isEqualTo(ID);
        assertThat(resultado.nome()).isEqualTo("João Silva");
        assertThat(resultado.cnh()).isEqualTo("12345678900");
    }

    @Test
    void findById_deveLancarExcecaoQuandoNaoEncontrado() {
        when(clienteRepository.findById(UNKNOWN_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.findById(UNKNOWN_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(UNKNOWN_ID.toString());
    }

    // --- create ---

    @Test
    void create_deveCriarClienteComSucesso() {
        when(clienteRepository.existsByCpf(anyString())).thenReturn(false);
        when(clienteRepository.existsByCnh(anyString())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteBase());

        ClienteResponseDTO resultado = clienteService.create(requestBase());

        assertThat(resultado.nome()).isEqualTo("João Silva");
        assertThat(resultado.cpf()).isEqualTo("123.456.789-00");
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void create_deveLancarExcecaoQuandoCpfJaCadastrado() {
        when(clienteRepository.existsByCpf("123.456.789-00")).thenReturn(true);

        assertThatThrownBy(() -> clienteService.create(requestBase()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CPF já cadastrado");

        verify(clienteRepository, never()).save(any());
    }

    @Test
    void create_deveLancarExcecaoQuandoCnhJaCadastrada() {
        when(clienteRepository.existsByCpf(anyString())).thenReturn(false);
        when(clienteRepository.existsByCnh("12345678900")).thenReturn(true);

        assertThatThrownBy(() -> clienteService.create(requestBase()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CNH já cadastrada");

        verify(clienteRepository, never()).save(any());
    }

    // --- update ---

    @Test
    void update_deveAtualizarClienteComSucesso() {
        Cliente existente = clienteBase();
        ClienteRequestDTO dto = new ClienteRequestDTO("Maria Silva", "123.456.789-00", "12345678900", "(21) 98888-7777", "maria@email.com");
        Cliente atualizado = Cliente.builder().id(ID).nome("Maria Silva").cpf("123.456.789-00").cnh("12345678900")
                .telefone("(21) 98888-7777").email("maria@email.com").dataCadastro(existente.getDataCadastro()).build();

        when(clienteRepository.findById(ID)).thenReturn(Optional.of(existente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(atualizado);

        ClienteResponseDTO resultado = clienteService.update(ID, dto);

        assertThat(resultado.nome()).isEqualTo("Maria Silva");
        assertThat(resultado.telefone()).isEqualTo("(21) 98888-7777");
    }

    @Test
    void update_deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.findById(UNKNOWN_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.update(UNKNOWN_ID, requestBase()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_deveLancarExcecaoQuandoCpfJaEmUsoPorOutroCliente() {
        Cliente existente = clienteBase();
        ClienteRequestDTO dto = new ClienteRequestDTO("João Silva", "999.999.999-99", "12345678900", null, null);

        when(clienteRepository.findById(ID)).thenReturn(Optional.of(existente));
        when(clienteRepository.existsByCpf("999.999.999-99")).thenReturn(true);

        assertThatThrownBy(() -> clienteService.update(ID, dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CPF já cadastrado");
    }

    @Test
    void update_deveLancarExcecaoQuandoCnhJaEmUsoPorOutroCliente() {
        Cliente existente = clienteBase();
        ClienteRequestDTO dto = new ClienteRequestDTO("João Silva", "123.456.789-00", "99999999999", null, null);

        when(clienteRepository.findById(ID)).thenReturn(Optional.of(existente));
        when(clienteRepository.existsByCnh("99999999999")).thenReturn(true);

        assertThatThrownBy(() -> clienteService.update(ID, dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CNH já cadastrada");
    }

    @Test
    void update_devePermitirManterMesmoCpfECnh() {
        Cliente existente = clienteBase();
        ClienteRequestDTO dto = new ClienteRequestDTO("João Atualizado", "123.456.789-00", "12345678900", "(11) 91111-2222", null);

        when(clienteRepository.findById(ID)).thenReturn(Optional.of(existente));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        assertThatCode(() -> clienteService.update(ID, dto)).doesNotThrowAnyException();
        verify(clienteRepository, never()).existsByCpf(anyString());
        verify(clienteRepository, never()).existsByCnh(anyString());
    }

    // --- delete ---

    @Test
    void delete_deveDeletarClienteComSucesso() {
        when(clienteRepository.existsById(ID)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(ID);

        assertThatCode(() -> clienteService.delete(ID)).doesNotThrowAnyException();
        verify(clienteRepository).deleteById(ID);
    }

    @Test
    void delete_deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.existsById(UNKNOWN_ID)).thenReturn(false);

        assertThatThrownBy(() -> clienteService.delete(UNKNOWN_ID))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(clienteRepository, never()).deleteById(any());
    }
}
