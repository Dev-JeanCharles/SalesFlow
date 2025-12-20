[![NPM](https://img.shields.io/npm/l/react)](https://github.com/Dev-JeanCharles/SalesFlow/blob/master/LICENSE)

# 📱 SalesFlow — Gestão de Planos e Vendas em Telefonia

## 📌 Visão Geral

O **SalesFlow** é um sistema back-end desenvolvido em **Java com Spring Boot**, projetado para **gerenciar a adesão, migração e ciclo de vida de planos de telecomunicação** (pré-pago, controle e pós-pago).

O sistema simula **cenários reais de operadoras**, onde cada cliente pode possuir **apenas um plano ativo por vez**, e toda a lógica de contrato, status e pagamento é centralizada no **Sales Service**.

O projeto foi construído com foco em **arquitetura limpa**, **boas práticas de engenharia de software** e **preparação para ambientes corporativos**.

---
## ▶️ Executando o Projeto Localmente

Este tutorial mostra como subir o **SalesFlow** localmente utilizando **Docker Compose**, sem a necessidade de instalar banco de dados ou serviços AWS na máquina.

---

## 📋 Pré-requisitos

Certifique-se de ter instalado:

- Docker
- Docker Compose
- Java 17
- Gradle

Verifique:

```bash
docker --version
docker-compose --version
java --version
```

## 🎯 Objetivo do Projeto

- Demonstrar domínio em **Back-end Java / Spring Boot**
- Aplicar conceitos de **DDD, Arquitetura Hexagonal e Microsserviços**
- Modelar **regras reais de negócio do setor de telecom**
- Simular **fluxos de venda, ativação, migração e cancelamento de planos**
- Servir como base para **evoluções arquiteturais futuras**

---

## 🧩 Contexto de Negócio

- Cada **Pessoa (cliente)** pode possuir **somente um plano ATIVO**
- O **Plano de Telefonia** é tratado como um produto
- A entidade **Sale** representa o **contrato / assinatura**
- O pagamento é modelado como um **Value Object embutido**
- O **Sales Service** é o **dono das regras críticas de negócio**

---

## 📦 Modelo de Domínio (Visão Geral)

- **Person** → Cliente / Assinante
- **Plan** → Plano de Telefonia
- **Sale** → Contrato / Assinatura
  - Status: `PENDING_ACTIVATION`, `ACTIVE`, `SUSPENDED`, `CANCELED`, `EXPIRED`
  - Contém um **Payment embutido**
- **Payment**
  - Status: `PENDING`, `PAID`, `FAILED`, `CANCELED`

---

## 🔐 Regras de Negócio Principais

- Uma pessoa não pode ter dois planos com status `ACTIVE`
- A verificação de plano ativo ocorre **na base do Sales Service**
- O pagamento influencia diretamente o status do contrato
- O histórico de contratos é preservado mesmo após cancelamento

---

# 📄 ADR-001 — Modelagem do Domínio de Vendas e Planos de Telefonia

## Status
**Aceito**

---

## Contexto

O sistema **SalesFlow** foi concebido para simular cenários reais de **adesão, migração e gestão de planos de telefonia**, inspirados em operações de telecomunicações.

Nesse domínio, existem regras críticas de negócio, principalmente no **momento de criação de uma venda (adesão)**.

### 📌 Regras de Negócio — Criação de Venda

Antes de criar uma **Sale**, o sistema deve garantir que:

- ✅ A **Pessoa existe**
- ✅ A **Pessoa está ativa**
- ✅ O **Plano existe**
- ✅ O **Plano está ativo**
- ✅ A **Pessoa NÃO possui outro plano ativo**
- ✅ A **data de início do contrato é válida**
- ✅ O **Plano permite novas adesões**

Além disso:

- Um cliente pode possuir **apenas um plano ativo por vez**
- A adesão representa um **contrato/assinatura**
- O pagamento impacta diretamente o estado do contrato
- Mudanças de plano devem respeitar regras de ciclo e status

Inicialmente, surgiram dúvidas sobre:
- Onde manter a regra de plano ativo
- Se o pagamento deveria ser uma entidade independente
- Qual serviço deveria ser o dono das regras de negócio

---

## Decisão Arquitetural

### 1️⃣ Centralização das Regras no Sales Service

Foi decidido que o **Sales Service** será o **Aggregate Root do domínio**, sendo o **único responsável** por:

- Criar e gerenciar contratos (**Sale**)
- Validar **todas as regras de negócio de criação**
- Garantir a regra de **um plano ativo por pessoa**
- Controlar o ciclo de vida do contrato
- Gerenciar o pagamento associado à venda

Durante a **criação da Sale**, o Sales Service deve:

- Consultar o **Person Service** para validar:
  - Existência da pessoa
  - Status ativo da pessoa
- Consultar o **Plan Service** para validar:
  - Existência do plano
  - Status ativo do plano
  - Permissão para novas adesões
- Validar regras internas:
  - Pessoa não possui outro plano ativo
  - Data de início válida

Os serviços **Person** e **Plan** são apenas **consultados**, não sendo responsáveis por regras do domínio de vendas.

---
## 🧱 Modelagem das Entidades de Domínio


## 👤 Person (Cliente)

A entidade **Person** representa o cliente/assinante do sistema.

### 📄 Person

| Campo | Tipo | Descrição |
|------|-----|----------|
| `personId` | String (8 caracteres) | Identificador único da pessoa |
| `name` | String | Nome completo |
| `taxIdentifier` | String | Documento (CPF/CNPJ) |
| `status` | PersonStatusEnum | Status da pessoa |
| `created` | DateTime | Data de criação |
| `birthDate` | DateTime | Data de nascimento |

### 📌 Status da Pessoa — PersonStatusEnum

| Status | Descrição |
|-------|----------|
| `ACTIVE` | Pessoa ativa e apta a contratar planos |
| `INACTIVE` | Pessoa inativa no sistema |
| `BLOCKED` | Pessoa bloqueada por regra de negócio |

---

## 📦 Plan (Plano de Telefonia)

A entidade **Plan** representa um plano comercializado pela operadora.

### 📄 Plan

| Campo | Tipo | Descrição |
|------|-----|----------|
| `planId` | String (8 caracteres) | Identificador do plano |
| `name` | String | Nome do plano |
| `type` | TypeEnum | Tipo do plano |
| `monthlyPrice` | Decimal | Valor mensal |
| `created` | DateTime | Data de criação |
| `active` | Boolean | Indica se o plano está ativo |
| `description` | String | Descrição do plano |

### 📌 Tipo do Plano — TypeEnum

| Tipo | Descrição |
|------|----------|
| `PRE_PAGO` | Plano pré-pago |
| `CONTROLE` | Plano controle |
| `POS_PAGO` | Plano pós-pago |

---

## 🧾 Sale (Contrato / Assinatura)

A entidade **Sale** representa o contrato firmado entre a pessoa e o plano.

### 📄 Sale
| Campo | Tipo | Descrição |
|------|-----|----------|
| `saleId` | String (8 caracteres) | Identificador único da venda |
| `taxIdentifier` | String | Documento da pessoa (CPF/CNPJ) |
| `planId` | String | Identificador do plano |
| `monthlyPrice` | Decimal | Valor base mensal do plano |
| `personName` | String | Nome da pessoa (snapshot) |
| `planName` | String | Nome do plano (snapshot) |
| `planType` | String | Tipo do plano (pré, controle, pós) |
| `startDate` | DateTime | Data de início do contrato |
| `endDate` | DateTime | Data de término do contrato |
| `billingDay` | Int | Dia de faturamento |
| `discount` | Decimal | Valor de desconto aplicado |
| `finalMonthlyPrice` | Decimal | Valor final após desconto |
| `status` | StatusEnum | Status atual do contrato |
| `createdAt` | DateTime | Data de criação da venda |
| `canceledAt` | DateTime | Data de cancelamento |
| `billing` | BillingHistory | Pagamento associado à venda |

---

A entidade mantém **snapshots** de dados relevantes (ex.: nome da pessoa, nome do plano, tipo do plano) para garantir **consistência histórica**, mesmo que dados externos sejam alterados futuramente.

---

### 3️⃣ Payment como Value Object Embutido

O pagamento foi modelado como um **Value Object embutido dentro da Sale**, e não como uma entidade independente.

Motivos da decisão:

- Cada venda possui **apenas um pagamento ativo**
- Não há necessidade de histórico financeiro complexo
- O pagamento **não existe sem a Sale**
- Simplicidade e coesão do agregado

### 💰 O `Payment` contém apenas:

| Campo | Tipo | Descrição |
|------|-----|----------|
| `paymentId` | String | Identificador do pagamento |
| `paymentValue` | BigDecimal | Valor pago |
| `paymentStatus` | StatusPaymentEnum | Status do pagamento |
| `paymentDate` | LocalDateTime | Data do pagamento |

### 💰 Status do Pagamento — StatusPaymentEnum

| Status | Descrição |
|-------|----------|
| `PENDING` | Pagamento criado e aguardando processamento |
| `PAID` | Pagamento realizado com sucesso |
| `FAILED` | Falha no processamento do pagamento |
| `CANCELED` | Pagamento cancelado |

---

### 4️⃣ Regra de Um Plano Ativo por Pessoa

A regra **“uma pessoa só pode ter um plano ACTIVE”** é:

- Validada no momento da criação da Sale
- Verificada diretamente na **base do Sales Service**
- Reforçada por **constraint no banco de dados**
- Nunca delegada a outros serviços

Essa abordagem garante:

- Consistência transacional
- Isolamento de domínio
- Proteção contra concorrência
- Prevenção de estados inválidos

---

## 🛠️ Tecnologias e Ferramentas Utilizadas

### ⚙️ Back-end & Frameworks
- Java 17
- Spring Boot
- Spring Web
- Spring Validation
- Spring Security com OAuth2

---

### 🔐 Segurança
- OAuth2 Resource Server
- Controle de acesso por roles e escopos

---

### ☁️ Cloud & Mensageria
- AWS SQS
  - Producers e Listeners
  - Processamento assíncrono
  - Base para eventos de domínio

---

### 🔗 Integrações
- OpenFeign
- Integração com Person Service e Plan Service

---

### ⏱️ Agendamentos
- Quartz Scheduler
- Expiração de contratos
- Processos recorrentes de negócio

---

### 🗄️ Banco de Dados
- PostgreSQL
- Constraints para regras críticas
- Controle transacional no domínio

---

### 📊 Observabilidade
- Logs estruturados
- Métricas com Prometheus
- Dashboards no Grafana

---

### 📄 Documentação
- Swagger / OpenAPI

---

### 🧪 Testes
- Testes unitários
- Testes end-to-end com containers
- Ambiente isolado para testes

---

### 🐳 Containers
- Docker
- Ambientes: local, staging e produção

---

### 🚀 CI/CD & Deploy
- Pipeline de CI/CD
- Deploy em produção com **Render**
- Preparado para cloud AWS

---

## Consequências

### Benefícios

- Regras de criação explícitas e centralizadas
- Domínio claro e coeso
- Baixo acoplamento entre serviços
- Regras críticas protegidas no domínio e persistência
- Modelo simples e fácil de evoluir
- Forte aderência a **DDD** e **Arquitetura Hexagonal**

### Limitações

- Não suporta histórico de múltiplos pagamentos
- Não atende cenários complexos de conciliação financeira
- Para esses casos, o `Payment` deverá evoluir para uma entidade própria

---

## Alternativas Consideradas

- Criar um serviço exclusivo de Billing  
  ❌ Rejeitado por complexidade desnecessária

- Manter pagamento em tabela própria  
  ❌ Rejeitado por falta de necessidade no cenário atual

- Delegar validações para Person ou Plan API  
  ❌ Rejeitado por violar o ownership do domínio de vendas

---

## Decisão Final

O **Sales Service** é o **guardião das regras de negócio de criação e gestão de vendas**, com a entidade **Sale** como **Aggregate Root** e o **Payment** como **Value Object embutido**, garantindo um modelo **simples, consistente e alinhado com cenários reais de telecom**.

---

## 🚀 Evoluções Futuras

- Comunicação assíncrona (Kafka / SQS / SNS)
- Emissão de eventos de domínio (`PlanActivated`, `PlanCanceled`)
- Autenticação e autorização (Spring Security)
- Observabilidade (logs, métricas, tracing)
- Deploy em Cloud (AWS ou similar)
- CI/CD

---

## 👤 Autor

**Jean Charles Duarte**  
Back-end Java Developer  
Foco em arquitetura, domínio de negócio e sistemas distribuídos

---

> Projeto desenvolvido com foco educacional e profissional, simulando desafios reais do mercado de telecomunicações e arquitetura de software.
