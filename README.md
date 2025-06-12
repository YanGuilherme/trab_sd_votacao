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
- Todos as partes do sistema configurados para docker
- Réplicas do nó coletor utilizando o Nginx como proxy reverso para balancear a carga
- Logs do sistema para auditoria

---

## 🚀 Como executar o projeto

Este projeto é um sistema distribuído que roda via Docker e Docker Compose. **É necessário estar em ambiente Linux** para seguir as instruções.

---

## Pré-requisitos ✅

- Docker e Docker Compose instalados
- Java 21 ou superior instalado

---

## Configuração inicial 🛠️

1. **Adicionar host local**  
   Edite o arquivo `/etc/hosts` e adicione a linha:

   ```
   127.0.1.1       coletor.local
   ```

2. **Criar rede Docker**  
   Crie uma rede customizada no Docker chamada `rede` para comunicação entre containers:

   ```bash
   docker network create rede
   ```

3. **Build do backend e core**  
   Entre nas pastas `core` e `backend` e execute o build do Java:

   ```bash
   ./mvnw clean package -DskipTests
   ```

---

## Rodando o sistema 🚦

1. Na raiz do projeto, rode o docker-compose para subir os containers principais:

   ```bash
   docker-compose up -d --build
   ```

2. Entre na pasta do core e do frontend e suba os containers:

   ```bash
   docker-compose up -d --build
   ```

3. Entre na pasta do backend para rodar as réplicas do coletor:

   ```bash
   docker-compose up --build --scale coletor=3 -d
   ```

---

## Conferindo se está tudo certo ✅

Use o comando:

```bash
docker ps
```

## Explicação dos containers 🧩

- **frontend-frontend**: Aplicação frontend rodando na porta `4200`.
- **core-agregador**: Serviço core agregador da aplicação, exposto na porta `8081`.
- **backend-coletor (3 réplicas)**: Três instâncias do backend coletor em execução. O número 3 indica a escala feita para suportar alta disponibilidade e paralelismo.
- **nginx_balanceador**: Nginx configurado como proxy reverso para balancear a carga dos coletores backend. Ele recebe as requisições no host `coletor.local` e na porta `8080` e as distribui entre as réplicas dos coletores, garantindo alta performance e tolerância a falhas.
- **bd_sd_core**: Banco de dados PostgreSQL principal para o core, porta `5432`.
- **postgres_eleicao**: Outro banco PostgreSQL utilizado para dados de eleição, porta `5433`.
- **rabbitmq**: Broker de mensagens RabbitMQ, que gerencia a fila de mensagens da aplicação, exposto na porta `5672` para comunicação entre microserviços, e na porta `15672` para a interface de gerenciamento via web.

---

## 🧰 Tecnologias Utilizadas

- Java + Spring Boot
- Angular
- PostgreSQL
- RabbitMQ
- Docker
- Maven
- Nginx

---

## Dicas finais 💡

- Sempre use `docker-compose down` para parar os containers.
- Use `docker logs <container_id>` para verificar logs de qualquer container.

# Licença

MIT © Yan Guilherme
