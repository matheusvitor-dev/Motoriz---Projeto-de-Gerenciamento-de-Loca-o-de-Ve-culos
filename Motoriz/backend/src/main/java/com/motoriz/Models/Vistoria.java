package com.motoriz.Models;

import com.motoriz.Enums.VistoriaTipo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vistorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vistoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locacao_id", nullable = false)
    private Locacao locacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VistoriaTipo tipo;

    @Column(name = "data_vistoria", updatable = false)
    private LocalDateTime dataVistoria;

    @Column(name = "nivel_combustivel", length = 20)
    private String nivelCombustivel;

    private Integer quilometragem;

    @Column(name = "observacoes_danos", columnDefinition = "TEXT")
    private String observacoesDanos;

    @Column(name = "fotos_url", columnDefinition = "json")
    private String fotosUrl;

    @PrePersist
    private void prePersist() {
        if (dataVistoria == null) dataVistoria = LocalDateTime.now();
    }
}