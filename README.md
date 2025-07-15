# 📊 Desafio Técnico RPE – Gestão de Clientes e Faturas

Este projeto implementa um pequeno sistema de gestão de **Clientes**, **Faturas**, para uma fintech fictícia, conforme o escopo do desafio:

---

## 📋 Visão Geral

Esta aplicação implementa um sistema full‑stack simples para:

1. **Gerenciar Clientes**  
   - Criar, listar, atualizar e bloquear clientes inadimplentes  
2. **Controlar Faturas**  
   - Listar faturas por cliente, registrar pagamento e sinalizar atrasos  
3. **Regras Automáticas**  
   - Toda fatura com mais de 3 dias de atraso é marcada como “ATRASADA” e bloqueia o cliente  
   - Clientes bloqueados têm seu limite de crédito zerado  
   - Scheduler diário para verificação de atrasos

---

## 🛠️ Tecnologias Utilizadas

| Camada    | Tecnologias                                                        |
|-----------|--------------------------------------------------------------------|
| **Back‑End**  | ☕ Java 21 • 🌐 Spring Boot • 📦 Spring Data JPA • 🛠️ Flyway • 📖 Swagger • 🧪 JUnit  |
| **Banco**     | 🗃️ PostgreSQL                                                      |
| **Front‑End** | 💻 HTML5 • 🎨 CSS3 • 🌐 JavaScript (Fetch API)                           |
| **Infra**     | 🐳 Docker • ⚓ Docker Compose                                         |

---

## 🚀 Funcionalidades Principais

### Clientes
- **GET** `/api/clientes` — lista todos  
- **POST** `/api/clientes` — cria novo  
- **GET** `/api/clientes/{id}` — busca por ID  
- **PUT** `/api/clientes/{id}` — atualiza dados ou bloqueia  
- **GET** `/api/clientes/bloqueados` — lista bloqueados  

### Faturas
- **GET** `/api/faturas/clients/{id}` — faturas de um cliente  
- **PUT** `/api/faturas/{id}/pay` — registra pagamento  
- **GET** `/api/faturas/atrasadas` — lista faturas atrasadas  

---

## 🗄️ Banco de Dados & Migrações

- **Flyway** cria e versiona os scripts em `src/main/resources/db/migration/`:
  - **V1__create_tables.sql** — cria tabelas `cliente` e `fatura`  
  - **V2__seed_data.sql** — insere 10 registros de exemplo  
- Queries úteis:
  ```sql
  -- Clientes bloqueados há mais de 3 dias
  SELECT c.*
  FROM cliente c
  JOIN fatura f ON f.cliente_id = c.id
  WHERE f.status = 'A'
    AND f.data_vencimento < CURRENT_DATE - INTERVAL '3 days'
    AND c.status_bloqueio = 'B';

  -- Zera limite de crédito de clientes bloqueados
  UPDATE cliente
  SET limite_credito = 0
  WHERE status_bloqueio = 'B';

## 🛠️ Como Executar Localmente

1. **Clone este repositório**  
   ```bash
   git clone https://github.com/SEU_USUARIO/fintech-desafio.git
   cd fintech-desafio