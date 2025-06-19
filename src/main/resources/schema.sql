-- Script SQL para criação da tabela de clientes no PostgreSQL
-- Execute este script quando conectar ao banco PostgreSQL

CREATE TABLE IF NOT EXISTS clientes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    endereco VARCHAR(200),
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índices para melhorar a performance das consultas
CREATE INDEX IF NOT EXISTS idx_clientes_nome ON clientes(nome);
CREATE INDEX IF NOT EXISTS idx_clientes_email ON clientes(email);
CREATE INDEX IF NOT EXISTS idx_clientes_data_criacao ON clientes(data_criacao);

-- Dados de exemplo (opcional)
INSERT INTO clientes (nome, email, telefone, endereco) VALUES
('João Silva', 'joao.silva@email.com', '(11) 99999-1111', 'Rua das Flores, 123 - São Paulo, SP'),
('Maria Santos', 'maria.santos@email.com', '(21) 88888-2222', 'Av. Copacabana, 456 - Rio de Janeiro, RJ'),
('Pedro Oliveira', 'pedro.oliveira@email.com', '(31) 77777-3333', 'Rua da Liberdade, 789 - Belo Horizonte, MG'),
('Ana Costa', 'ana.costa@email.com', '(41) 66666-4444', 'Rua XV de Novembro, 321 - Curitiba, PR'),
('Carlos Ferreira', 'carlos.ferreira@email.com', '(51) 55555-5555', 'Av. Ipiranga, 654 - Porto Alegre, RS')
ON CONFLICT (email) DO NOTHING;

