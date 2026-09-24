package com.motoriz.Dtos;

public record ClienteRequestDTO(
        String nome,
        String cpf,
        String cnh,
        String telefone,
        String email
) {}
