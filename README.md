# API de Registros Meteorológicos

Projeto desenvolvido para cadastro e gerenciamento de registros meteorológicos.

## Tecnologias utilizadas

- Java 25
- Spring Boot 4.0.8
- Maven
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 Database
- MySQL
- Flyway
- Swagger/OpenAPI
- Docker

## Estrutura do projeto

O projeto possui as seguintes camadas principais:

controllers
dto
entities
repositories
services

A entidade principal é `RegistroMeteorologico`, responsávelpor armazenar os dados dos registros meteorológicos.

Os registros possuem:

registroId
estacaoNome
dataRegistro
temperatura
percentualUmidade
precipitacao
condicaoClimatica

As condições climáticas disponíveis são:

ENSOLARADO
NUBLADO
CHUVOSO
TEMPESTADE

## Profiles

O projeto possui dois profiles:

test
dev

O profile `test` utiliza banco H2.

O profile `dev` utiliza banco MySQL.

---

## Executando com H2

Por padrão, o projeto inicia utilizando o profile `test`.

A aplicação pode ser iniciada pelo IntelliJ executando a classe principal:

ApiRegistroApplication

Também pode ser executada pelo terminal:

./mvnw spring-boot:run

No Windows:

mvnw.cmd spring-boot:run

Depois que a aplicação iniciar, a API estará disponível em:

http://localhost:8080

### H2 Console

O console do H2 pode ser acessado em:

http://localhost:8080/h2-console

Utilizar:

JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password:

A senha deve ser deixada em branco.

---

## Executando com MySQL

Para executar a aplicação com MySQL, primeiro é necessário ter o Docker instalado e em execução.

### Criar o container do MySQL

Execute:

docker run -d --name mysql_846 -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 mysql:8.4.6

Para verificar se o container está rodando:

docker ps

Deve aparecer o container:

mysql_846

### Executar a aplicação com o profile dev

No terminal:

./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

No Windows:

mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev

O projeto utiliza o banco:

registros

Caso o banco ainda não exista, ele será criado automaticamente.

As tabelas são criadas por meio das migrations do Flyway.

---

## Flyway

As migrations ficam na pasta:

src/main/resources/db/migration

Quando a aplicação é executada com o profile `dev`, o Flyway verifica e executa automaticamente as migrations ainda não aplicadas.

Também é criada a tabela:

flyway_schema_history

Ela armazena o histórico das migrations executadas.

---

## Swagger

Com a aplicação em execução, a documentação dos endpoints pode ser acessada em:

http://localhost:8080/swagger-ui/index.html

---

## Endpoints

### Listar registros

GET /registros

Exemplo:

http://localhost:8080/registros

### Buscar registro por ID

GET /registros/{id}

Exemplo:

http://localhost:8080/registros/1

### Criar registro

POST /registros

Exemplo de JSON:

{
  "estacaoNome": "Estacao Leste",
  "dataRegistro": "2026-10-14",
  "temperatura": 27.5,
  "percentualUmidade": 68.0,
  "precipitacao": 0.0,
  "condicaoClimatica": "ENSOLARADO"
}

### Atualizar registro

PUT /registros/{id}

Exemplo:

http://localhost:8080/registros/1

Body:

{
  "estacaoNome": "Estacao Central",
  "dataRegistro": "2026-10-14",
  "temperatura": 25.0,
  "percentualUmidade": 70.0,
  "precipitacao": 2.5,
  "condicaoClimatica": "CHUVOSO"
}

### Excluir registro

DELETE /registros/{id}

Exemplo:

http://localhost:8080/registros/1

---

## Validações

Os dados enviados no cadastro e atualização possuem validações.

### Nome da estação

Obrigatório.

Deve possuir entre 3 e 100 caracteres.

### Data do registro

Obrigatória.

Não pode ser uma data futura.

### Temperatura

Obrigatória.

### Percentual de umidade

Obrigatório.

Deve estar entre:

0 e 100

### Precipitação

Obrigatória.

Não pode possuir valor negativo.

### Condição climática

Obrigatória.

Valores disponíveis:

ENSOLARADO
NUBLADO
CHUVOSO
TEMPESTADE

---

# Docker

O projeto possui um `Dockerfile` para criação da imagem da aplicação.

## Criar a imagem

Na raiz do projeto, execute:

docker build -t api-registro .

Para verificar a imagem criada:

docker images

---

## Executar com Docker e H2

Execute:

docker run -d --name api-registro -p 8080:8080 -e SPRING_PROFILES_ACTIVE=test api-registro

Confira:

docker ps

Depois acesse:

http://localhost:8080/registros

ou:

http://localhost:8080/swagger-ui/index.html

---

# Docker com MySQL

Para que a aplicação e o MySQL consigam se comunicar utilizando containers separados, os dois precisam estar na mesma rede Docker.

## Criar a rede

Execute:

docker network create checkpoint-net

Para visualizar as redes:

docker network ls

---

## Conectar o MySQL à rede

Caso o container `mysql_846` já esteja rodando:

docker network connect checkpoint-net mysql_846

Para conferir:

docker network inspect checkpoint-net

O container `mysql_846` deve aparecer conectado à rede.

---

## Remover container anterior da aplicação

Caso exista um container chamado `api-registro`, remova antes de criar outro:

docker rm -f api-registro

---

## Executar a API com MySQL

Execute:

docker run -d --name api-registro --network checkpoint-net -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev -e DB_HOST=mysql_846 api-registro

Nesse modo, a aplicação utiliza:

DB_HOST=mysql_846

O nome do container é utilizado porque, dentro da rede Docker, a aplicação acessa o MySQL pelo nome do container e não por `localhost`.

---

## Verificar os containers

Execute:

docker ps

Devem aparecer:

mysql_846
api-registro

---

## Verificar os logs

Para visualizar os logs da aplicação:

docker logs api-registro

Para acompanhar os logs em tempo real:

docker logs -f api-registro

---

# Verificando o MySQL

Para entrar no container:

docker exec -it mysql_846 bash

Depois:

mysql -p

Senha:

root

Listar os bancos:

show databases;

Selecionar o banco:

use registros;

Listar as tabelas:

show tables;

Consultar os registros:

select * from tb_registro_meteorologico;

Consultar o histórico do Flyway:

select * from flyway_schema_history;

Para sair do MySQL:

exit;

Depois sair do container:

exit

---

# Parar os containers

Parar a aplicação:

docker stop api-registro

Parar o MySQL:

docker stop mysql_846

Para iniciar novamente:

docker start mysql_846
docker start api-registro

Para remover o container da aplicação:

docker rm -f api-registro

---

## Teste rápido

Com a aplicação rodando, acessar:

http://localhost:8080/swagger-ui/index.html

Criar um registro utilizando o endpoint:

POST /registros

Depois consultar:

GET /registros

Se estiver usando o profile `dev`, também é possível entrar no MySQL e confirmar que o registro foi salvo na tabela:

select * from tb_registro_meteorologico;
