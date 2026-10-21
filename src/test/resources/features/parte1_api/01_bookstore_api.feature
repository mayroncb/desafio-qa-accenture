# language: pt
@parte1 @api @regressivo
Funcionalidade: Parte 1 - Desafio API BookStore e Account (DemoQA)
  Como um engenheiro de QA Automation
  Eu quero executar o fluxo completo de API do DemoQA de forma contínua e em uma única execução
  Para criar um usuário, autenticá-lo, verificar sua autorização, listar o catálogo, alugar dois livros e consultar os detalhes do usuário com os livros reservados

  Cenário: Fluxo contínuo de criação de usuário, autorização, aluguel de dois livros e consulta de detalhes
    Dado que possuo credenciais válidas e dinâmicas para um novo usuário na API DemoQA
    Quando envio a requisição para criar um usuário no endpoint "/Account/v1/User"
    Então o usuário deve ser criado com sucesso retornando status 201 e um userID válido
    Quando envio a requisição para gerar um token de acesso no endpoint "/Account/v1/GenerateToken"
    Então o token de acesso deve ser gerado com sucesso retornando status 200 e status "Success"
    Quando confirmo se o usuário criado está autorizado no endpoint "/Account/v1/Authorized"
    Então a API deve confirmar que o usuário está autorizado retornando status 200 e valor true
    Quando consulto a lista de livros disponíveis no endpoint "/BookStore/v1/Books"
    Então a lista de livros disponíveis deve ser retornada com status 200 contendo ao menos 2 livros
    Quando alugo dois livros de livre escolha do catálogo disponível no endpoint "/BookStore/v1/Books"
    Então o aluguel dos dois livros deve ser registrado com status 201 contendo os respectivos ISBNs
    Quando consulto os detalhes do usuário criado no endpoint "/Account/v1/User/{userID}"
    Então os detalhes do usuário devem ser retornados com status 200 contendo exatamente os dois livros escolhidos

