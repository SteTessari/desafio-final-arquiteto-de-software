# Instruções de Execução - Desafio Final API

## Guia Rápido de Execução

### 1. Pré-requisitos
Certifique-se de ter instalado:
- **Java 17** ou superior
- **Maven 3.6** ou superior

Para verificar as versões instaladas:
```bash
java -version
mvn -version
```

### 2. Executando a Aplicação

#### Opção 1: Usando Maven (Recomendado para desenvolvimento)
```bash
# Navegue até o diretório do projeto
cd desafio-api

# Execute a aplicação
mvn spring-boot:run
```

#### Opção 2: Compilando e executando o JAR
```bash
# Navegue até o diretório do projeto
cd desafio-api

# Compile o projeto
mvn clean package

# Execute o JAR gerado
java -jar target/desafio-api-1.0.0.jar
```

### 3. Verificando se a aplicação está funcionando

Após iniciar a aplicação, você verá logs similares a:
```
Started ApiApplication in X.XXX seconds (JVM running for X.XXX)
```

### 4. Acessando a aplicação

- **API Base URL**: http://localhost:8080/api/clientes
- **Swagger UI**: http://localhost:8080/swagger-ui.html

### 5. Testando a API

#### Teste rápido via browser:
Acesse: http://localhost:8080/api/clientes

#### Teste via curl:
```bash
# Listar todos os clientes
curl http://localhost:8080/api/clientes

# Contar clientes
curl http://localhost:8080/api/clientes/contar

# Buscar cliente por ID
curl http://localhost:8080/api/clientes/1
```

### 6. Usando o Swagger UI

1. Acesse http://localhost:8080/swagger-ui.html
2. Explore os endpoints disponíveis
3. Teste os endpoints diretamente na interface
4. Veja a documentação completa da API

### 7. Configuração do Banco PostgreSQL (Opcional)

Se desejar usar PostgreSQL em vez do H2:

1. **Instale e configure o PostgreSQL**
2. **Crie um banco de dados**:
   ```sql
   CREATE DATABASE desafio_db;
   ```

3. **Execute o script de criação das tabelas**:
   ```bash
   psql -d desafio_db -f src/main/resources/schema.sql
   ```

4. **Atualize o arquivo `application.properties`**:
   - Descomente as linhas do PostgreSQL
   - Comente as linhas do H2
   - Configure usuário e senha

5. **Reinicie a aplicação**

### 8. Parando a Aplicação

Para parar a aplicação:
- **No terminal**: Pressione `Ctrl + C`
- **Se executando em background**: Use `kill` com o PID do processo

### 9. Logs e Troubleshooting

#### Verificando logs:
Os logs são exibidos no console. Para problemas comuns:

- **Porta 8080 em uso**: Altere a porta no `application.properties`:
  ```properties
  server.port=8081
  ```

- **Problemas de conexão com banco**: Verifique as configurações no `application.properties`

- **Erro de compilação**: Verifique se o Java 17 está sendo usado:
  ```bash
  echo $JAVA_HOME
  ```

### 10. Dados de Exemplo

A aplicação vem com dados de exemplo pré-carregados. Para ver os dados:
```bash
curl http://localhost:8080/api/clientes
```

### 11. Testando Operações CRUD

#### Criar cliente:
```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Teste Cliente",
    "email": "teste@email.com",
    "telefone": "(11) 99999-9999",
    "endereco": "Rua Teste, 123"
  }'
```

#### Atualizar cliente (substitua {id} pelo ID real):
```bash
curl -X PUT http://localhost:8080/api/clientes/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Teste Cliente Atualizado",
    "email": "teste@email.com",
    "telefone": "(11) 88888-8888",
    "endereco": "Rua Teste Atualizada, 456"
  }'
```

#### Deletar cliente:
```bash
curl -X DELETE http://localhost:8080/api/clientes/{id}
```

### 12. Próximos Passos

Após executar com sucesso:
1. Explore a documentação no Swagger UI
2. Teste todos os endpoints
3. Configure o PostgreSQL se necessário
4. Integre com sua aplicação frontend
5. Implemente funcionalidades adicionais conforme necessário

---

**Dica**: Use o Swagger UI para uma experiência mais amigável ao testar a API!

