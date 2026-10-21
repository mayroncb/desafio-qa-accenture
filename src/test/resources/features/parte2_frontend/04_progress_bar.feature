# language: pt
@parte2 @frontend @progressbar @regressivo
Funcionalidade: Parte 2 - Barra de Progresso (Widgets > Progress Bar)
  Como um engenheiro de QA Automation
  Eu quero controlar a execução da Progress Bar parando antes dos 25% e depois resetando ao atingir 100%
  Para validar o controle dinâmico de progresso do componente

  Cenário: Parar a Progress Bar antes dos 25%, validar o percentual e resetar ao atingir 100%
    Dado que acesso o site DemoQA na página inicial
    Quando escolho a opção "Widgets" na página inicial
    E clico no submenu "Progress Bar"
    E clico no botão Start e paro a barra de progresso antes dos 25%
    Então valido que o valor da Progress Bar é menor ou igual aos 25%
    Quando aperto Start novamente e aguardo a barra chegar aos 100%
    E reseto a progress bar ao atingir 100%
    Então a progress bar deve voltar ao valor inicial de 0%

