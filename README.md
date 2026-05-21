# APIcommerce
API REST de produtos desenvolvida em Spring Boot com JPA/Hibernate e banco H2.

## 🚀 Tecnologias
- Java 21
- Spring Boot 4.0.5
- JPA/Hibernate
- Banco H2
- IntelliJ IDEA
- Maven

## ⚙️ Configuração do Projeto
Este projeto foi gerado com Spring Initializr com as seguintes opções:
- Group: br.com.bruno
- Artifact: APIcommerce
- Package name: br.com.bruno.APIcommerce
- Spring Boot: 4.0.5
- Java: 21
- Packaging: Jar
- Configuração: Properties
- Dependências:
  - Spring Web
  - Spring Boot DevTools
  - Spring Data JPA
  - H2 Database

## 📌 Observações
- Banco H2 em memória (os dados se perdem ao reiniciar).
- Projeto criado para estudo e prática de Spring Boot.

---

# 🔑 Autenticação JWT

## Usuários padrão
- USER → `user / 654321`
- ADMIN → `admin / 123456`

## Endpoints principais
- `POST /auth/login` → gera token JWT
- `GET /produto` → USER e ADMIN
- `POST /produto` → apenas ADMIN
- `GET /admin/**` → apenas ADMIN
- `POST /categoria` → apenas ADMIN

## Regras de autorização
- Sem token → `401 Unauthorized`
- Token inválido/expirado → `401 Unauthorized`
- Token USER → `200 OK` em GET, `403 Forbidden` em POST/admin
- Token ADMIN → `200/201 OK` em todas as rotas

## Exemplos de requisições (cURL)
```bash
# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"654321"}'

# GET produto com token USER
curl -X GET http://localhost:8080/produto \
  -H "Authorization: Bearer <JWT_USER>"

# POST produto com token ADMIN
curl -X POST http://localhost:8080/produto \
  -H "Authorization: Bearer <JWT_ADMIN>" \
  -H "Content-Type: application/json" \
  -d '{"categoriaId":1,"fotoUrl":"https://meusite.com/imagens/notebook.jpg","valorUnitario":3500.00,"descricao":"Notebook gamer com 16GB RAM","nome":"Notebook Gamer"}'
