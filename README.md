POST http://localhost:8080/api/cartoes:
{
    "numeroCartao": 6549873025634501,
    "senha": "1234"
}

PUT http://localhost:8080/api/cartoes/transacao:
{
    "numeroCartao": 6549873025634501,
    "senha": "1234",
    "valor": 10
}