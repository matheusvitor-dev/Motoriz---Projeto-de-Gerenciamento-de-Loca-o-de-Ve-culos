package com.motoriz.Repositories;

import com.motoriz.Models.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        entityManager.persistAndFlush(
                Cliente.builder()
                        .nome("João Silva")
                        .cpf("123.456.789-00")
                        .cnh("12345678900")
                        .telefone("(11) 99999-9999")
                        .email("joao@email.com")
                        .build()
        );
    }

    // --- findByCpf ---

    @Test
    void findByCpf_deveEncontrarClientePeloCpf() {
        Optional<Cliente> resultado = clienteRepository.findByCpf("123.456.789-00");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNome()).isEqualTo("João Silva");
        assertThat(resultado.get().getCnh()).isEqualTo("12345678900");
    }

    @Test
    void findByCpf_deveRetornarVazioParaCpfNaoExistente() {
        Optional<Cliente> resultado = clienteRepository.findByCpf("000.000.000-00");

        assertThat(resultado).isEmpty();
    }

    // --- existsByCpf ---

    @Test
    void existsByCpf_deveRetornarTrueParaCpfExistente() {
        assertThat(clienteRepository.existsByCpf("123.456.789-00")).isTrue();
    }

    @Test
    void existsByCpf_deveRetornarFalseParaCpfNaoExistente() {
        assertThat(clienteRepository.existsByCpf("000.000.000-00")).isFalse();
    }

    // --- existsByCnh ---

    @Test
    void existsByCnh_deveRetornarTrueParaCnhExistente() {
        assertThat(clienteRepository.existsByCnh("12345678900")).isTrue();
    }

    @Test
    void existsByCnh_deveRetornarFalseParaCnhNaoExistente() {
        assertThat(clienteRepository.existsByCnh("99999999999")).isFalse();
    }

    // --- Persistência geral ---

    @Test
    void save_deveDefinirDataCadastroAutomaticamente() {
        Cliente cliente = clienteRepository.findByCpf("123.456.789-00").orElseThrow();

        assertThat(cliente.getDataCadastro()).isNotNull();
    }

    @Test
    void save_deveRespeitarUnicidadeDeCpf() {
        Cliente duplicado = Cliente.builder()
                .nome("Outro Nome")
                .cpf("123.456.789-00")
                .cnh("99999999999")
                .build();

        org.junit.jupiter.api.Assertions.assertThrows(
                Exception.class,
                () -> entityManager.persistAndFlush(duplicado)
        );
    }
}
