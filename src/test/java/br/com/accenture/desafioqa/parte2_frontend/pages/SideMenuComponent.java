package br.com.accenture.desafioqa.parte2_frontend.pages;

import br.com.accenture.desafioqa.config.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Componente Page Object representativo do menu lateral (Left Panel) do DemoQA.
 * Inclui resiliência contra menus colapsados, anúncios sobrepostos e fallback de navegação.
 */
public class SideMenuComponent extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SideMenuComponent.class);

    private static final Map<String, String> SUBMENU_ROUTES = new HashMap<>();

    static {
        SUBMENU_ROUTES.put("Practice Form", "/automation-practice-form");
        SUBMENU_ROUTES.put("Browser Windows", "/browser-windows");
        SUBMENU_ROUTES.put("Web Tables", "/webtables");
        SUBMENU_ROUTES.put("Progress Bar", "/progress-bar");
        SUBMENU_ROUTES.put("Sortable", "/sortable");
    }

    public void clickSubmenuItem(String submenuName) {
        LOGGER.info("Clicando no submenu lateral: '{}'", submenuName);
        removeAdsAndFloatingBanners();

        String expectedRoute = SUBMENU_ROUTES.get(submenuName);

        // Se o grupo do menu estiver colapsado, expande o grupo correspondente
        ensureGroupIsExpanded(submenuName);

        By submenuLinkLocator = By.xpath(String.format(
                "//div[contains(@class,'left-pannel')]//li[contains(@class,'btn-light')][.//span[@class='text' and normalize-space()='%s']] | " +
                "//div[contains(@class,'left-pannel')]//span[@class='text' and normalize-space()='%s']",
                submenuName, submenuName
        ));

        try {
            WebElement element = waitForVisible(submenuLinkLocator);
            scrollIntoView(element);
            safeClick(element);
        } catch (Exception e) {
            LOGGER.warn("Tentando clique alternativo via JavaScript no submenu '{}': {}", submenuName, e.getMessage());
            List<WebElement> elements = driver.findElements(submenuLinkLocator);
            if (!elements.isEmpty()) {
                scrollIntoView(elements.get(0));
                jsClick(elements.get(0));
            }
        }

        // Caso o clique não tenha propagado o roteamento (comum no DemoQA em modo visual com ads), valida e força a navegação
        if (expectedRoute != null) {
            try {
                wait.until(ExpectedConditions.urlContains(expectedRoute));
            } catch (Exception timeout) {
                LOGGER.warn("Navegação para rota '{}' atrasada. Forçando acesso direto à URL da página...", expectedRoute);
                String baseUrl = ConfigManager.getBaseUrl();
                driver.get(baseUrl + expectedRoute);
                removeAdsAndFloatingBanners();
            }
        }
    }

    private void ensureGroupIsExpanded(String submenuName) {
        try {
            By parentGroupLocator = By.xpath(String.format(
                    "//div[contains(@class,'element-group')][.//span[@class='text' and normalize-space()='%s']]",
                    submenuName
            ));
            List<WebElement> groups = driver.findElements(parentGroupLocator);
            if (!groups.isEmpty()) {
                WebElement group = groups.get(0);
                List<WebElement> collapsedLists = group.findElements(By.xpath(".//div[contains(@class,'element-list') and contains(@class,'collapse') and not(contains(@class,'show'))]"));
                if (!collapsedLists.isEmpty()) {
                    LOGGER.info("Expandindo grupo lateral do submenu '{}'...", submenuName);
                    WebElement header = group.findElement(By.cssSelector(".header-wrapper, .group-header"));
                    scrollIntoView(header);
                    jsClick(header);
                }
            }
        } catch (Exception ignored) {
        }
    }
}
