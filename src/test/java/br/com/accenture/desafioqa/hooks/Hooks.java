package br.com.accenture.desafioqa.hooks;

import br.com.accenture.desafioqa.config.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hooks do Cucumber para gerenciamento de ciclo de vida dos cenários de teste.
 * Cenários da Parte 1 (@parte1) rodam na camada de API sem abrir navegador.
 * Cenários da Parte 2 (@parte2) inicializam e finalizam o WebDriver automaticamente.
 */
public class Hooks {

    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);

    @Before("@parte1")
    public void beforeApiScenario(Scenario scenario) {
        LOGGER.info("=================================================================");
        LOGGER.info("[PARTE 1 - API] Iniciando cenário: {}", scenario.getName());
        LOGGER.info("=================================================================");
    }

    @After("@parte1")
    public void afterApiScenario(Scenario scenario) {
        LOGGER.info("[PARTE 1 - API] Finalizado cenário: {} | Status: {}", scenario.getName(), scenario.getStatus());
    }

    @Before("@parte2")
    public void beforeFrontendScenario(Scenario scenario) {
        LOGGER.info("=================================================================");
        LOGGER.info("[PARTE 2 - FRONTEND] Iniciando cenário: {}", scenario.getName());
        LOGGER.info("=================================================================");
        DriverFactory.getDriver();
    }

    @After("@parte2")
    public void afterFrontendScenario(Scenario scenario) {
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Evidencia_" + scenario.getName());
            }
        } catch (Exception e) {
            LOGGER.warn("Não foi possível capturar screenshot final do cenário: {}", e.getMessage());
        } finally {
            DriverFactory.quitDriver();
            LOGGER.info("[PARTE 2 - FRONTEND] Finalizado cenário: {} | Status: {}", scenario.getName(), scenario.getStatus());
        }
    }
}

