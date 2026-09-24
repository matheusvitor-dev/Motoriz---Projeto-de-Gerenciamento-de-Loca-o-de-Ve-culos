# Motoriz API — Documentação

## Base URL

| Ambiente | URL |
|----------|-----|
| Local (sem Docker) | `http://localhost:8080` |
| Docker Compose | `http://motoriz_backend:8080` (rede interna) |

> Todos os endpoints retornam e consomem `application/json`.  
> Autenticação: não requerida no momento (todos os endpoints são públicos).

---

## Clientes

### Listar todos os clientes

```
GET /api/clientes
```

**Response 200**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "cpf": "123.456.789-00",
    "cnh": "12345678900",
    "telefone": "(11) 99999-9999",
    "email": "joao@email.com",
    "dataCadastro": "2024-01-15T10:30:00"
  }
]
```

---

### Buscar cliente por ID

```
GET /api/clientes/{id}
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `id` | Integer | ID do cliente |

**Response 200**
```json
{
  "id": 1,
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "cnh": "12345678900",
  "telefone": "(11) 99999-9999",
  "email": "joao@email.com",
  "dataCadastro": "2024-01-15T10:30:00"
}
```

**Response 404**
```json
{
  "message": "Cliente não encontrado com id: 1"
}
```

---

### Criar cliente

```
POST /api/clientes
```

**Request Body**
```json
{
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "cnh": "12345678900",
  "telefone": "(11) 99999-9999",
  "email": "joao@email.com"
}
```

| Campo | Tipo | Obrigatório | Regras |
|-------|------|-------------|--------|
| `nome` | String (max 100) | Sim | — |
| `cpf` | String (max 14) | Sim | Único no sistema |
| `cnh` | String (max 20) | Sim | Único no sistema |
| `telefone` | String (max 15) | Não | — |
| `email` | String (max 100) | Não | — |

**Response 201**
```json
{
  "id": 1,
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "cnh": "12345678900",
  "telefone": "(11) 99999-9999",
  "email": "joao@email.com",
  "dataCadastro": "2024-01-15T10:30:00"
}
```

**Response 400** — CPF ou CNH já cadastrado
```json
{
  "message": "CPF já cadastrado"
}
```

---

### Atualizar cliente

```
PUT /api/clientes/{id}
```

**Request Body** — mesma estrutura do `POST /api/clientes`

**Response 200** — retorna o cliente atualizado  
**Response 404** — cliente não encontrado  
**Response 400** — CPF ou CNH já pertence a outro cliente

---

### Deletar cliente

```
DELETE /api/clientes/{id}
```

**Response 204** — sem conteúdo  
**Response 404** — cliente não encontrado

---

## Veículos

### Listar todos os veículos

```
GET /api/veiculos
```

**Response 200**
```json
[
  {
    "id": 1,
    "modelo": "Onix",
    "marca": "Chevrolet",
    "placa": "ABC-1234",
    "ano": 2022,
    "cor": "Branco",
    "quilometragemAtual": 15000,
    "status": "DISPONIVEL",
    "localizacaoAtual": "Pátio Central",
    "kmProximaRevisao": 20000
  }
]
```

---

### Listar veículos por status

```
GET /api/veiculos/status/{status}
```

| Parâmetro | Tipo | Valores aceitos |
|-----------|------|-----------------|
| `status` | String | `DISPONIVEL`, `ALUGADO`, `MANUTENCAO` |

**Response 200** — lista de veículos com o status informado

---

### Buscar veículo por ID

```
GET /api/veiculos/{id}
```

**Response 200**
```json
{
  "id": 1,
  "modelo": "Onix",
  "marca": "Chevrolet",
  "placa": "ABC-1234",
  "ano": 2022,
  "cor": "Branco",
  "quilometragemAtual": 15000,
  "status": "DISPONIVEL",
  "localizacaoAtual": "Pátio Central",
  "kmProximaRevisao": 20000
}
```

**Response 404**
```json
{
  "message": "Veículo não encontrado com id: 1"
}
```

---

### Criar veículo

```
POST /api/veiculos
```

**Request Body**
```json
{
  "modelo": "Onix",
  "marca": "Chevrolet",
  "placa": "ABC-1234",
  "ano": 2022,
  "cor": "Branco",
  "quilometragemAtual": 15000,
  "status": "DISPONIVEL",
  "localizacaoAtual": "Pátio Central",
  "kmProximaRevisao": 20000
}
```

| Campo | Tipo | Obrigatório | Padrão | Regras |
|-------|------|-------------|--------|--------|
| `modelo` | String (max 50) | Sim | — | — |
| `marca` | String (max 50) | Sim | — | — |
| `placa` | String (max 10) | Sim | — | Única no sistema |
| `ano` | Integer | Sim | — | — |
| `cor` | String (max 20) | Não | — | — |
| `quilometragemAtual` | Integer | Não | `0` | — |
| `status` | VeiculoStatus | Não | `DISPONIVEL` | Ver enum abaixo |
| `localizacaoAtual` | String (max 100) | Não | `"Pátio Central"` | — |
| `kmProximaRevisao` | Integer | Não | — | — |

**Response 201** — retorna o veículo criado  
**Response 400** — placa já cadastrada

---

### Atualizar veículo

```
PUT /api/veiculos/{id}
```

**Request Body** — mesma estrutura do `POST /api/veiculos`

**Response 200** — retorna o veículo atualizado  
**Response 404** — veículo não encontrado  
**Response 400** — placa já pertence a outro veículo

---

### Deletar veículo

```
DELETE /api/veiculos/{id}
```

**Response 204** — sem conteúdo  
**Response 404** — veículo não encontrado

---

## Enums

### VeiculoStatus

| Valor | Descrição |
|-------|-----------|
| `DISPONIVEL` | Veículo disponível para locação |
| `ALUGADO` | Veículo em locação ativa |
| `MANUTENCAO` | Veículo em manutenção |

---

## Erros comuns

| HTTP | Situação |
|------|----------|
| `400 Bad Request` | Dados inválidos ou violação de unicidade (CPF, CNH, placa) |
| `404 Not Found` | Recurso não encontrado pelo ID informado |
| `500 Internal Server Error` | Erro interno do servidor |

---

## Exemplos com cURL

### Criar um cliente
```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Souza",
    "cpf": "987.654.321-00",
    "cnh": "98765432100",
    "telefone": "(21) 98888-7777",
    "email": "maria@email.com"
  }'
```

### Listar veículos disponíveis
```bash
curl http://localhost:8080/api/veiculos/status/DISPONIVEL
```

### Atualizar status de um veículo (id=3)
```bash
curl -X PUT http://localhost:8080/api/veiculos/3 \
  -H "Content-Type: application/json" \
  -d '{
    "modelo": "HB20",
    "marca": "Hyundai",
    "placa": "XYZ-5678",
    "ano": 2023,
    "quilometragemAtual": 8000,
    "status": "MANUTENCAO"
  }'
```

### Deletar um cliente (id=2)
```bash
curl -X DELETE http://localhost:8080/api/clientes/2
```
