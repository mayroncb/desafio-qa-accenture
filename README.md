# Desafio QA Automation Engineer - Accenture

Projeto de automação de testes End-to-End (**API + Frontend Web**) desenvolvido em **Java 17** utilizando **Selenium WebDriver 4**, **RestAssured 5**, **Cucumber 7 (BDD)**, **JUnit 5** e padrão de projeto **Page Object Model (POM)** para resolução completa do desafio técnico de QA Automation no portal [DemoQA](https://demoqa.com/).

---

## Sumário

1. [Arquitetura e Organização do Projeto](#1-arquitetura-e-organização-do-projeto)
2. [Tecnologias e Padrões Utilizados](#2-tecnologias-e-padrões-utilizados)
3. [Escopo Automatizado](#3-escopo-automatizado)
   - [Parte 1 - Desafio API (BookStore & Account)](#parte-1---desafio-api-bookstore--account)
   - [Parte 2 - Desafio Frontend (Elementos Visuais + Bônus)](#parte-2---desafio-frontend-elementos-visuais--bônus)
4. [Pré-requisitos e Configuração](#4-pré-requisitos-e-configuração)
5. [Como Executar os Testes](#5-como-executar-os-testes)
6. [Relatórios e Evidências de Execução](#6-relatórios-e-evidências-de-execução)

---

## 1. Arquitetura e Organização do Projeto

O projeto está claramente separado entre **Parte 1 (`parte1_api`)** e **Parte 2 (`parte2_frontend`)**, tanto na camada de especificações BDD (`src/test/resources/features`) quanto na camada de implementação (`src/test/java/br/com/accenture/desafioqa`):

```text
Desafio_QA/
├── pom.xml
├── README.md
├── Desafio_QA (004).pptx
└── src/
    └── test/
        ├── java/
        │   └── br/com/accenture/desafioqa/
        │       ├── config/
        │       │   ├── ConfigManager.java               # Leitura centralizada de propriedades e parâmetros JVM
        │       │   └── DriverFactory.java               # Gerenciamento ThreadLocal do Selenium WebDriver
        │       ├── hooks/
        │       │   └── Hooks.java                       # Ciclo de vida Cucumber (@parte1 e @parte2) + Screenshots
        │       ├── parte1_api/                          # ================= PARTE 1: DESAFIO API =================
        │       │   ├── client/
        │       │   │   ├── AccountApiClient.java        # Cliente HTTP para /Account/v1/*
        │       │   │   └── BookStoreApiClient.java      # Cliente HTTP para /BookStore/v1/*
        │       │   ├── context/
        │       │   │   └── ApiTestContext.java          # Estado compartilhado entre steps (execução contínua)
        │       │   ├── model/                           # POJOs de Request/Response (Jackson)
        │       │   └── steps/
        │       │       └── BookStoreApiSteps.java       # Step Definitions encadeados da Parte 1
        │       ├── parte2_frontend/                     # ================ PARTE 2: DESAFIO FRONTEND =============
        │       │   ├── model/
        │       │   │   ├── StudentFormData.java         # Modelo de dados do formulário Practice Form
        │       │   │   └── WebTableRecord.java          # Modelo de registro da tabela Web Tables
        │       │   ├── pages/                           # Padrão Page Object Model (POM)
        │       │   │   ├── BasePage.java
        │       │   │   ├── HomePage.java
        │       │   │   ├── SideMenuComponent.java
        │       │   │   ├── PracticeFormPage.java
        │       │   │   ├── BrowserWindowsPage.java
        │       │   │   ├── WebTablesPage.java
        │       │   │   ├── ProgressBarPage.java
        │       │   │   └── SortablePage.java
        │       │   ├── steps/                           # Step Definitions modulares da Parte 2
        │       │   │   ├── CommonNavigationSteps.java
        │       │   │   ├── PracticeFormSteps.java
        │       │   │   ├── BrowserWindowsSteps.java
        │       │   │   ├── WebTablesSteps.java
        │       │   │   ├── ProgressBarSteps.java
        │       │   │   └── SortableSteps.java
        │       │   └── utils/
        │       │       └── TestDataGenerator.java       # Gerador de massas de dados aleatórias
        │       └── runners/                             # Suites de Execução JUnit 5 + Cucumber
        │           ├── RunAllTests.java                 # Executa Parte 1 + Parte 2
        │           ├── RunParte1ApiTest.java            # Executa exclusivamente a Parte 1 (API)
        │           └── RunParte2FrontendTest.java       # Executa exclusivamente a Parte 2 (Frontend)
        └── resources/
            ├── config.properties
            ├── junit-platform.properties
            ├── files/
            │   └── arquivo_upload_desafio.txt           # Arquivo .txt versionado para upload no Practice Form
            └── features/
                ├── parte1_api/
                │   └── 01_bookstore_api.feature
                └── parte2_frontend/
                    ├── 01_practice_form.feature
                    ├── 02_browser_windows.feature
                    ├── 03_web_tables.feature
                    ├── 04_progress_bar.feature
                    └── 05_sortable.feature
```

---

## 2. Tecnologias e Padrões Utilizados

- **Java 17** e **Apache Maven**
- **Selenium WebDriver 4.25.0** (com *Selenium Manager* nativo para resolução automática do driver do navegador)
- **Cucumber 7.18.1 (BDD)** com especificações Gherkin em português (`# language: pt`) e injeção de dependência via `cucumber-picocontainer`
- **RestAssured 5.5.0** + **Jackson Databind 2.17.2** para testes de API REST na Parte 1
- **JUnit 5 (JUnit Platform Suite)** para orquestração de suítes e asserções
- **Padrões de Projeto Aplicados**:
  - **Page Object Model (POM)**: encapsulamento de seletores e interações de UI em classes de página coesas herdando de `BasePage`.
  - **Component Object**: abstração do menu lateral (`SideMenuComponent`) reutilizado em todos os fluxos da Parte 2.
  - **API Client + Scenario Context (`ApiTestContext`)**: desacoplamento das chamadas HTTP e compartilhamento seguro de estado (`userID`, `token`, `books`) ao longo da execução contínua da Parte 1.
  - **Factory Pattern (`DriverFactory`)**: instanciação centralizada e *thread-safe* do `WebDriver`.
  - **Data Generator / Builder**: geração dinâmica de dados aleatórios para formulários e tabelas (`TestDataGenerator`).

---

## 3. Escopo Automatizado

### Parte 1 - Desafio API (BookStore & Account)
Arquivo: `src/test/resources/features/parte1_api/01_bookstore_api.feature`

Todos os 6 passos são executados **de forma contínua e em uma única execução**:
1. **Criar um usuário** (`POST https://demoqa.com/Account/v1/User`) -> Valida status `201` e extrai `userID`.
2. **Gerar um token de acesso** (`POST https://demoqa.com/Account/v1/GenerateToken`) -> Valida status `200`, `status = "Success"` e armazena o token Bearer.
3. **Confirmar autorização do usuário** (`POST https://demoqa.com/Account/v1/Authorized`) -> Valida status `200` e retorno `true`.
4. **Listar livros disponíveis** (`GET https://demoqa.com/BookStore/v1/Books`) -> Valida status `200` e catálogo disponível.
5. **Alugar dois livros de livre escolha** (`POST https://demoqa.com/BookStore/v1/Books`) -> Envia token Bearer e `collectionOfIsbns` com 2 livros do catálogo, validando status `201`.
6. **Listar detalhes do usuário com os livros escolhidos** (`GET https://demoqa.com/Account/v1/User/{userID}`) -> Valida status `200` e confirma que os dois livros reservados constam no perfil do usuário criado.

---

### Parte 2 - Desafio Frontend (Elementos Visuais + Bônus)
Arquivos em: `src/test/resources/features/parte2_frontend/`

1. **Forms > Practice Form (`01_practice_form.feature`)**:
   - Acessa `https://demoqa.com/`, seleciona `Forms` > `Practice Form`.
   - Preenche todo o formulário com valores aleatórios gerados dinamicamente.
   - Realiza upload do arquivo `.txt` versionado em `src/test/resources/files/arquivo_upload_desafio.txt`.
   - Submete o formulário, garante a abertura do popup `"Thanks for submitting the form"`, valida os dados submetidos e fecha o popup.
2. **Alerts, Frame & Windows > Browser Windows (`02_browser_windows.feature`)**:
   - Acessa `https://demoqa.com/`, seleciona `Alerts, Frame & Windows` > `Browser Windows`.
   - Clica no botão `New Window`, alterna o foco para a nova janela aberta, valida a mensagem `"This is a sample page"` e fecha a nova janela.
3. **Elements > Web Tables + Desafio Bônus (`03_web_tables.feature`)**:
   - **Cenário Principal**: Cria um novo registro, edita o registro criado e deleta o registro criado.
   - **Cenário Bônus (`@bonus`)**: Cria **12 novos registros de forma dinâmica através do Cucumber (`DataTable`)**, valida a presença dos 12 registros e deleta todos os 12 novos registros criados.
4. **Widgets > Progress Bar (`04_progress_bar.feature`)**:
   - Acessa `https://demoqa.com/`, seleciona `Widgets` > `Progress Bar`.
   - Clica em `Start`, para a barra antes dos `25%` e valida que o percentual é `<= 25%`.
   - Clica em `Start` novamente, aguarda atingir `100%` e aciona o botão `Reset`, validando o retorno a `0%`.
5. **Interactions > Sortable (`05_sortable.feature`)**:
   - Acessa `https://demoqa.com/`, seleciona `Interactions` > `Sortable`.
   - Utilizando métodos de *Drag and Drop*, posiciona todos os elementos na ordem crescente (`One`, `Two`, `Three`, `Four`, `Five`, `Six`).

---

## 4. Pré-requisitos e Configuração

- **Java JDK 17+**
- **Apache Maven 3.8+**
- **Google Chrome** ou **Microsoft Edge** instalado

As configurações padrão encontram-se em `src/test/resources/config.properties` e podem ser sobrescritas via linha de comando (`-Dheadless=true`, `-Dbrowser=edge`).

---

## 5. Como Executar os Testes

### Executar TODO o Desafio (Parte 1 + Parte 2)
```bash
mvn clean test
```

### Executar APENAS a Parte 1 (Desafio API - execução única e contínua)
```bash
mvn test -Dtest=RunParte1ApiTest
```

### Executar APENAS a Parte 2 (Desafio Frontend + Bônus)
```bash
mvn test -Dtest=RunParte2FrontendTest
```

### Executar em Modo Headless (Sem abrir janela visual do navegador)
```bash
mvn test -Dheadless=true
```

### Executar Filtrando por Tags do Cucumber
```bash
# Executar apenas o cenário Bônus de 12 registros dinâmicos:
mvn test -Dcucumber.filter.tags="@bonus"
```

---

## 6. Relatórios e Evidências de Execução

Após a execução dos testes, os relatórios HTML e JSON do Cucumber (com screenshots anexados automaticamente ao final de cada cenário de Frontend) são gerados no diretório `target/cucumber-reports/`:

- Relatório Completo: `target/cucumber-reports/cucumber-html-report.html`
- Relatório Parte 1 (API): `target/cucumber-reports/parte1-api-report.html`
- Relatório Parte 2 (Frontend): `target/cucumber-reports/parte2-frontend-report.html`

