# language: pt
@parte2 @frontend @webtables @regressivo
Funcionalidade: Parte 2 - Tabelas Web (Elements > Web Tables) e Desafio Bônus
  Como um engenheiro de QA Automation
  Eu quero manipular registros na página Web Tables (criar, editar, deletar e criar/deletar 12 registros dinamicamente)
  Para validar o funcionamento completo do CRUD e paginação da tabela

  Cenário: Criar, editar e deletar um novo registro no Web Tables
    Dado que acesso o site DemoQA na página inicial
    Quando escolho a opção "Elements" na página inicial
    E clico no submenu "Web Tables"
    E crio um novo registro na tabela
    Então o novo registro criado deve estar visível na tabela
    Quando edito o novo registro criado
    Então o registro editado deve apresentar os dados atualizados na tabela
    Quando deleto o novo registro criado
    Então o registro não deve mais estar presente na tabela

  @bonus
  Cenário: Bônus - Criar 12 novos registros de forma dinâmica através do Cucumber e deletar todos os novos registros criados
    Dado que acesso o site DemoQA na página inicial
    Quando escolho a opção "Elements" na página inicial
    E clico no submenu "Web Tables"
    E crio 12 novos registros de forma dinâmica através do Cucumber:
      | firstName | lastName  | age | email                      | salary | department   |
      | Lucas     | Silva     | 25  | lucas.silva@qa.com         | 4500   | QA           |
      | Mariana   | Santos    | 28  | mariana.santos@qa.com      | 5200   | Automation   |
      | Rafael    | Oliveira  | 31  | rafael.oliveira@qa.com     | 6100   | Engineering  |
      | Camila    | Souza     | 27  | camila.souza@qa.com        | 4900   | DevOps       |
      | Bruno     | Pereira   | 34  | bruno.pereira@qa.com       | 7200   | Architecture |
      | Juliana   | Costa     | 29  | juliana.costa@qa.com       | 5600   | Security     |
      | Diego     | Ferreira  | 32  | diego.ferreira@qa.com      | 6400   | Backend      |
      | Fernanda  | Rodrigues | 26  | fernanda.rodrigues@qa.com  | 4800   | Frontend     |
      | Thiago    | Almeida   | 35  | thiago.almeida@qa.com      | 7800   | Cloud        |
      | Patricia  | Nascimento| 30  | patricia.nascimento@qa.com | 5900   | Data         |
      | Gustavo   | Lima      | 33  | gustavo.lima@qa.com        | 6700   | Mobile       |
      | Larissa   | Araujo    | 24  | larissa.araujo@qa.com      | 4300   | Product      |
    Então todos os 12 novos registros criados devem estar presentes na tabela
    Quando deleto todos os novos registros criados dinamicamente
    Então nenhum dos 12 registros criados deve permanecer na tabela

