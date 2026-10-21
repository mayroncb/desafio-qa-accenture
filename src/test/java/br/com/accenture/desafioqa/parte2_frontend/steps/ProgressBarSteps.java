package br.com.accenture.desafioqa.parte2_frontend.steps;

import br.com.accenture.desafioqa.parte2_frontend.pages.ProgressBarPage;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step Definitions do fluxo Widgets > Progress Bar (Parte 2).
 */
public class ProgressBarSteps {

    private final ProgressBarPage progressBarPage = new ProgressBarPage();
    private int stoppedPercentage;

    @Quando("clico no botão Start e paro a barra de progresso antes dos {int}%")
    public void clicoNoBotaoStartEParoABarraDeProgressoAntesDos(int maxPercentage) {
        stoppedPercentage = progressBarPage.startAndStopBeforeThreshold(maxPercentage);
    }

    @Então("valido que o valor da Progress Bar é menor ou igual aos {int}%")
    public void validoQueOValorDaProgressBarEMenorOuIgualAos(int maxPercentage) {
        assertTrue(stoppedPercentage > 0 && stoppedPercentage <= maxPercentage,
                String.format("O valor da Progress Bar (%d%%) deve ser maior que 0%% e menor ou igual a %d%%.",
                        stoppedPercentage, maxPercentage));
    }

    @Quando("aperto Start novamente e aguardo a barra chegar aos 100%")
    public void apertoStartNovamenteEAguardoABarraChegarAos100() {
        progressBarPage.startAndWaitUntilComplete();
        assertEquals(100, progressBarPage.getCurrentProgressPercentage(),
                "A Progress Bar deve ter atingido 100%.");
    }

    @E("reseto a progress bar ao atingir 100%")
    public void resetoAProgressBarAoAtingir100() {
        progressBarPage.clickResetButton();
    }

    @Então("a progress bar deve voltar ao valor inicial de {int}%")
    public void aProgressBarDeveVoltarAoValorInicialDe(int expectedPercentage) {
        assertEquals(expectedPercentage, progressBarPage.getCurrentProgressPercentage(),
                "Após o Reset, a Progress Bar deve retornar para 0%.");
    }
}

