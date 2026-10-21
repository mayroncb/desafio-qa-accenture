package br.com.accenture.desafioqa.parte2_frontend.steps;

import br.com.accenture.desafioqa.parte2_frontend.pages.BrowserWindowsPage;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step Definitions do fluxo Alerts, Frame & Windows > Browser Windows (Parte 2).
 */
public class BrowserWindowsSteps {

    private final BrowserWindowsPage browserWindowsPage = new BrowserWindowsPage();

    @Quando("clico no botão {string}")
    public void clicoNoBotao(String buttonName) {
        browserWindowsPage.clickNewWindowButton();
    }

    @Então("certifico-me que uma nova janela foi aberta e valido a mensagem {string}")
    public void certificoMeQueUmaNovaJanelaFoiAbertaEValidoAMensagem(String expectedMessage) {
        assertTrue(browserWindowsPage.switchToNewWindow(),
                "Uma nova janela do navegador deve ter sido aberta.");
        assertEquals(expectedMessage, browserWindowsPage.getSamplePageHeadingText(),
                "A mensagem exibida na nova janela deve ser '" + expectedMessage + "'.");
    }

    @E("fecho a nova janela aberta retornando para a janela principal")
    public void fechoANovaJanelaAbertaRetornandoParaAJanelaPrincipal() {
        browserWindowsPage.closeNewWindowAndReturnToMain();
        assertEquals(1, browserWindowsPage.getOpenWindowsCount(),
                "Apenas a janela principal deve permanecer aberta após fechar a nova janela.");
    }
}

