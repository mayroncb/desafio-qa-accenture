# language: pt
@parte2 @frontend @forms @regressivo
Funcionalidade: Parte 2 - Formulário de Prática (Forms > Practice Form)
  Como um engenheiro de QA Automation
  Eu quero preencher o formulário Practice Form com dados aleatórios e upload de arquivo .txt
  Para validar a abertura e o fechamento do modal de confirmação de submissão

  Cenário: Preencher todo o formulário Practice Form com valores aleatórios, fazer upload de .txt e validar popup
    Dado que acesso o site DemoQA na página inicial
    Quando escolho a opção "Forms" na página inicial
    E clico no submenu "Practice Form"
    E preencho todo o formulário com valores aleatórios e realizo upload do arquivo txt
    E submeto o formulário de cadastro
    Então garanto que um popup de confirmação foi aberto após o submit com o título "Thanks for submitting the form"
    E fecho o popup de confirmação

