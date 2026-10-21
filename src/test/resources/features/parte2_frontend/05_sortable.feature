# language: pt
@parte2 @frontend @sortable @regressivo
Funcionalidade: Parte 2 - Ordenação com Drag and Drop (Interactions > Sortable)
  Como um engenheiro de QA Automation
  Eu quero ordenar os elementos da lista Sortable utilizando métodos de drag and drop
  Para garantir que todos os itens fiquem posicionados na ordem crescente

  Cenário: Colocar os elementos na ordem crescente utilizando métodos de drag and drop
    Dado que acesso o site DemoQA na página inicial
    Quando escolho a opção "Interactions" na página inicial
    E clico no submenu "Sortable"
    E utilizo métodos de drag and drop para colocar os elementos na ordem crescente
    Então os elementos da lista devem estar ordenados de forma crescente de "One" a "Six"

