package br.com.accenture.desafioqa.parte2_frontend.steps;

import br.com.accenture.desafioqa.config.ConfigManager;
import br.com.accenture.desafioqa.parte2_frontend.model.StudentFormData;
import br.com.accenture.desafioqa.parte2_frontend.pages.PracticeFormPage;
import br.com.accenture.desafioqa.parte2_frontend.utils.TestDataGenerator;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step Definitions do fluxo Forms > Practice Form (Parte 2).
 */
public class PracticeFormSteps {

    private final PracticeFormPage practiceFormPage = new PracticeFormPage();
    private StudentFormData generatedStudent;
    private File uploadFile;

    @Quando("preencho todo o formulário com valores aleatórios e realizo upload do arquivo txt")
    public void preenchoTodoOFormularioComValoresAleatoriosERealizoUploadDoArquivoTxt() {
        generatedStudent = TestDataGenerator.generateRandomStudent();
        uploadFile = new File(ConfigManager.getUploadFilePath());
        assertTrue(uploadFile.exists(),
                "O arquivo .txt de upload deve existir no projeto: " + uploadFile.getAbsolutePath());

        practiceFormPage.fillEntireForm(generatedStudent, uploadFile);
    }

    @E("submeto o formulário de cadastro")
    public void submetoOFormularioDeCadastro() {
        practiceFormPage.submitForm();
    }

    @Então("garanto que um popup de confirmação foi aberto após o submit com o título {string}")
    public void garantoQueUmPopupDeConfirmacaoFoiAbertoAposOSubmitComOTitulo(String expectedTitle) {
        assertTrue(practiceFormPage.isConfirmationModalDisplayed(),
                "O modal de confirmação deve estar visível após o submit.");
        assertEquals(expectedTitle, practiceFormPage.getConfirmationModalTitle(),
                "O título do modal de confirmação deve corresponder ao esperado.");

        String modalText = practiceFormPage.getModalTableText();
        assertTrue(modalText.contains(generatedStudent.getFullName()),
                "O popup deve exibir o nome completo do estudante cadastrado.");
        assertTrue(modalText.contains(generatedStudent.getEmail()),
                "O popup deve exibir o e-mail do estudante cadastrado.");
        assertTrue(modalText.contains(uploadFile.getName()),
                "O popup deve exibir o nome do arquivo .txt enviado no upload.");
    }

    @E("fecho o popup de confirmação")
    public void fechoOPopupDeConfirmacao() {
        practiceFormPage.closeConfirmationModal();
    }
}

