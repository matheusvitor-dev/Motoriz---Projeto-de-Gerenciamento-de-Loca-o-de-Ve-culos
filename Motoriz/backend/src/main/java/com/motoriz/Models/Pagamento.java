package com.motoriz.Models;

import com.motoriz.Enums.MetodoPagamento;
import com.motoriz.Enums.PagamentoStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locacao_id", nullable = false)
    private Locacao locacao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento")
    private MetodoPagamento metodoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PagamentoStatus status = PagamentoStatus.PENDENTE;

    @Column(name = "parcela_numero")
    @Builder.Default
    private Integer parcelaNumero = 1;

    @Column(name = "total_parcelas")
    @Builder.Default
    private Integer totalParcelas = 1;

    @Column(name = "motivo_recusa", length = 255)
    private String motivoRecusa;

    // Campos de integração n8n
    @Column(name = "notificacao_enviada")
    @Builder.Default
    private Boolean notificacaoEnviada = false;

    @Column(name = "data_ultima_notificacao")
    private LocalDateTime dataUltimaNotificacao;

    @Column(name = "link_boleto_pix", length = 255)
    private String linkBoletoPix;
}