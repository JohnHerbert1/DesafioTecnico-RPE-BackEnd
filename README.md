# DesafioTecnico-RPE-BackEnd

# EVOLUIR – Desafio Técnico  
**Estagiário em Desenvolvimento Fullstack**

---

## 📋 Visão Geral  
Este projeto implementa um pequeno sistema de gestão de **Clientes**, **Faturas** e **Pagamentos** para uma fintech fictícia, conforme o escopo do desafio:

1. **Banco de Dados**  
   - Tabelas: `cliente`, `fatura`  
   - Scripts de população com 10 registros de exemplo  
   - Query para listar clientes bloqueados há mais de 3 dias  
   - Script para zerar limite de crédito de clientes bloqueados  

2. **Back‑End**  
   - **Tecnologias**: Java 21, Spring Boot, Spring Data JPA, PostgreSQL  
   - **Documentação**: Swagger UI disponível em `/swagger-ui.html`  
   - **Testes unitários**: JUnit + MockMvc  
   - **Endpoints**:
     - **Clientes**  
       - `GET /clientes` — lista todos  
       - `POST /clientes` — cria novo  
       - `GET /clientes/{id}` — consulta por ID  
       - `PUT /clientes/{id}` — atualiza dados ou bloqueia  
       - `GET /clientes/bloqueados` — lista bloqueados  
     - **Faturas**  
       - `GET /faturas/clients/{id}` — lista faturas de um cliente  
       - `PUT /faturas/{id}/pay` — registra pagamento  
       - `GET /faturas/atrasadas` — lista faturas com >3 dias de atraso  

   - **Regras de negócio**:
     1. Ao pagar uma fatura, seu status vai para **PAGA**.  
     2. Faturas com mais de 3 dias de atraso mudam para **ATRASADA** e bloqueiam o cliente.  
     3. Clientes bloqueados têm `limite_credito = 0`.  

3. **Front‑End**  
   - **HTML/CSS** + **JavaScript** (fetch API)  
   - **Telas**:  
     1. **Listagem de Clientes**  
        - Colunas: Nome, CPF, Idade, Status, Limite de Crédito  
        - Botão “Ver Faturas”  
     2. **Faturas do Cliente**  
        - Colunas: Valor, Vencimento, Status, Data de Pagamento  
        - Botão “Registrar pagamento”  

4. **Docker & Docker‑Compose**  
   - **`Dockerfile`** para empacotar o backend Spring Boot  
   - **`docker-compose.yml`** para orquestrar:
     - Serviço **app** (container Java)
     - Serviço **db** (PostgreSQL)

---

## 🚀 Tecnologias Utilizadas

- **Java 21**  
- **Spring Boot** (Web, Data JPA, Validation, Scheduler)  
- **PostgreSQL**  
- **Flyway** (migrações SQL)  
- **JUnit 5**, **MockMvc**  
- **Swagger** (OpenAPI)  
- **HTML5**, **CSS3**, **Vanilla JS**  
- **Docker**, **Docker Compose**

---

## 🛠️ Como Executar Localmente

1. **Clone este repositório**  
   ```bash
   git clone https://github.com/SEU_USUARIO/fintech-desafio.git
   cd fintech-desafio
