package com.motoriz.Repositories;

import com.motoriz.Enums.VeiculoStatus;
import com.motoriz.Models.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {
    Optional<Veiculo> findByPlaca(String placa);
    boolean existsByPlaca(String placa);
    List<Veiculo> findByStatus(VeiculoStatus status);
}
