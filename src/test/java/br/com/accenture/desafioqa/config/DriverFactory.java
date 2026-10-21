package br.com.accenture.desafioqa.config;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

/**
 * Fábrica e gerenciador de instâncias do Selenium WebDriver utilizando ThreadLocal.
 * Suporta execução com navegador visível ou em modo headless (-Dheadless=true).
 */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER_POOL = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        if (DRIVER_POOL.get() == null) {
            DRIVER_POOL.set(createDriver());
        }
        return DRIVER_POOL.get();
    }

    private static WebDriver createDriver() {
        String browser = ConfigManager.getProperty("browser", "chrome").toLowerCase();
        boolean headless = ConfigManager.getBoolean("headless", false);

        WebDriver driver;
        switch (browser) {
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setAcceptInsecureCerts(true);
                edgeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                edgeOptions.addArguments("--remote-allow-origins=*");
                edgeOptions.addArguments("--ignore-certificate-errors");
                edgeOptions.addArguments("--disable-notifications");
                edgeOptions.addArguments("--window-size=1920,1080");
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setAcceptInsecureCerts(true);
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--ignore-certificate-errors");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--window-size=1920,1080");
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        if (!headless) {
            try {
                driver.manage().window().maximize();
            } catch (Exception ignored) {
            }
        }

        int pageLoadTimeout = ConfigManager.getInt("page.load.timeout.seconds", 45);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER_POOL.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
            } finally {
                DRIVER_POOL.remove();
            }
        }
    }
}
