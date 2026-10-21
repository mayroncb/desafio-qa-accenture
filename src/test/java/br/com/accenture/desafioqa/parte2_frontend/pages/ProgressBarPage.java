package br.com.accenture.desafioqa.parte2_frontend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object representativa da página Widgets > Progress Bar.
 */
public class ProgressBarPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgressBarPage.class);

    private static final By START_STOP_BUTTON = By.id("startStopButton");
    private static final By RESET_BUTTON = By.id("resetButton");
    private static final By PROGRESS_BAR_VALUE = By.cssSelector("#progressBar .progress-bar, #progressBar[role='progressbar']");

    public int startAndStopBeforeThreshold(int maxPercentageThreshold) {
        removeAdsAndFloatingBanners();
        WebElement startStopBtn = waitForVisible(START_STOP_BUTTON);
        scrollIntoView(startStopBtn);

        LOGGER.info("Clicando em 'Start' na Progress Bar e monitorando para parar antes de {}%...", maxPercentageThreshold);
        jsClick(startStopBtn);

        int targetStop = Math.max(10, maxPercentageThreshold - 7); // Ex: para entre 18% e 24%
        long deadline = System.currentTimeMillis() + 8000;
        int currentVal = 0;

        while (System.currentTimeMillis() < deadline) {
            currentVal = getCurrentProgressPercentage();
            if (currentVal >= targetStop) {
                jsClick(driver.findElement(START_STOP_BUTTON));
                break;
            }
            sleepSilently(20);
        }

        sleepSilently(150);
        int stoppedValue = getCurrentProgressPercentage();
        LOGGER.info("Progress Bar parada em: {}% (Limite máximo permitido: {}%)", stoppedValue, maxPercentageThreshold);
        return stoppedValue;
    }

    public void startAndWaitUntilComplete() {
        LOGGER.info("Clicando em 'Start' novamente e aguardando a Progress Bar atingir 100%...");
        safeClick(START_STOP_BUTTON);

        long deadline = System.currentTimeMillis() + 20000;
        while (System.currentTimeMillis() < deadline) {
            if (getCurrentProgressPercentage() >= 100 && !driver.findElements(RESET_BUTTON).isEmpty()) {
                break;
            }
            sleepSilently(100);
        }

        waitForVisible(RESET_BUTTON);
        LOGGER.info("Progress Bar atingiu {}% e o botão 'Reset' está visível.", getCurrentProgressPercentage());
    }

    public void clickResetButton() {
        LOGGER.info("Clicando no botão 'Reset' para zerar a Progress Bar...");
        safeClick(RESET_BUTTON);
        wait.until(d -> getCurrentProgressPercentage() == 0);
        LOGGER.info("Progress Bar resetada para {}%.", getCurrentProgressPercentage());
    }

    public int getCurrentProgressPercentage() {
        WebElement bar = waitForPresent(PROGRESS_BAR_VALUE);
        String ariaNow = bar.getAttribute("aria-valuenow");
        if (ariaNow != null && !ariaNow.isBlank()) {
            return Integer.parseInt(ariaNow.trim());
        }
        String text = bar.getText().replace("%", "").trim();
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    private void sleepSilently(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

