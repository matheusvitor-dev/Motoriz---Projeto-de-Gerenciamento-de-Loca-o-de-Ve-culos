package com.motoriz.Services;

import com.motoriz.Dtos.VeiculoRequestDTO;
import com.motoriz.Dtos.VeiculoResponseDTO;
import com.motoriz.Enums.VeiculoStatus;
import com.motoriz.Exceptions.ResourceNotFoundException;
import com.motoriz.Models.Veiculo;
import com.motoriz.Repositories.VeiculoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoService veiculoService;

    private static final UUID ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final UUID UNKNOWN_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private Veiculo veiculoBase() {
        return Veiculo.builder()
                .id(ID)
                .modelo("Onix")
                .marca("Chevrolet")
                .placa("ABC-1234")
                .ano(2022)
                .cor("Branco")
                .quilometragemAtual(15000)
                .status(VeiculoStatus.DISPONIVEL)
                .localizacaoAtual("Pátio Central")
                .kmProximaRevisao(20000)
                .build();
    }

    private VeiculoRequestDTO requestBase() {
        return new VeiculoRequestDTO("Onix", "Chevrolet", "ABC-1234", 2022, "Branco", 15000, VeiculoStatus.DISPONIVEL, "Pátio Central", 20000);
    }

    // --- findAll ---

    @Test
    void findAll_deveRetornarListaDeVeiculos() {
        when(veiculoRepository.findAll()).thenReturn(List.of(veiculoBase()));

        List<VeiculoResponseDTO> resultado = veiculoService.findAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).modelo()).isEqualTo("Onix");
        assertThat(resultado.get(0).placa()).isEqualTo("ABC-1234");
    }

    @Test
    void findAll_deveRetornarListaVaziaQuandoNaoHaVeiculos() {
        when(veiculoRepository.findAll()).thenReturn(List.of());

        assertThat(veiculoService.findAll()).isEmpty();
    }

    // --- findByStatus ---

    @Test
    void findByStatus_deveRetornarVeiculosFiltradosPorStatus() {
        when(veiculoRepository.findByStatus(VeiculoStatus.DISPONIVEL)).thenReturn(List.of(veiculoBase()));

        List<VeiculoResponseDTO> resultado = veiculoService.findByStatus(VeiculoStatus.DISPONIVEL);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).status()).isEqualTo(VeiculoStatus.DISPONIVEL);
    }

    @Test
    void findByStatus_deveRetornarListaVaziaQuandoNaoHaVeiculosNoStatus() {
        when(veiculoRepository.findByStatus(VeiculoStatus.MANUTENCAO)).thenReturn(List.of());

        assertThat(veiculoService.findByStatus(VeiculoStatus.MANUTENCAO)).isEmpty();
    }

    // --- findById ---

    @Test
    void findById_deveRetornarVeiculoQuandoEncontrado() {
        when(veiculoRepository.findById(ID)).thenReturn(Optional.of(veiculoBase()));

        VeiculoResponseDTO resultado = veiculoService.findById(ID);

        assertThat(resultado.id()).isEqualTo(ID);
        assertThat(resultado.modelo()).isEqualTo("Onix");
        assertThat(resultado.status()).isEqualTo(VeiculoStatus.DISPONIVEL);
    }

    @Test
    void findById_deveLancarExcecaoQuandoNaoEncontrado() {
        when(veiculoRepository.findById(UNKNOWN_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> veiculoService.findById(UNKNOWN_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(UNKNOWN_ID.toString());
    }

    // --- create ---

    @Test
    void create_deveCriarVeiculoComSucesso() {
        when(veiculoRepository.existsByPlaca("ABC-1234")).thenReturn(false);
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculoBase());

        VeiculoResponseDTO resultado = veiculoService.create(requestBase());

        assertThat(resultado.modelo()).isEqualTo("Onix");
        assertThat(resultado.placa()).isEqualTo("ABC-1234");
        verify(veiculoRepository).save(any(Veiculo.class));
    }

    @Test
    void create_deveCriarVeiculoComValoresPadrao() {
        VeiculoRequestDTO dto = new VeiculoRequestDTO("HB20", "Hyundai", "XYZ-9999", 2023, null, null, null, null, null);

        when(veiculoRepository.existsByPlaca("XYZ-9999")).thenReturn(false);
        when(veiculoRepository.save(any(Veiculo.class))).thenAnswer(inv -> {
            Veiculo v = inv.getArgument(0);
            v.setId(UUID.randomUUID());
            return v;
        });

        VeiculoResponseDTO resultado = veiculoService.create(dto);

        assertThat(resultado.quilometragemAtual()).isEqualTo(0);
        assertThat(resultado.status()).isEqualTo(VeiculoStatus.DISPONIVEL);
        assertThat(resultado.localizacaoAtual()).isEqualTo("Pátio Central");
    }

    @Test
    void create_deveLancarExcecaoQuandoPlacaJaCadastrada() {
        when(veiculoRepository.existsByPlaca("ABC-1234")).thenReturn(true);

        assertThatThrownBy(() -> veiculoService.create(requestBase()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Placa já cadastrada");

        verify(veiculoRepository, never()).save(any());
    }

    // --- update ---

    @Test
    void update_deveAtualizarVeiculoComSucesso() {
        Veiculo existente = veiculoBase();
        VeiculoRequestDTO dto = new VeiculoRequestDTO("Onix Plus", "Chevrolet", "ABC-1234", 2022, "Prata", 16000, VeiculoStatus.ALUGADO, "Filial Norte", 22000);
        Veiculo atualizado = Veiculo.builder().id(ID).modelo("Onix Plus").marca("Chevrolet").placa("ABC-1234")
                .ano(2022).cor("Prata").quilometragemAtual(16000).status(VeiculoStatus.ALUGADO)
                .localizacaoAtual("Filial Norte").kmProximaRevisao(22000).build();

        when(veiculoRepository.findById(ID)).thenReturn(Optional.of(existente));
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(atualizado);

        VeiculoResponseDTO resultado = veiculoService.update(ID, dto);

        assertThat(resultado.modelo()).isEqualTo("Onix Plus");
        assertThat(resultado.status()).isEqualTo(VeiculoStatus.ALUGADO);
        assertThat(resultado.cor()).isEqualTo("Prata");
    }

    @Test
    void update_deveLancarExcecaoQuandoVeiculoNaoEncontrado() {
        when(veiculoRepository.findById(UNKNOWN_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> veiculoService.update(UNKNOWN_ID, requestBase()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_deveLancarExcecaoQuandoPlacaJaEmUsoPorOutroVeiculo() {
        Veiculo existente = veiculoBase();
        VeiculoRequestDTO dto = new VeiculoRequestDTO("Onix", "Chevrolet", "ZZZ-9999", 2022, "Branco", 15000, VeiculoStatus.DISPONIVEL, "Pátio Central", 20000);

        when(veiculoRepository.findById(ID)).thenReturn(Optional.of(existente));
        when(veiculoRepository.existsByPlaca("ZZZ-9999")).thenReturn(true);

        assertThatThrownBy(() -> veiculoService.update(ID, dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Placa já cadastrada");
    }

    @Test
    void update_devePermitirManterMesmaPlaca() {
        Veiculo existente = veiculoBase();
        VeiculoRequestDTO dto = new VeiculoRequestDTO("Onix", "Chevrolet", "ABC-1234", 2022, "Prata", 15000, VeiculoStatus.DISPONIVEL, "Pátio Central", 20000);

        when(veiculoRepository.findById(ID)).thenReturn(Optional.of(existente));
        when(veiculoRepository.save(any(Veiculo.class))).thenAnswer(inv -> inv.getArgument(0));

        assertThatCode(() -> veiculoService.update(ID, dto)).doesNotThrowAnyException();
        verify(veiculoRepository, never()).existsByPlaca(anyString());
    }

    // --- delete ---

    @Test
    void delete_deveDeletarVeiculoComSucesso() {
        when(veiculoRepository.existsById(ID)).thenReturn(true);
        doNothing().when(veiculoRepository).deleteById(ID);

        assertThatCode(() -> veiculoService.delete(ID)).doesNotThrowAnyException();
        verify(veiculoRepository).deleteById(ID);
    }

    @Test
    void delete_deveLancarExcecaoQuandoVeiculoNaoEncontrado() {
        when(veiculoRepository.existsById(UNKNOWN_ID)).thenReturn(false);

        assertThatThrownBy(() -> veiculoService.delete(UNKNOWN_ID))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(veiculoRepository, never()).deleteById(any());
    }
}
