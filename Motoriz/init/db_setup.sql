-- Garante a criação do banco
CREATE DATABASE IF NOT EXISTS motoriz;
USE motoriz;

-- 1. Clientes (Não depende de ninguém)
CREATE TABLE IF NOT EXISTS clientes (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    cnh VARCHAR(20) UNIQUE NOT NULL,
    telefone VARCHAR(15),
    email VARCHAR(100),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Veículos (Não depende de ninguém)
CREATE TABLE IF NOT EXISTS veiculos (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    modelo VARCHAR(50) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    placa VARCHAR(10) UNIQUE NOT NULL,
    ano INT NOT NULL,
    cor VARCHAR(20),
    quilometragem_atual INT DEFAULT 0,
    status ENUM('DISPONIVEL', 'ALUGADO', 'MANUTENCAO') DEFAULT 'DISPONIVEL',
    localizacao_atual VARCHAR(100) DEFAULT 'Pátio Central',
    km_proxima_revisao INT
);

-- 3. Locações (Depende de Clientes e Veículos)
CREATE TABLE IF NOT EXISTS locacoes (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    cliente_id CHAR(36) NOT NULL,
    veiculo_id CHAR(36) NOT NULL,
    data_retirada DATETIME NOT NULL,
    data_devolucao_prevista DATETIME NOT NULL,
    data_devolucao_real DATETIME,
    km_inicial INT NOT NULL,
    km_final INT,
    combustivel_retirada VARCHAR(20),
    combustivel_devolucao VARCHAR(20),
    valor_total DECIMAL(10, 2),
    status ENUM('EM_ANDAMENTO', 'FINALIZADO', 'CANCELADO') DEFAULT 'EM_ANDAMENTO',
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id)
);

-- 4. Pagamentos (Depende de Locações - Já com campos do n8n)
CREATE TABLE IF NOT EXISTS pagamentos (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    locacao_id CHAR(36) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    data_vencimento DATE NOT NULL,
    data_pagamento DATETIME,
    metodo_pagamento ENUM('CARTAO_CREDITO', 'CARTAO_DEBITO', 'PIX', 'BOLETO', 'DINHEIRO'),
    status ENUM('PENDENTE', 'PAGO', 'ATRASADO', 'RECUSADO', 'CANCELADO') DEFAULT 'PENDENTE',
    parcela_numero INT DEFAULT 1,
    total_parcelas INT DEFAULT 1,
    motivo_recusa VARCHAR(255),

    -- Campos para o robô do n8n

    notificacao_enviada BOOLEAN DEFAULT FALSE,
    data_ultima_notificacao DATETIME,
    link_boleto_pix VARCHAR(255),

    FOREIGN KEY (locacao_id) REFERENCES locacoes(id)
);

-- 5. Manutenções (Depende de Veículos)
CREATE TABLE IF NOT EXISTS manutencoes (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    veiculo_id CHAR(36) NOT NULL,
    data_manutencao DATE NOT NULL,
    descricao TEXT NOT NULL,
    custo DECIMAL(10, 2) NOT NULL,
    oficina VARCHAR(100),
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id)
);

-- 6. Vistorias (Depende de Locações)
CREATE TABLE IF NOT EXISTS vistorias (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    locacao_id CHAR(36) NOT NULL,
    tipo ENUM('RETIRADA', 'DEVOLUCAO') NOT NULL,
    data_vistoria TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nivel_combustivel VARCHAR(20),
    quilometragem INT,
    observacoes_danos TEXT,
    fotos_url JSON,
    FOREIGN KEY (locacao_id) REFERENCES locacoes(id)
);

-- 7. Multas (Depende de Veículos e Locações)
CREATE TABLE IF NOT EXISTS multas (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    veiculo_id CHAR(36) NOT NULL,
    locacao_id CHAR(36),
    codigo_infracao VARCHAR(50),
    valor DECIMAL(10, 2) NOT NULL,
    data_infracao DATETIME NOT NULL,
    status_pagamento ENUM('PENDENTE', 'REPASSADO_CLIENTE', 'PAGO') DEFAULT 'PENDENTE',
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id),
    FOREIGN KEY (locacao_id) REFERENCES locacoes(id)
);

-- 8. Seguros (Depende de Veículos)
CREATE TABLE IF NOT EXISTS seguros (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    veiculo_id CHAR(36) NOT NULL,
    seguradora VARCHAR(100),
    numero_apolice VARCHAR(50),
    data_vencimento DATE NOT NULL,
    valor_premio DECIMAL(10, 2),
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id)
);