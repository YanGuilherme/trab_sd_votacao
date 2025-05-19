# 🗳️ Sistema de Eleição Distribuído

Este projeto é uma simulação de um **Sistema de Votação Distribuído**, desenvolvido com o objetivo de aplicar na prática os conceitos de **Sistemas Distribuídos (SD)**. O sistema é dividido em dois principais **nós**:

- **Coletor**: Responsável por receber os votos dos usuários.
- **Agregador**: Responsável por contabilizar os votos recebidos.

Além disso, o sistema utiliza **RabbitMQ** para comunicação entre os nós via mensagens assíncronas e **PostgreSQL** para persistência de dados. A interface de usuário é feita com **Angular**.

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

O projeto depende de três serviços: RabbitMQ, banco de dados do núcleo (`postgres_core`) e banco de dados da eleição (`postgres_eleicao`). Todos estão definidos em um único arquivo Docker Compose.

```bash
cd docker
docker-compose up -d
```

Acesse o painel do RabbitMQ em: [http://localhost:15672](http://localhost:15672)  
Login: `yan` / Senha: `yan`

### 3. Inicie o backend e o core

Você pode iniciar os dois serviços com:

```bash
# Terminal 1: Inicie o core (Agregador)
cd core
./mvnw spring-boot:run
```

```bash
# Terminal 2: Inicie o backend (Coletor)
cd backend
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

---

## 🧰 Tecnologias Utilizadas

- Java + Spring Boot
- Angular
- PostgreSQL
- RabbitMQ
- Docker
- Maven

---

## 📁 Estrutura do Projeto

```
.
├── backend/         -> Serviço coletor de votos
├── core/            -> Serviço agregador de votos
├── docker/          -> Contém o docker-compose com RabbitMQ e os dois bancos PostgreSQL
├── frontend/        -> Interface Angular
└── README.md
```

---

## 🧑‍💻 Contribuição

Pull Requests são bem-vindas! Para mudanças maiores, por favor abra uma issue primeiro para discutir o que você gostaria de mudar.
