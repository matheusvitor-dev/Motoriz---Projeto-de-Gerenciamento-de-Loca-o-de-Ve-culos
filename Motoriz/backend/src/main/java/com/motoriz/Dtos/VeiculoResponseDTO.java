package com.motoriz.Dtos;

import com.motoriz.Enums.VeiculoStatus;
import com.motoriz.Models.Veiculo;

import java.util.UUID;

public record VeiculoResponseDTO(
        UUID id,
        String modelo,
        String marca,
        String placa,
        Integer ano,
        String cor,
        Integer quilometragemAtual,
        VeiculoStatus status,
        String localizacaoAtual,
        Integer kmProximaRevisao
) {
    public static VeiculoResponseDTO from(Veiculo veiculo) {
        return new VeiculoResponseDTO(
                veiculo.getId(),
                veiculo.getModelo(),
                veiculo.getMarca(),
                veiculo.getPlaca(),
                veiculo.getAno(),
                veiculo.getCor(),
                veiculo.getQuilometragemAtual(),
                veiculo.getStatus(),
                veiculo.getLocalizacaoAtual(),
                veiculo.getKmProximaRevisao()
        );
    }
}
