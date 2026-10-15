# API Registro Meteorologico

Projeto do Checkpoint - Carolina Seiko - RM 558839.

## Tecnologias

- Java 25
- Spring Boot 4.0.8
- Maven
- Spring Web
- Spring Data JPA
- Bean Validation
- H2
- MySQL
- Flyway
- Swagger/OpenAPI
- Docker

## Profiles

### test - H2

O projeto inicia com o profile `test` por padrao.

Execute pelo IntelliJ ou:

```bash
./mvnw spring-boot:run
```

H2 Console:

```text
http://localhost:8080/h2-console
```

JDBC URL:

```text
jdbc:h2:mem:testdb
```

Usuario: `sa`
Senha: em branco

### dev - MySQL

Suba um MySQL 8.4.6:

```bash
docker run -d --name mysql_846 -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 mysql:8.4.6
```

Execute a aplicacao com o profile dev:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

O banco `registros` sera criado e o Flyway executara as migrations.

## Swagger

```text
http://localhost:8080/swagger-ui/index.html
```

## Endpoints

```text
GET    /registros
GET    /registros/{id}
POST   /registros
PUT    /registros/{id}
DELETE /registros/{id}
```

Exemplo de POST:

```json
{
  "estacaoNome": "Estacao Leste",
  "dataRegistro": "2026-10-14",
  "temperatura": 27.5,
  "percentualUmidade": 68.0,
  "precipitacao": 0.0,
  "condicaoClimatica": "ENSOLARADO"
}
```

## Docker

Build da imagem:

```bash
docker build -t api-registro .
```

### Profile test com H2

```bash
docker run -d --name api-registro -p 8080:8080 -e SPRING_PROFILES_ACTIVE=test api-registro
```

### Profile dev com MySQL usando rede Docker

Crie a rede:

```bash
docker network create checkpoint-net
```

Conecte o MySQL na rede:

```bash
docker network connect checkpoint-net mysql_846
```

Remova o container anterior da API, se existir:

```bash
docker rm -f api-registro
```

Execute a API na mesma rede:

```bash
docker run -d --name api-registro --network checkpoint-net -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev -e DB_HOST=mysql_846 api-registro
```

Nesse cenario, a aplicacao acessa o MySQL pelo nome do container `mysql_846`.
# api-registro-CarolinaSeiko-558839-m1
