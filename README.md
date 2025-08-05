# Hexagonal Architecture - User Service

Este projeto é uma API de gerenciamento de usuários construída com **Java**, utilizando **Arquitetura Hexagonal (Ports & Adapters)** e princípios de **Domain-Driven Design (DDD)**.

### 📌 Proposta

A API permite:
- Cadastro de usuário
- Alteração de senha
- Alteração de email

> ⚠️ A operação de exclusão (delete) **não está disponível**, seguindo uma proposta mais controlada e auditável do ciclo de vida do usuário.

---

### 🔄 Fluxo de operação

1. Controller (adapter inbound) recebe a requisição.
2. Um caso de uso (ex: `InsertUserUseCaseImpl`) é acionado.
3. O domínio é manipulado via Value Objects e entidades imutáveis.
4. Os adapters (outbound) realizam a persistência e codificação de senha.

## 🧠 DDD + Imutabilidade

Exemplo da entidade `User` com métodos que retornam **novas instâncias** em vez de modificar o estado:

```java
public User changePasswordTo(String newPassword) {
    return new User(this.id, this.name, this.email, new Password(newPassword));
}
```

## 🧱 Estrutura do Projeto

Organizado em três camadas principais:

`application`, `domain` e `infra`

```plaintext
.
└── com.sirkaue.hexagonalarchitecture/
    ├── application/
    │   ├── helper/
    │   │   └── EntityFinderHelper.java
    │   ├── ports/
    │   │   ├── in/
    │   │   │   ├── FindUserByIdUseCase.java
    │   │   │   ├── InsertUserUseCase.java
    │   │   │   ├── UpdateUserEmailUseCase.java
    │   │   │   └── UpdateUserPasswordUseCase.java
    │   │   └── out/
    │   │       ├── FindUserByIdPort.java
    │   │       ├── InsertUserPort.java
    │   │       ├── PasswordEncoderPort.java
    │   │       ├── UpdateUserPort.java
    │   │       └── UserExistsByEmailPort.java
    │   └── usecase/
    │       ├── FindUserByIdUseCaseImpl.java
    │       ├── InsertUserUseCaseImpl.java
    │       ├── UpdateUserEmailUseCaseImpl.java
    │       └── UpdateUserPasswordUseCaseImpl.java
    ├── domain/
    │   ├── exception
    │   ├── model
    │   └── valueobjects
    └── infra/
        ├── adapters/
        │   ├── in/
        │   │   ├── dto/
        │   │   │   ├── request/
        │   │   │   │   ├── UpdateEmailRequest.java
        │   │   │   │   ├── UpdatePasswordRequest.java
        │   │   │   │   └── UserRequest.java
        │   │   │   └── response/
        │   │   │       ├── ErrorResponse.java
        │   │   │       ├── FieldErrorResponse.java
        │   │   │       ├── UserResponse.java
        │   │   │       └── ValidationErrorResponse.java
        │   │   ├── mapper/
        │   │   │   ├── impl/
        │   │   │   │   └── UserMapperImpl.java
        │   │   │   └── UserMapper.java
        │   │   └── web/
        │   │       ├── controller/
        │   │       │   └── UserController.java
        │   │       └── exception/
        │   │           └── RestExceptionHandler.java
        │   └── out/
        │       ├── persistence/
        │       │   ├── entity/
        │       │   │   └── UserEntity.java
        │       │   └── mapper/
        │       │       ├── impl/
        │       │       │   └── UserEntityMapperImpl.java
        │       │       └── UserEntityMapper.java
        │       ├── repository/
        │       │   └── UserJpaRepository.java
        │       ├── FindUserByIdAdapter.java
        │       ├── InsertUserAdapter.java
        │       ├── PasswordEncoderAdapter.java
        │       ├── UpdateUserEmailAdapter.java
        │       ├── UpdateUserPasswordAdapter.java
        │       └── UserExistsByEmailAdapter.java
        └── config/
            ├── EntityFinderHelperConfig.java
            ├── FindUserByIdConfig.java
            ├── InsertUserConfig.java
            ├── PasswordEncoderConfig.java
            ├── SpringDocOpenApiConfig.java
            ├── UpdateUserEmailConfig.java
            └── UpdateUserPasswordConfig.java
```

