package com.motoriz.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motoriz.Dtos.VeiculoRequestDTO;
import com.motoriz.Dtos.VeiculoResponseDTO;
import com.motoriz.Enums.VeiculoStatus;
import com.motoriz.Exceptions.ResourceNotFoundException;
import com.motoriz.Security.SecurityConfig;
import com.motoriz.Services.VeiculoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VeiculoController.class)
@Import(SecurityConfig.class)
class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VeiculoService veiculoService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final UUID ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final UUID UNKNOWN_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private VeiculoResponseDTO responseBase() {
        return new VeiculoResponseDTO(ID, "Onix", "Chevrolet", "ABC-1234", 2022, "Branco",
                15000, VeiculoStatus.DISPONIVEL, "Pátio Central", 20000);
    }

    private VeiculoRequestDTO requestBase() {
        return new VeiculoRequestDTO("Onix", "Chevrolet", "ABC-1234", 2022, "Branco",
                15000, VeiculoStatus.DISPONIVEL, "Pátio Central", 20000);
    }

    // --- GET /api/veiculos ---

    @Test
    void findAll_deveRetornar200ComListaDeVeiculos() throws Exception {
        when(veiculoService.findAll()).thenReturn(List.of(responseBase()));

        mockMvc.perform(get("/api/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ID.toString()))
                .andExpect(jsonPath("$[0].modelo").value("Onix"))
                .andExpect(jsonPath("$[0].placa").value("ABC-1234"))
                .andExpect(jsonPath("$[0].status").value("DISPONIVEL"));
    }

    @Test
    void findAll_deveRetornar200ComListaVazia() throws Exception {
        when(veiculoService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // --- GET /api/veiculos/status/{status} ---

    @Test
    void findByStatus_deveRetornar200ComVeiculosDisponiveis() throws Exception {
        when(veiculoService.findByStatus(VeiculoStatus.DISPONIVEL)).thenReturn(List.of(responseBase()));

        mockMvc.perform(get("/api/veiculos/status/DISPONIVEL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("DISPONIVEL"))
                .andExpect(jsonPath("$[0].placa").value("ABC-1234"));
    }

    @Test
    void findByStatus_deveRetornar200ComListaVaziaParaStatusSemVeiculos() throws Exception {
        when(veiculoService.findByStatus(VeiculoStatus.MANUTENCAO)).thenReturn(List.of());

        mockMvc.perform(get("/api/veiculos/status/MANUTENCAO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // --- GET /api/veiculos/{id} ---

    @Test
    void findById_deveRetornar200QuandoVeiculoEncontrado() throws Exception {
        when(veiculoService.findById(ID)).thenReturn(responseBase());

        mockMvc.perform(get("/api/veiculos/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.modelo").value("Onix"))
                .andExpect(jsonPath("$.marca").value("Chevrolet"))
                .andExpect(jsonPath("$.ano").value(2022));
    }

    @Test
    void findById_deveRetornar404QuandoNaoEncontrado() throws Exception {
        when(veiculoService.findById(UNKNOWN_ID))
                .thenThrow(new ResourceNotFoundException("Veículo não encontrado: id " + UNKNOWN_ID));

        mockMvc.perform(get("/api/veiculos/{id}", UNKNOWN_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Veículo não encontrado: id " + UNKNOWN_ID));
    }

    // --- POST /api/veiculos ---

    @Test
    void create_deveRetornar201ComVeiculoCriado() throws Exception {
        when(veiculoService.create(any(VeiculoRequestDTO.class))).thenReturn(responseBase());

        mockMvc.perform(post("/api/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBase())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.placa").value("ABC-1234"))
                .andExpect(jsonPath("$.quilometragemAtual").value(15000));
    }

    @Test
    void create_deveRetornar400QuandoPlacaDuplicada() throws Exception {
        when(veiculoService.create(any(VeiculoRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Placa já cadastrada: ABC-1234"));

        mockMvc.perform(post("/api/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBase())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Placa já cadastrada: ABC-1234"));
    }

    // --- PUT /api/veiculos/{id} ---

    @Test
    void update_deveRetornar200ComVeiculoAtualizado() throws Exception {
        when(veiculoService.update(eq(ID), any(VeiculoRequestDTO.class))).thenReturn(responseBase());

        mockMvc.perform(put("/api/veiculos/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBase())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID.toString()));
    }

    @Test
    void update_deveRetornar404QuandoVeiculoNaoEncontrado() throws Exception {
        when(veiculoService.update(eq(UNKNOWN_ID), any(VeiculoRequestDTO.class)))
                .thenThrow(new ResourceNotFoundException("Veículo não encontrado: id " + UNKNOWN_ID));

        mockMvc.perform(put("/api/veiculos/{id}", UNKNOWN_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBase())))
                .andExpect(status().isNotFound());
    }

    // --- DELETE /api/veiculos/{id} ---

    @Test
    void delete_deveRetornar204QuandoDeletadoComSucesso() throws Exception {
        doNothing().when(veiculoService).delete(ID);

        mockMvc.perform(delete("/api/veiculos/{id}", ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_deveRetornar404QuandoVeiculoNaoEncontrado() throws Exception {
        doThrow(new ResourceNotFoundException("Veículo não encontrado: id " + UNKNOWN_ID))
                .when(veiculoService).delete(UNKNOWN_ID);

        mockMvc.perform(delete("/api/veiculos/{id}", UNKNOWN_ID))
                .andExpect(status().isNotFound());
    }
}
