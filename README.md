# 🗳️ Sistema de Eleição Distribuído

Este projeto é uma simulação de um **Sistema de Votação Distribuído**, desenvolvido com o objetivo de aplicar na prática os conceitos de **Sistemas Distribuídos (SD)**. O sistema é dividido em dois principais **nós**:

- **Coletor**: Responsável por receber os votos dos usuários.
- **Agregador**: Responsável por contabilizar os votos recebidos.

Além disso, o sistema utiliza **RabbitMQ** para comunicação entre os nós via mensagens assíncronas e **MongoDB** para persistência de dados. A interface de usuário é feita com **Angular**.

## 🧠 Conceitos Aplicados

- Comunicação assíncrona via fila de mensagens (RabbitMQ)
- Separação de responsabilidades (coletor e agregador)
- Microsserviços
- Persistência distribuída (banco de dados separado para núcleo e eleições)
- Interface moderna via Angular

---

## 🚀 Como executar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/seu-repo.git
cd seu-repo
```

### 2. Suba os containers Docker

O projeto depende de três serviços: RabbitMQ, banco de dados do núcleo (`bd_core`) e banco de dados da eleição (`bd_eleicao`). Cada serviço tem seu próprio `docker-compose`.

#### 🐇 Suba o RabbitMQ

```bash
cd rabbit
docker-compose up -d
cd ..
```

Acesse o painel do RabbitMQ em: [http://localhost:15672](http://localhost:15672)  
Login padrão: `guest` / `guest`

#### 💾 Suba o banco de dados do núcleo

```bash
cd bd_core
docker-compose up -d
cd ..
```

#### 💾 Suba o banco de dados da eleição

```bash
cd bd_eleicao
docker-compose up -d
cd ..
```

### 3. Inicie o backend e o core

Você pode iniciar os dois serviços com:

```bash
cd backend+core
# Execute o core
cd core
./mvnw spring-boot:run
# Em outro terminal, execute o backend
cd ../backend
./mvnw spring-boot:run
```

Certifique-se de que o Java 17+ está instalado. O Maven wrapper (`mvnw`) já está incluído.

### 4. Inicie o frontend (Angular)

Certifique-se de ter o Angular CLI instalado:

```bash
npm install -g @angular/cli
```

Em seguida:

```bash
cd frontend
npm install
ng serve
```

A aplicação estará disponível em: [http://localhost:4200](http://localhost:4200)

### 5. Acesse a aplicação

- Interface Web: [http://localhost:4200](http://localhost:4200)
- Backend (API): [http://localhost:8080](http://localhost:8080)
- Core (Agregador): geralmente em [http://localhost:8081](http://localhost:8081) (verifique a porta se configurada)

---

## 🧰 Tecnologias Utilizadas

- Java + Spring Boot
- Angular
- MongoDB
- RabbitMQ
- Docker
- Maven

---

## 📁 Estrutura do Projeto

```
backend+core/
├── backend/         -> Serviço coletor de votos
├── core/            -> Serviço agregador de votos
├── bd_core/         -> Banco de dados do core (votos e candidatos)
├── bd_eleicao/      -> Banco de dados da eleição (users)
├── rabbit/          -> RabbitMQ
frontend/            -> Interface Angular
```

---

## 🧑‍💻 Contribuição

Pull Requests são bem-vindas! Para mudanças maiores, por favor abra uma issue primeiro para discutir o que você gostaria de mudar.