## 🔧 Tecnologias e Padrões Utilizados

- Java 17+
- Spring Boot
- Arquitetura Hexagonal (Ports & Adapters)
- Domain-Driven Design (DDD)
- Modelo de domínio rico e imutável
- Separação em camadas: `application`, `domain`, `infra`
- Testes com JUnit 5 e Mockito
- Testes de integração com Spring Boot
- Docker e Docker Compose
- Documentação com Swagger/OpenAPI

---

## 🧠 Destaques Arquiteturais
### ✅ Arquitetura Hexagonal (Ports & Adapters)

- Application: camadas que orquestram os fluxos de negócio.
- Domain: lógica de negócio rica, imutável e independente de frameworks.
- Infra: adaptações técnicas, como persistência e criptografia.

### ✅ Domain-Driven Design

- `User` como entidade imutável.
- `Name`, `Email` e `Password` como Value Objects com responsabilidade própria.
- Exceções de domínio claras (`EmailAlreadyExistsException`, etc).

## 🔐 Segurança com BCrypt

Foi configurado um PasswordEncoder para garantir que as senhas sejam armazenadas com segurança:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

## ✅ Testes

O projeto já possui:

- Testes unitários para lógica de domínio e casos de uso.
- Testes de integração utilizando Spring Boot.

## 🧩 Exemplo de Caso de Uso

```java
public User execute(User user) {
    if (userExistsByEmailPort.existsByEmail(user.getEmail().value())) {
        throw new EmailAlreadyExistsException("Email already exists");
    }

    String hashedPassword = passwordEncoderPort.encode(user.getPassword());
    user = user.changePasswordTo(hashedPassword);
    return insertUserPort.insert(user);
}
```
---

## 📬 Exemplos de Operações da API - User Service

---

### 1. Criar Usuário

**POST** `/users`

#### Requisição (JSON)

```json
{
  "name": "Jose",
  "email": "jose@email.com",
  "password": "jose123321"
}
```

**Resposta**
- Status: `201 Created`
- Location Header: `http://localhost:8080/users/1`

---

### 2. Atualizar Email do Usuário

**PATCH** `/users/{id}/email`

#### Requisição (JSON)

```json
{
  "email": "jose1@email.com"
}
```

**Resposta**
- Status: `204 No Content`

---

### 3. Atualizar Senha do Usuário

**PATCH** `/users/{id}/email`

#### Requisição (JSON)

```json
{
  "currentPassword": "jose123321",
  "newPassword": "jose123",
  "confirmPassword": "jose123"
}
```

**Resposta**
- Status: `204 No Content`

## 🚀 Como Rodar Localmente

### Requisitos
- Java 17+
- Maven

### Clone o repositório

```bash
git clone https://github.com/sirkaue/hexagonal-architecture.git
```

## 🐳 Como Rodar com Docker

Este projeto utiliza um **Dockerfile multi-stage**, separando as etapas de build e runtime para gerar
uma imagem leve e eficiente.

### Clonar o repositório:
```bash
git clone https://github.com/sirkaue/hexagonal-architecture.git
cd hexagonal-architecture
```


### 📦 Build da Imagem

Execute o comando abaixo na raiz do projeto (onde está o `Dockerfile` e o `docker-compose.yml`):

```bash
docker compose up --build
```
- A aplicação será executada em `http://localhost:8080`
- O MySQL estará disponível internamente como `mysql-db` na rede do Docker.
- O banco estará acessível externamente na porta `3307`

## ✅ Conclusão

Este projeto é um exemplo prático de como aplicar a **Arquitetura Hexagonal (Ports & Adapters)** em uma aplicação com **Spring Boot**, aliada aos princípios de **Domain-Driven Design (DDD)**.  
Ele demonstra como construir um serviço de usuários com domínio rico, imutável e desacoplado de frameworks, promovendo flexibilidade, testabilidade e evolução sustentável da aplicação.
