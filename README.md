# Desafio Final - API REST

## Descrição

Esta é uma API REST desenvolvida em Java Spring Boot para o desafio final do bootcamp de Arquiteto de Software. A API implementa operações CRUD para gerenciamento de clientes, seguindo o padrão arquitetural MVC e aplicando design patterns apropriados.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Web**
- **Spring Validation**
- **PostgreSQL**
- **SpringDoc OpenAPI 3** (Swagger)
- **Maven** (gerenciamento de dependências)

## Arquitetura

A aplicação segue o padrão **MVC (Model-View-Controller)** com as seguintes camadas:

### Estrutura de Pastas

```
src/main/java/com/desafio/api/
├── controller/          # Controladores REST (Camada de Apresentação)
├── model/              # Entidades de domínio (Camada de Modelo)
├── repository/         # Repositórios de dados (Camada de Acesso a Dados)
├── service/            # Serviços de negócio (Camada de Lógica de Negócios)
└── ApiApplication.java # Classe principal da aplicação
```

### Design Patterns Aplicados

1. **MVC (Model-View-Controller)**: Separação de responsabilidades entre as camadas
2. **Dependency Injection**: Injeção de dependências via Spring Framework
3. **Repository Pattern**: Abstração do acesso a dados
4. **Service Layer Pattern**: Encapsulamento da lógica de negócios
5. **Singleton**: Gerenciamento de beans pelo Spring Container

## Funcionalidades da API

A API fornece os seguintes endpoints para gerenciamento de clientes:

### Endpoints Disponíveis

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/clientes` | Lista todos os clientes |
| GET | `/api/clientes/{id}` | Busca cliente por ID |
| GET | `/api/clientes/nome/{nome}` | Busca clientes por nome (parcial) |
| GET | `/api/clientes/email/{email}` | Busca cliente por email |
| GET | `/api/clientes/contar` | Conta total de clientes |
| GET | `/api/clientes/recentes` | Lista clientes recentes |
| GET | `/api/clientes/{id}/existe` | Verifica se cliente existe |
| POST | `/api/clientes` | Cria novo cliente |
| PUT | `/api/clientes/{id}` | Atualiza cliente existente |
| DELETE | `/api/clientes/{id}` | Remove cliente |

### Modelo de Dados - Cliente

```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "telefone": "(11) 99999-1111",
  "endereco": "Rua das Flores, 123 - São Paulo, SP",
  "dataCriacao": "2024-01-15T10:30:00",
  "dataAtualizacao": "2024-01-15T10:30:00"
}
```

## Configuração e Execução

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- PostgreSQL

### Configuração do Banco de Dados


#### Para Produção e Desenvolvimento (PostgreSQL)
1. Crie um banco de dados PostgreSQL
2. Execute o script `src/main/resources/schema.sql` para criar as tabelas
3. Atualize as configurações no arquivo `application.properties`:

### Executando a Aplicação

1. **Clone ou baixe o projeto**
2. **Navegue até o diretório do projeto**
   ```bash
   cd desafio-api
   ```

3. **Execute a aplicação**
   ```bash
   mvn spring-boot:run
   ```
   
   Ou compile e execute o JAR:
   ```bash
   mvn clean package
   java -jar target/desafio-api-1.0.0.jar
   ```

4. **Acesse a aplicação**
   - API: http://localhost:8080/api/clientes
   - Swagger UI: http://localhost:8080/swagger-ui.html

## Documentação da API (Swagger)

A documentação interativa da API está disponível através do Swagger UI em:
**http://localhost:8080/swagger-ui.html**

O Swagger fornece:
- Documentação completa de todos os endpoints
- Possibilidade de testar os endpoints diretamente na interface
- Esquemas de dados detalhados
- Exemplos de requisições e respostas

## Exemplos de Uso

### Criar um novo cliente
```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao.silva@email.com",
    "telefone": "(11) 99999-1111",
    "endereco": "Rua das Flores, 123 - São Paulo, SP"
  }'
```

### Listar todos os clientes
```bash
curl -X GET http://localhost:8080/api/clientes
```

### Buscar cliente por ID
```bash
curl -X GET http://localhost:8080/api/clientes/1
```

### Atualizar cliente
```bash
curl -X PUT http://localhost:8080/api/clientes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva Santos",
    "email": "joao.silva@email.com",
    "telefone": "(11) 99999-1111",
    "endereco": "Rua das Flores, 456 - São Paulo, SP"
  }'
```

### Deletar cliente
```bash
curl -X DELETE http://localhost:8080/api/clientes/1
```

## Validações Implementadas

- **Nome**: Obrigatório, entre 2 e 100 caracteres
- **Email**: Obrigatório, formato válido, único no sistema
- **Telefone**: Opcional, máximo 20 caracteres
- **Endereço**: Opcional, máximo 200 caracteres

## Tratamento de Erros

A API retorna códigos de status HTTP apropriados:

- **200 OK**: Operação realizada com sucesso
- **201 Created**: Recurso criado com sucesso
- **204 No Content**: Recurso deletado com sucesso
- **400 Bad Request**: Dados inválidos
- **404 Not Found**: Recurso não encontrado
- **409 Conflict**: Conflito (ex: email já em uso)

## Logs e Monitoramento

A aplicação está configurada com logs detalhados para facilitar o desenvolvimento e depuração:
- Logs de SQL (queries executadas)
- Logs de requisições HTTP
- Logs da aplicação em nível DEBUG

## Próximos Passos

Para expandir a aplicação, considere:

1. **Implementar autenticação e autorização** (Spring Security)
2. **Adicionar paginação** nos endpoints de listagem
3. **Implementar cache** (Redis/Hazelcast)
4. **Adicionar métricas** (Micrometer/Actuator)
5. **Implementar testes unitários e de integração**
6. **Configurar CI/CD**
7. **Adicionar outras entidades** (Produtos, Pedidos)

## Contato

Para dúvidas ou sugestões, entre em contato com a equipe de desenvolvimento.

---

**Desenvolvido como parte do Desafio Final do Bootcamp de Arquiteto de Software**

