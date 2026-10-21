package br.com.accenture.desafioqa.parte2_frontend.steps;

import br.com.accenture.desafioqa.parte2_frontend.model.WebTableRecord;
import br.com.accenture.desafioqa.parte2_frontend.pages.WebTablesPage;
import br.com.accenture.desafioqa.parte2_frontend.utils.TestDataGenerator;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step Definitions do fluxo Elements > Web Tables (Fluxo Principal CRUD + Desafio Bônus de 12 registros dinâmicos).
 */
public class WebTablesSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebTablesSteps.class);

    private final WebTablesPage webTablesPage = new WebTablesPage();
    private WebTableRecord currentRecord;
    private final List<WebTableRecord> dynamicCreatedRecords = new ArrayList<>();

    @Quando("crio um novo registro na tabela")
    public void crioUmNovoRegistroNaTabela() {
        currentRecord = TestDataGenerator.generateRandomWebTableRecord();
        webTablesPage.createNewRecord(currentRecord);
    }

    @Então("o novo registro criado deve estar visível na tabela")
    public void oNovoRegistroCriadoDeveEstarVisivelNaTabela() {
        assertTrue(webTablesPage.isRecordPresent(currentRecord),
                "O novo registro criado deve estar visível na tabela: " + currentRecord);
    }

    @Quando("edito o novo registro criado")
    public void editoONovoRegistroCriado() {
        String originalEmail = currentRecord.getEmail();
        currentRecord.setFirstName(currentRecord.getFirstName() + "Editado");
        currentRecord.setDepartment("QA Lead");
        currentRecord.setSalary("9800");

        webTablesPage.editRecordByEmail(originalEmail, currentRecord);
    }

    @Então("o registro editado deve apresentar os dados atualizados na tabela")
    public void oRegistroEditadoDeveApresentarOsDadosAtualizadosNaTabela() {
        assertTrue(webTablesPage.isRecordPresent(currentRecord),
                "O registro editado deve exibir os novos valores na tabela: " + currentRecord);
    }

    @Quando("deleto o novo registro criado")
    public void deletoONovoRegistroCriado() {
        webTablesPage.deleteRecordByEmail(currentRecord.getEmail());
    }

    @Então("o registro não deve mais estar presente na tabela")
    public void oRegistroNaoDeveMaisEstarPresenteNaTabela() {
        assertFalse(webTablesPage.isEmailPresent(currentRecord.getEmail()),
                "O registro deletado não deve mais constar na tabela: " + currentRecord.getEmail());
    }

    @Quando("crio 12 novos registros de forma dinâmica através do Cucumber:")
    public void crio12NovosRegistrosDeFormaDinamicaAtravesDoCucumber(DataTable dataTable) {
        dynamicCreatedRecords.clear();
        webTablesPage.setPageSizeToMax();

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        assertEquals(12, rows.size(), "A tabela dinâmica do Cucumber deve conter exatamente 12 registros.");

        int index = 1;
        for (Map<String, String> row : rows) {
            WebTableRecord record = new WebTableRecord(
                    row.get("firstName"),
                    row.get("lastName"),
                    row.get("age"),
                    row.get("email"),
                    row.get("salary"),
                    row.get("department")
            );
            LOGGER.info("[Bônus {}/12] Criando registro dinâmico via Cucumber: {}", index++, record);
            webTablesPage.createNewRecord(record);
            dynamicCreatedRecords.add(record);
        }
    }

    @Então("todos os 12 novos registros criados devem estar presentes na tabela")
    public void todosOs12NovosRegistrosCriadosDevemEstarPresentesNaTabela() {
        assertEquals(12, dynamicCreatedRecords.size(), "Devem existir 12 registros na lista criada.");
        for (WebTableRecord record : dynamicCreatedRecords) {
            assertTrue(webTablesPage.isEmailPresent(record.getEmail()),
                    "O registro dinâmico '" + record.getEmail() + "' deve estar presente na tabela.");
        }
        LOGGER.info("[Bônus] Todos os 12 novos registros foram validados com sucesso na tabela!");
    }

    @Quando("deleto todos os novos registros criados dinamicamente")
    public void deletoTodosOsNovosRegistrosCriadosDinamicamente() {
        int index = 1;
        for (WebTableRecord record : dynamicCreatedRecords) {
            if (webTablesPage.isEmailPresent(record.getEmail())) {
                LOGGER.info("[Bônus {}/12] Deletando registro dinâmico: {}", index, record.getEmail());
                webTablesPage.deleteRecordByEmail(record.getEmail());
            } else {
                LOGGER.info("[Bônus {}/12] Registro '{}' já removido na atualização da tabela.", index, record.getEmail());
            }
            index++;
        }
    }

    @Então("nenhum dos 12 registros criados deve permanecer na tabela")
    public void nenhumDos12RegistrosCriadosDevePermanecerNaTabela() {
        for (WebTableRecord record : dynamicCreatedRecords) {
            assertFalse(webTablesPage.isEmailPresent(record.getEmail()),
                    "O registro dinâmico '" + record.getEmail() + "' deveria ter sido removido da tabela.");
        }
        LOGGER.info("[Bônus] Todos os 12 registros dinâmicos foram removidos e validados com sucesso!");
    }
}

