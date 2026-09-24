package com.motoriz.Services;

import com.motoriz.Dtos.VeiculoRequestDTO;
import com.motoriz.Dtos.VeiculoResponseDTO;
import com.motoriz.Enums.VeiculoStatus;
import com.motoriz.Exceptions.ResourceNotFoundException;
import com.motoriz.Models.Veiculo;
import com.motoriz.Repositories.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public List<VeiculoResponseDTO> findAll() {
        return veiculoRepository.findAll()
                .stream()
                .map(VeiculoResponseDTO::from)
                .toList();
    }

    public List<VeiculoResponseDTO> findByStatus(VeiculoStatus status) {
        return veiculoRepository.findByStatus(status)
                .stream()
                .map(VeiculoResponseDTO::from)
                .toList();
    }

    public VeiculoResponseDTO findById(UUID id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado: id " + id));
        return VeiculoResponseDTO.from(veiculo);
    }

    public VeiculoResponseDTO create(VeiculoRequestDTO dto) {
        if (veiculoRepository.existsByPlaca(dto.placa())) {
            throw new IllegalArgumentException("Placa já cadastrada: " + dto.placa());
        }

        Veiculo veiculo = Veiculo.builder()
                .modelo(dto.modelo())
                .marca(dto.marca())
                .placa(dto.placa())
                .ano(dto.ano())
                .cor(dto.cor())
                .quilometragemAtual(dto.quilometragemAtual() != null ? dto.quilometragemAtual() : 0)
                .status(dto.status() != null ? dto.status() : VeiculoStatus.DISPONIVEL)
                .localizacaoAtual(dto.localizacaoAtual() != null ? dto.localizacaoAtual() : "Pátio Central")
                .kmProximaRevisao(dto.kmProximaRevisao())
                .build();

        return VeiculoResponseDTO.from(veiculoRepository.save(veiculo));
    }

    public VeiculoResponseDTO update(UUID id, VeiculoRequestDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado: id " + id));

        if (!veiculo.getPlaca().equals(dto.placa()) && veiculoRepository.existsByPlaca(dto.placa())) {
            throw new IllegalArgumentException("Placa já cadastrada: " + dto.placa());
        }

        veiculo.setModelo(dto.modelo());
        veiculo.setMarca(dto.marca());
        veiculo.setPlaca(dto.placa());
        veiculo.setAno(dto.ano());
        veiculo.setCor(dto.cor());
        if (dto.quilometragemAtual() != null) veiculo.setQuilometragemAtual(dto.quilometragemAtual());
        if (dto.status() != null) veiculo.setStatus(dto.status());
        if (dto.localizacaoAtual() != null) veiculo.setLocalizacaoAtual(dto.localizacaoAtual());
        veiculo.setKmProximaRevisao(dto.kmProximaRevisao());

        return VeiculoResponseDTO.from(veiculoRepository.save(veiculo));
    }

    public void delete(UUID id) {
        if (!veiculoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Veículo não encontrado: id " + id);
        }
        veiculoRepository.deleteById(id);
    }
}
