package com.motoriz.Dtos;

import com.motoriz.Enums.VeiculoStatus;

public record VeiculoRequestDTO(
        String modelo,
        String marca,
        String placa,
        Integer ano,
        String cor,
        Integer quilometragemAtual,
        VeiculoStatus status,
        String localizacaoAtual,
        Integer kmProximaRevisao
) {}
