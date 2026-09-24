package com.motoriz.Services;

import com.motoriz.Dtos.ClienteRequestDTO;
import com.motoriz.Dtos.ClienteResponseDTO;
import com.motoriz.Exceptions.ResourceNotFoundException;
import com.motoriz.Models.Cliente;
import com.motoriz.Repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<ClienteResponseDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteResponseDTO::from)
                .toList();
    }

    public ClienteResponseDTO findById(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: id " + id));
        return ClienteResponseDTO.from(cliente);
    }

    public ClienteResponseDTO create(ClienteRequestDTO dto) {
        if (clienteRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + dto.cpf());
        }
        if (clienteRepository.existsByCnh(dto.cnh())) {
            throw new IllegalArgumentException("CNH já cadastrada: " + dto.cnh());
        }

        Cliente cliente = Cliente.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .cnh(dto.cnh())
                .telefone(dto.telefone())
                .email(dto.email())
                .build();

        return ClienteResponseDTO.from(clienteRepository.save(cliente));
    }

    public ClienteResponseDTO update(UUID id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: id " + id));

        if (!cliente.getCpf().equals(dto.cpf()) && clienteRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + dto.cpf());
        }
        if (!cliente.getCnh().equals(dto.cnh()) && clienteRepository.existsByCnh(dto.cnh())) {
            throw new IllegalArgumentException("CNH já cadastrada: " + dto.cnh());
        }

        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setCnh(dto.cnh());
        cliente.setTelefone(dto.telefone());
        cliente.setEmail(dto.email());

        return ClienteResponseDTO.from(clienteRepository.save(cliente));
    }

    public void delete(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado: id " + id);
        }
        clienteRepository.deleteById(id);
    }
}
