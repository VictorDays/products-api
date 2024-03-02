# Documentação da API de Produtos

## Descrição
Esta API permite gerenciar produtos em um sistema de e-commerce e também oferece funcionalidades de autenticação para os usuários.

## Endpoints
- **POST /products**
  - Cria um novo produto com os dados fornecidos no corpo da requisição.
  
- **GET /products**
  - Retorna uma lista de todos os produtos cadastrados no sistema, incluindo links para detalhes de cada produto.
  
- **GET /products/{id}**
  - Retorna os detalhes de um produto específico com base no ID fornecido na URL, incluindo um link para a lista completa de produtos.
  
- **PUT /products/{id}**
  - Atualiza os dados de um produto existente com base no ID fornecido na URL, utilizando os dados fornecidos no corpo da requisição.
  
- **DELETE /products/{id}**
  - Exclui um produto existente com base no ID fornecido na URL.
  
- **POST /auth/login**
  - Realiza a autenticação do usuário com base no login e senha fornecidos no corpo da requisição e retorna um token de autenticação válido.
  
- **POST /auth/register**
  - Registra um novo usuário com base nos dados fornecidos no corpo da requisição.

## Parâmetros de Entrada
- **productDto**: Objeto JSON contendo os dados do produto a ser criado.
- **id**: Identificador único (UUID) do produto.
- **AuthenticationDTO**: Objeto JSON contendo as credenciais de login e senha do usuário.
- **RegisterDTO**: Objeto JSON contendo os dados para registrar um novo usuário.

## Parâmetros de Saída
- **ProductDTO**: Objeto JSON contendo os dados do produto.
- **List<ProductDTO>**: Lista de objetos JSON contendo os dados de vários produtos.
- **HttpStatus**: Status da resposta da requisição HTTP.
- **Token**: Token de autenticação válido.

## Respostas de Erro
- **404 Not Found**: Produto não encontrado.
- **400 Bad Request**: Requisição inválida.
- **401 Unauthorized**: Falha na autenticação do usuário.
- **500 Internal Server Error**: Erro interno do servidor.

## Exemplos de Uso

1. **Criar um novo produto:**

```
POST /products
{
    "name": "Nome do Produto",
    "description": "Descrição do Produto",
    "price": 9.99
}
```
2. **Obter detalhes de um produto específico:**
```
GET /products/{id}
```

3. **Atualizar um produto existente:**
```
PUT /products/{id}

Corpo da Requisição:
{
"name": "Novo Nome do Produto",
"description": "Nova Descrição do Produto",
"price": 19.99
}
```

4. **Excluir um produto existente:**
```
DELETE /products/{id}
```

5. **Autenticar um usuário:**
```
POST /auth/login

Corpo da Requisição:
{
"login": "nome_usuario",
"password": "senha_usuario"
}
```

6. **Registrar um novo usuário:**
```
POST /auth/register

Corpo da Requisição:
{
"login": "nome_usuario",
"password": "senha_usuario",
"role": "role_usuario"
}
```

## Notas Adicionais
- Os endpoints retornam objetos JSON no corpo da resposta.
- A validação dos dados de entrada é realizada com base nas anotações de validação do Bean Validation (jakarta.validation.Valid).
- Links HATEOAS são fornecidos para facilitar a navegação entre recursos relacionados.
