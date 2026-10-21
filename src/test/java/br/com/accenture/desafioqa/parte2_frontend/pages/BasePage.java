package br.com.accenture.desafioqa.parte2_frontend.pages;

import br.com.accenture.desafioqa.config.ConfigManager;
import br.com.accenture.desafioqa.config.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Classe base para todas as Page Objects do projeto (Padrão Page Object Model).
 * Centraliza esperas explícitas, manipulação segura de elementos e limpeza de banners/anúncios fixos do DemoQA.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        int explicitWaitSeconds = ConfigManager.getInt("explicit.wait.seconds", 15);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void removeAdsAndFloatingBanners() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "document.querySelectorAll('#fixedban, footer, iframe, .ad-plus-anchor, [id^=\"google_ads\"]').forEach(el => el.remove());"
            );
        } catch (Exception ignored) {
        }
    }

    protected void scrollIntoView(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element
            );
        } catch (Exception ignored) {
        }
    }

    protected void safeClick(By locator) {
        removeAdsAndFloatingBanners();
        WebElement element = waitForVisible(locator);
        scrollIntoView(element);
        try {
            waitForClickable(locator).click();
        } catch (ElementClickInterceptedException e) {
            removeAdsAndFloatingBanners();
            jsClick(element);
        }
    }

    protected void safeClick(WebElement element) {
        removeAdsAndFloatingBanners();
        scrollIntoView(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            removeAdsAndFloatingBanners();
            jsClick(element);
        }
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void clearAndType(By locator, String text) {
        WebElement element = waitForVisible(locator);
        scrollIntoView(element);
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.BACK_SPACE);
        element.clear();
        element.sendKeys(text);
    }
}

