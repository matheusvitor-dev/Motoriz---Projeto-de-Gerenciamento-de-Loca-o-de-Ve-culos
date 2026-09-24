package com.motoriz.Repositories;

import com.motoriz.Enums.VeiculoStatus;
import com.motoriz.Models.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VeiculoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @BeforeEach
    void setUp() {
        entityManager.persistAndFlush(
                Veiculo.builder()
                        .modelo("Onix")
                        .marca("Chevrolet")
                        .placa("ABC-1234")
                        .ano(2022)
                        .status(VeiculoStatus.DISPONIVEL)
                        .build()
        );
        entityManager.persistAndFlush(
                Veiculo.builder()
                        .modelo("HB20")
                        .marca("Hyundai")
                        .placa("XYZ-5678")
                        .ano(2021)
                        .status(VeiculoStatus.ALUGADO)
                        .build()
        );
    }

    // --- findByPlaca ---

    @Test
    void findByPlaca_deveEncontrarVeiculoPelaPlaca() {
        Optional<Veiculo> resultado = veiculoRepository.findByPlaca("ABC-1234");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getModelo()).isEqualTo("Onix");
        assertThat(resultado.get().getMarca()).isEqualTo("Chevrolet");
    }

    @Test
    void findByPlaca_deveRetornarVazioParaPlacaNaoExistente() {
        Optional<Veiculo> resultado = veiculoRepository.findByPlaca("ZZZ-0000");

        assertThat(resultado).isEmpty();
    }

    // --- existsByPlaca ---

    @Test
    void existsByPlaca_deveRetornarTrueParaPlacaExistente() {
        assertThat(veiculoRepository.existsByPlaca("ABC-1234")).isTrue();
    }

    @Test
    void existsByPlaca_deveRetornarFalseParaPlacaNaoExistente() {
        assertThat(veiculoRepository.existsByPlaca("ZZZ-0000")).isFalse();
    }

    // --- findByStatus ---

    @Test
    void findByStatus_deveRetornarApenasVeiculosDisponiveis() {
        List<Veiculo> resultado = veiculoRepository.findByStatus(VeiculoStatus.DISPONIVEL);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getPlaca()).isEqualTo("ABC-1234");
    }

    @Test
    void findByStatus_deveRetornarApenasVeiculosAlugados() {
        List<Veiculo> resultado = veiculoRepository.findByStatus(VeiculoStatus.ALUGADO);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getPlaca()).isEqualTo("XYZ-5678");
    }

    @Test
    void findByStatus_deveRetornarListaVaziaParaStatusSemVeiculos() {
        List<Veiculo> resultado = veiculoRepository.findByStatus(VeiculoStatus.MANUTENCAO);

        assertThat(resultado).isEmpty();
    }

    // --- Persistência geral ---

    @Test
    void save_deveRespeitarUnicidadeDePlaca() {
        Veiculo duplicado = Veiculo.builder()
                .modelo("Gol")
                .marca("Volkswagen")
                .placa("ABC-1234")
                .ano(2020)
                .build();

        org.junit.jupiter.api.Assertions.assertThrows(
                Exception.class,
                () -> entityManager.persistAndFlush(duplicado)
        );
    }

    @Test
    void save_deveAplicarValoresPadraoQuandoNaoInformados() {
        Veiculo veiculo = Veiculo.builder()
                .modelo("Polo")
                .marca("Volkswagen")
                .placa("POL-9999")
                .ano(2023)
                .build();

        Veiculo salvo = entityManager.persistAndFlush(veiculo);

        assertThat(salvo.getQuilometragemAtual()).isEqualTo(0);
        assertThat(salvo.getStatus()).isEqualTo(VeiculoStatus.DISPONIVEL);
        assertThat(salvo.getLocalizacaoAtual()).isEqualTo("Pátio Central");
    }
}
