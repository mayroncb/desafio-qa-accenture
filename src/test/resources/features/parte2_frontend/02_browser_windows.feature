# language: pt
@parte2 @frontend @windows @regressivo
Funcionalidade: Parte 2 - Janelas do Navegador (Alerts, Frame & Windows > Browser Windows)
  Como um engenheiro de QA Automation
  Eu quero abrir uma nova janela a partir do menu Browser Windows
  Para validar a exibição da mensagem "This is a sample page" e fechar a janela aberta

  Cenário: Abrir nova janela e validar mensagem "This is a sample page"
    Dado que acesso o site DemoQA na página inicial
    Quando escolho a opção "Alerts, Frame & Windows" na página inicial
    E clico no submenu "Browser Windows"
    E clico no botão "New Window"
    Então certifico-me que uma nova janela foi aberta e valido a mensagem "This is a sample page"
    E fecho a nova janela aberta retornando para a janela principal

