# 📊 Desafio Técnico RPE – Gestão de Clientes e Faturas

Este projeto implementa um pequeno sistema de gestão de **Clientes**, **Faturas** e **Pagamentos** para uma fintech fictícia, conforme o escopo do desafio:

---

## 📋 Visão Geral

Esta aplicação full‑stack simples permite:

### Como rodar o projeto local

1. Clone o repositório:

```bash
git clone https://github.com/JohnHerbert1/DesafioTecnico-RPE-BackEnd.git cd DesafioTecnico-RPE-BackEnd
```

1. **Gerenciar Clientes**

   * Criar, listar, atualizar e bloquear clientes inadimplentes
2. **Controlar Faturas**

   * Listar faturas por cliente, registrar pagamento e sinalizar atrasos
3. **Regras Automáticas**

   * Toda fatura com mais de **3 dias** de atraso é marcada como **ATRASADA** e bloqueia o cliente
   * Clientes bloqueados têm seu **limite de crédito zerado**
   * **Scheduler diário** roda às 00:00 para aplicar essas regras

---

## 🛠️ Tecnologias Utilizadas

| Camada        | Tecnologias                                                                          |
| ------------- | ------------------------------------------------------------------------------------ |
| **Back‑End**  | ☕ Java 21 • 🌐 Spring Boot • 📦 Spring Data JPA • 🛠️ Flyway • 📖 Swagger • 🧪 JUnit |
| **Banco**     | 💃 PostgreSQL                                                                        |
| **Front‑End** | 💻 HTML5 • 🎨 CSS3 • 🌐 JavaScript (Fetch API)                                       |
| **Infra**     | 🐳 Docker • ⚓ Docker Compose                                                         |

---

## 🚀 Funcionalidades Principais

### 🔒 Clientes

| Método | Endpoint                   | Descrição                 |
| ------ | -------------------------- | ------------------------- |
| GET    | `/api/clientes`            | Lista todos os clientes   |
| POST   | `/api/clientes`            | Cadastra novo cliente     |
| GET    | `/api/clientes/{id}`       | Consulta cliente por ID   |
| PUT    | `/api/clientes/{id}`       | Atualiza/bloqueia cliente |
| GET    | `/api/clientes/bloqueados` | Lista clientes bloqueados |

#### 🖊️ Exemplo de requisição (POST `/api/clientes`)

```json
{
  "nome": "Antonio",
  "cpf": "03937142096",
  "dataNascimento": "2003-11-27",
  "statusBloqueio": "ATIVO",
  "limiteCredito": 5000
}
```

---

### 🔑 Faturas

| Método | Endpoint                    | Descrição                     |
| ------ | --------------------------- | ----------------------------- |
| GET    | `/api/faturas/clients/{id}` | Lista faturas de um cliente   |
| PUT    | `/api/faturas/{id}/pay`     | Registra pagamento da fatura  |
| GET    | `/api/faturas/atrasadas`    | Lista faturas em atraso (>3d) |

#### 🖊️ Exemplo de requisição (GET `/api/faturas/clients/{id}`)

```json
[
  {
    "id": "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
    "nomeCliente": "Alice Silva",
    "dataVencimento": "2025-07-01",
    "dataPagamento": null,
    "valor": 500.0,
    "statusFatura": "B"
  }
]
```

---

## ▶️ Executar a Aplicação

### 🔧 Via Maven

```bash
./mvnw clean install
./mvnw spring-boot:run
```

### 🚧 Via Docker Compose

```bash
docker-compose up --build
```

### 🌐 Acesso Swagger

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🤕 Testes & Services

* `ClienteService` — cadastro, atualização, listagem de bloqueados
* `FaturaService` — pagamento, atraso e bloqueio

**Testes com JUnit 5 + MockMvc:**

* `ClienteServiceTest`
* `FaturaServiceTest`
* `ClienteControllerTest`
* `FaturaControllerTest`

**Executar todos os testes:**

```bash
./mvnw test
```

---

## 🗄️ Banco de Dados & Migrações

Scripts Flyway em `src/main/resources/db/migration/`:

* `V1__create_tables.sql` — cria tabelas `cliente` e `fatura`
* `V2__seed_data.sql` — dados de exemplo
## Enum:

[Enum Status Fatura: *A("Atrasada),P("Paga"),B("Aberto")]
[Enum Status Bloqueio: *ATIVO("A"),BLOQUEI("B")]


### 📃 Tabelas

#### Tabela `cliente`

```sql
CREATE TABLE cliente (
  id UUID PRIMARY KEY,
  nome VARCHAR(100),
  cpf VARCHAR(11) UNIQUE NOT NULL,
  data_nascimento DATE,
  status_bloqueio VARCHAR(1),
  limite_credito NUMERIC(10,2)
);
```

#### Tabela `fatura`

```sql
CREATE TABLE fatura (
  id UUID PRIMARY KEY,
  cliente_id UUID REFERENCES cliente(id),
  data_vencimento DATE,
  data_pagamento DATE,
  valor NUMERIC(10,2),
  status VARCHAR(1)
);
```

### 🔢 Queries úteis

```sql
-- Clientes bloqueados com fatura vencida > 3 dias
SELECT c.*
  FROM cliente c
  JOIN fatura f ON f.cliente_id = c.id
 WHERE f.status = 'A'
   AND f.data_vencimento < CURRENT_DATE - INTERVAL '3 days'
   AND c.status_bloqueio = 'B';

-- Zerar limite de crédito dos bloqueados
UPDATE cliente
   SET limite_credito = 0
 WHERE status_bloqueio = 'B';
```

---

## 🚧 Possíveis Melhorias Futuras

* 🔐 Autenticação e autorização (Spring Security + JWT)
* 🎨 Front‑end com React ou Bootstrap/Tailwind
* ⚖️ Tratamento de erros padronizado (RFC 7807)
---

### 📌 Link do projeto

➡️ [https://github.com/JohnHerbert1/DesafioTecnico-RPE-Front](https://github.com/JohnHerbert1/DesafioTecnico-RPE-Front)

---

## Desenvolvedor

 ## John Herbert Freire Lourenço
