package br.com.accenture.desafioqa.parte2_frontend.pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Page Object representativa da página Alerts, Frame & Windows > Browser Windows.
 */
public class BrowserWindowsPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserWindowsPage.class);

    private static final By NEW_WINDOW_BUTTON = By.id("windowButton");
    private static final By SAMPLE_HEADING = By.id("sampleHeading");

    private String mainWindowHandle;

    public void clickNewWindowButton() {
        mainWindowHandle = driver.getWindowHandle();
        LOGGER.info("Janela principal registrada ({}). Clicando no botão 'New Window'...", mainWindowHandle);
        safeClick(NEW_WINDOW_BUTTON);
    }

    public boolean switchToNewWindow() {
        wait.until(d -> d.getWindowHandles().size() > 1);
        Set<String> allWindows = driver.getWindowHandles();
        for (String handle : allWindows) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                LOGGER.info("Foco alternado para a nova janela aberta: {}", handle);
                return true;
            }
        }
        return false;
    }

    public String getSamplePageHeadingText() {
        String text = waitForVisible(SAMPLE_HEADING).getText().trim();
        LOGGER.info("Mensagem encontrada na nova janela: '{}'", text);
        return text;
    }

    public void closeNewWindowAndReturnToMain() {
        LOGGER.info("Fechando a nova janela e retornando para a janela principal...");
        driver.close();
        driver.switchTo().window(mainWindowHandle);
        wait.until(d -> d.getWindowHandles().size() == 1);
    }

    public int getOpenWindowsCount() {
        return driver.getWindowHandles().size();
    }
}

