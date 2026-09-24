package com.motoriz.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motoriz.Dtos.ClienteRequestDTO;
import com.motoriz.Dtos.ClienteResponseDTO;
import com.motoriz.Exceptions.ResourceNotFoundException;
import com.motoriz.Security.SecurityConfig;
import com.motoriz.Services.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
@Import(SecurityConfig.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final UUID ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final UUID UNKNOWN_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private ClienteResponseDTO responseBase() {
        return new ClienteResponseDTO(ID, "João Silva", "123.456.789-00", "12345678900",
                "(11) 99999-9999", "joao@email.com", LocalDateTime.of(2024, 1, 15, 10, 30));
    }

    private ClienteRequestDTO requestBase() {
        return new ClienteRequestDTO("João Silva", "123.456.789-00", "12345678900", "(11) 99999-9999", "joao@email.com");
    }

    // --- GET /api/clientes ---

    @Test
    void findAll_deveRetornar200ComListaDeClientes() throws Exception {
        when(clienteService.findAll()).thenReturn(List.of(responseBase()));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ID.toString()))
                .andExpect(jsonPath("$[0].nome").value("João Silva"))
                .andExpect(jsonPath("$[0].cpf").value("123.456.789-00"));
    }

    @Test
    void findAll_deveRetornar200ComListaVazia() throws Exception {
        when(clienteService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // --- GET /api/clientes/{id} ---

    @Test
    void findById_deveRetornar200QuandoClienteEncontrado() throws Exception {
        when(clienteService.findById(ID)).thenReturn(responseBase());

        mockMvc.perform(get("/api/clientes/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cnh").value("12345678900"));
    }

    @Test
    void findById_deveRetornar404QuandoNaoEncontrado() throws Exception {
        when(clienteService.findById(UNKNOWN_ID))
                .thenThrow(new ResourceNotFoundException("Cliente não encontrado: id " + UNKNOWN_ID));

        mockMvc.perform(get("/api/clientes/{id}", UNKNOWN_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente não encontrado: id " + UNKNOWN_ID));
    }

    // --- POST /api/clientes ---

    @Test
    void create_deveRetornar201ComClienteCriado() throws Exception {
        when(clienteService.create(any(ClienteRequestDTO.class))).thenReturn(responseBase());

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBase())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    void create_deveRetornar400QuandoCpfDuplicado() throws Exception {
        when(clienteService.create(any(ClienteRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("CPF já cadastrado: 123.456.789-00"));

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBase())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("CPF já cadastrado: 123.456.789-00"));
    }

    @Test
    void create_deveRetornar400QuandoCnhDuplicada() throws Exception {
        when(clienteService.create(any(ClienteRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("CNH já cadastrada: 12345678900"));

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBase())))
                .andExpect(status().isBadRequest());
    }

    // --- PUT /api/clientes/{id} ---

    @Test
    void update_deveRetornar200ComClienteAtualizado() throws Exception {
        when(clienteService.update(eq(ID), any(ClienteRequestDTO.class))).thenReturn(responseBase());

        mockMvc.perform(put("/api/clientes/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBase())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID.toString()));
    }

    @Test
    void update_deveRetornar404QuandoClienteNaoEncontrado() throws Exception {
        when(clienteService.update(eq(UNKNOWN_ID), any(ClienteRequestDTO.class)))
                .thenThrow(new ResourceNotFoundException("Cliente não encontrado: id " + UNKNOWN_ID));

        mockMvc.perform(put("/api/clientes/{id}", UNKNOWN_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBase())))
                .andExpect(status().isNotFound());
    }

    // --- DELETE /api/clientes/{id} ---

    @Test
    void delete_deveRetornar204QuandoDeletadoComSucesso() throws Exception {
        doNothing().when(clienteService).delete(ID);

        mockMvc.perform(delete("/api/clientes/{id}", ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_deveRetornar404QuandoClienteNaoEncontrado() throws Exception {
        doThrow(new ResourceNotFoundException("Cliente não encontrado: id " + UNKNOWN_ID))
                .when(clienteService).delete(UNKNOWN_ID);

        mockMvc.perform(delete("/api/clientes/{id}", UNKNOWN_ID))
                .andExpect(status().isNotFound());
    }
}
