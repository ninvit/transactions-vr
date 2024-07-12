
### Iniciar aplicação:
### Executar os comandos:
    mvn clean package
    docker compose up --build

### Para testes via Postman
### Criar novo cartão
```
Method: POST
URL: http://localhost:8080/cartoes
Body (json):
{
    "numeroCartao": "6549873025634501",
    "senha": "1234"
}
```

### Obter saldo do Cartão
```
Method: GET
URL: http://localhost:8080/cartoes/{numeroCartao} , onde {numeroCartao} é o número do cartão que se deseja consultar

```
### Realizar uma Transação
```
PUT http://localhost:8080/api/cartoes/transacao:
{
    "numeroCartao": 6549873025634501,
    "senha": "1234",
    "valor": 10
}
```