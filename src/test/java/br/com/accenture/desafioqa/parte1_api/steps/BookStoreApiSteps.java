package br.com.accenture.desafioqa.parte1_api.steps;

import br.com.accenture.desafioqa.parte1_api.client.AccountApiClient;
import br.com.accenture.desafioqa.parte1_api.client.BookStoreApiClient;
import br.com.accenture.desafioqa.parte1_api.context.ApiTestContext;
import br.com.accenture.desafioqa.parte1_api.model.AccountCredentialsRequest;
import br.com.accenture.desafioqa.parte1_api.model.AddBooksRequest;
import br.com.accenture.desafioqa.parte1_api.model.BookModel;
import br.com.accenture.desafioqa.parte1_api.model.BooksListResponse;
import br.com.accenture.desafioqa.parte1_api.model.CreateUserResponse;
import br.com.accenture.desafioqa.parte1_api.model.IsbnModel;
import br.com.accenture.desafioqa.parte1_api.model.TokenResponse;
import br.com.accenture.desafioqa.parte1_api.model.UserDetailsResponse;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step Definitions da Parte 1 (Desafio API - BookStore & Account).
 * Executa os 6 passos do desafio de forma contínua e encadeada em uma única execução.
 */
public class BookStoreApiSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookStoreApiSteps.class);

    private final ApiTestContext context;
    private final AccountApiClient accountClient;
    private final BookStoreApiClient bookStoreClient;

    public BookStoreApiSteps(ApiTestContext context) {
        this.context = context;
        this.accountClient = new AccountApiClient();
        this.bookStoreClient = new BookStoreApiClient();
    }

    @Dado("que possuo credenciais válidas e dinâmicas para um novo usuário na API DemoQA")
    public void quePossuoCredenciaisValidasEDinamicasParaUmNovoUsuarioNaApiDemoQA() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        String userName = "qa_accenture_" + uniqueSuffix;
        String password = "Qa@" + uniqueSuffix + "9!";

        AccountCredentialsRequest credentials = new AccountCredentialsRequest(userName, password);
        context.setCredentials(credentials);
        LOGGER.info("[Passo 0] Credenciais geradas -> userName: {}", userName);
    }

    @Quando("envio a requisição para criar um usuário no endpoint {string}")
    public void envioARequisicaoParaCriarUmUsuarioNoEndpoint(String endpoint) {
        LOGGER.info("[Passo 1] Criando usuário em {}...", endpoint);
        Response response = accountClient.createUser(endpoint, context.getCredentials());
        context.setLastResponse(response);
        LOGGER.info("[Passo 1] Status: {} | Response Body: {}", response.getStatusCode(), response.asString());
    }

    @Então("o usuário deve ser criado com sucesso retornando status {int} e um userID válido")
    public void oUsuarioDeveSerCriadoComSucessoRetornandoStatusEUmUserIDValido(int expectedStatus) {
        Response response = context.getLastResponse();
        assertEquals(expectedStatus, response.getStatusCode(),
                "Status HTTP inesperado ao criar usuário: " + response.asString());

        CreateUserResponse createdUser = response.as(CreateUserResponse.class);
        assertNotNull(createdUser.getUserId(), "O userID retornado não deve ser nulo.");
        assertFalse(createdUser.getUserId().isBlank(), "O userID retornado não deve estar em branco.");
        assertEquals(context.getCredentials().getUserName(), createdUser.getUsername(),
                "O username retornado na criação deve corresponder ao enviado.");

        context.setCreatedUser(createdUser);
        LOGGER.info("[Passo 1 Validado] Usuário criado com userID: {}", createdUser.getUserId());
    }

    @Quando("envio a requisição para gerar um token de acesso no endpoint {string}")
    public void envioARequisicaoParaGerarUmTokenDeAcessoNoEndpoint(String endpoint) {
        LOGGER.info("[Passo 2] Gerando token de acesso em {}...", endpoint);
        Response response = accountClient.generateToken(endpoint, context.getCredentials());
        context.setLastResponse(response);
        LOGGER.info("[Passo 2] Status: {} | Response Body: {}", response.getStatusCode(), response.asString());
    }

    @Então("o token de acesso deve ser gerado com sucesso retornando status {int} e status {string}")
    public void oTokenDeAcessoDeveSerGeradoComSucessoRetornandoStatusEStatus(int expectedStatus, String expectedTokenStatus) {
        Response response = context.getLastResponse();
        assertEquals(expectedStatus, response.getStatusCode(),
                "Status HTTP inesperado ao gerar token: " + response.asString());

        TokenResponse tokenResponse = response.as(TokenResponse.class);
        assertEquals(expectedTokenStatus, tokenResponse.getStatus(), "Status do token deve ser 'Success'.");
        assertNotNull(tokenResponse.getToken(), "O token JWT não deve ser nulo.");
        assertFalse(tokenResponse.getToken().isBlank(), "O token JWT não deve estar vazio.");

        context.setTokenResponse(tokenResponse);
        LOGGER.info("[Passo 2 Validado] Token gerado com sucesso. Expira em: {}", tokenResponse.getExpires());
    }

    @Quando("confirmo se o usuário criado está autorizado no endpoint {string}")
    public void confirmoSeOUsuarioCriadoEstaAutorizadoNoEndpoint(String endpoint) {
        LOGGER.info("[Passo 3] Verificando autorização do usuário em {}...", endpoint);
        Response response = accountClient.isAuthorized(endpoint, context.getCredentials());
        context.setLastResponse(response);
        LOGGER.info("[Passo 3] Status: {} | Response Body: {}", response.getStatusCode(), response.asString());
    }

    @Então("a API deve confirmar que o usuário está autorizado retornando status {int} e valor true")
    public void aApiDeveConfirmarQueOUsuarioEstaAutorizadoRetornandoStatusEValorTrue(int expectedStatus) {
        Response response = context.getLastResponse();
        assertEquals(expectedStatus, response.getStatusCode(),
                "Status HTTP inesperado na confirmação de autorização: " + response.asString());

        boolean authorized = Boolean.parseBoolean(response.asString().trim());
        assertTrue(authorized, "O usuário recém-autenticado deve estar autorizado (true).");
        LOGGER.info("[Passo 3 Validado] Usuário confirmado como autorizado (true).");
    }

    @Quando("consulto a lista de livros disponíveis no endpoint {string}")
    public void consultoAListaDeLivrosDisponiveisNoEndpoint(String endpoint) {
        LOGGER.info("[Passo 4] Listando livros disponíveis em {}...", endpoint);
        Response response = bookStoreClient.listBooks(endpoint);
        context.setLastResponse(response);
        LOGGER.info("[Passo 4] Status: {}", response.getStatusCode());
    }

    @Então("a lista de livros disponíveis deve ser retornada com status {int} contendo ao menos {int} livros")
    public void aListaDeLivrosDisponiveisDeveSerRetornadaComStatusContendoAoMenosLivros(int expectedStatus, int minBooks) {
        Response response = context.getLastResponse();
        assertEquals(expectedStatus, response.getStatusCode(),
                "Status HTTP inesperado ao listar livros: " + response.asString());

        BooksListResponse booksListResponse = response.as(BooksListResponse.class);
        assertNotNull(booksListResponse.getBooks(), "A lista de livros não deve ser nula.");
        assertTrue(booksListResponse.getBooks().size() >= minBooks,
                "O catálogo deve possuir ao menos " + minBooks + " livros disponíveis.");

        context.setAvailableBooks(booksListResponse.getBooks());
        LOGGER.info("[Passo 4 Validado] Total de livros disponíveis encontrados: {}", booksListResponse.getBooks().size());
        booksListResponse.getBooks().forEach(book ->
                LOGGER.info("   -> Livro disponível: ISBN={} | Título='{}' | Autor='{}'",
                        book.getIsbn(), book.getTitle(), book.getAuthor()));
    }

    @Quando("alugo dois livros de livre escolha do catálogo disponível no endpoint {string}")
    public void alugoDoisLivrosDeLivreEscolhaDoCatalogoDisponivelNoEndpoint(String endpoint) {
        List<BookModel> availableBooks = context.getAvailableBooks();
        BookModel firstChoice = availableBooks.get(0);
        BookModel secondChoice = availableBooks.get(1);
        List<BookModel> chosenBooks = Arrays.asList(firstChoice, secondChoice);
        context.setSelectedBooks(chosenBooks);

        List<IsbnModel> collectionOfIsbns = chosenBooks.stream()
                .map(book -> new IsbnModel(book.getIsbn()))
                .collect(Collectors.toList());

        AddBooksRequest rentRequest = new AddBooksRequest(
                context.getCreatedUser().getUserId(),
                collectionOfIsbns
        );

        LOGGER.info("[Passo 5] Alugando 2 livros em {}: [1] '{}' ({}) e [2] '{}' ({})",
                endpoint, firstChoice.getTitle(), firstChoice.getIsbn(), secondChoice.getTitle(), secondChoice.getIsbn());

        Response response = bookStoreClient.rentBooks(
                endpoint,
                rentRequest,
                context.getTokenResponse().getToken()
        );
        context.setLastResponse(response);
        LOGGER.info("[Passo 5] Status: {} | Response Body: {}", response.getStatusCode(), response.asString());
    }

    @Então("o aluguel dos dois livros deve ser registrado com status {int} contendo os respectivos ISBNs")
    public void oAluguelDosDoisLivrosDeveSerRegistradoComStatusContendoOsRespectivosIsbns(int expectedStatus) {
        Response response = context.getLastResponse();
        assertEquals(expectedStatus, response.getStatusCode(),
                "Status HTTP inesperado ao alugar livros: " + response.asString());

        List<String> returnedIsbns = response.jsonPath().getList("books.isbn");
        assertNotNull(returnedIsbns, "A resposta do aluguel deve conter a lista de ISBNs.");
        assertEquals(2, returnedIsbns.size(), "Devem constar exatamente 2 livros alugados na resposta.");

        for (BookModel chosen : context.getSelectedBooks()) {
            assertTrue(returnedIsbns.contains(chosen.getIsbn()),
                    "O ISBN escolhido " + chosen.getIsbn() + " deve estar presente na resposta do aluguel.");
        }
        LOGGER.info("[Passo 5 Validado] Livros alugados com sucesso: {}", returnedIsbns);
    }

    @Quando("consulto os detalhes do usuário criado no endpoint {string}")
    public void consultoOsDetalhesDoUsuarioCriadoNoEndpoint(String endpointTemplate) {
        String userId = context.getCreatedUser().getUserId();
        LOGGER.info("[Passo 6] Consultando detalhes do usuário {} em {}...", userId, endpointTemplate);

        Response response = accountClient.getUserDetails(
                endpointTemplate,
                userId,
                context.getTokenResponse().getToken()
        );
        context.setLastResponse(response);
        LOGGER.info("[Passo 6] Status: {} | Response Body: {}", response.getStatusCode(), response.asString());
    }

    @Então("os detalhes do usuário devem ser retornados com status {int} contendo exatamente os dois livros escolhidos")
    public void osDetalhesDoUsuarioDevemSerRetornadosComStatusContendoExatamenteOsDoisLivrosEscolhidos(int expectedStatus) {
        Response response = context.getLastResponse();
        assertEquals(expectedStatus, response.getStatusCode(),
                "Status HTTP inesperado ao consultar detalhes do usuário: " + response.asString());

        UserDetailsResponse userDetails = response.as(UserDetailsResponse.class);
        context.setUserDetailsResponse(userDetails);

        assertEquals(context.getCreatedUser().getUserId(), userDetails.getUserId(),
                "O userId nos detalhes do usuário deve ser idêntico ao usuário criado.");
        assertEquals(context.getCredentials().getUserName(), userDetails.getUsername(),
                "O username nos detalhes do usuário deve ser idêntico ao usuário criado.");
        assertNotNull(userDetails.getBooks(), "A lista de livros do usuário não deve ser nula.");
        assertEquals(2, userDetails.getBooks().size(),
                "O usuário deve possuir exatamente 2 livros reservados em seus detalhes.");

        List<String> userBookIsbns = userDetails.getBooks().stream()
                .map(BookModel::getIsbn)
                .collect(Collectors.toList());

        for (BookModel chosen : context.getSelectedBooks()) {
            assertTrue(userBookIsbns.contains(chosen.getIsbn()),
                    "O livro escolhido '" + chosen.getTitle() + "' (" + chosen.getIsbn() + ") deve constar nos detalhes do usuário.");
        }

        LOGGER.info("[Passo 6 Validado] Detalhes do usuário '{}' (ID: {}) validados com sucesso!",
                userDetails.getUsername(), userDetails.getUserId());
        userDetails.getBooks().forEach(book ->
                LOGGER.info("   -> Livro reservado no perfil: ISBN={} | Título='{}' | Autor='{}'",
                        book.getIsbn(), book.getTitle(), book.getAuthor()));
    }
}

