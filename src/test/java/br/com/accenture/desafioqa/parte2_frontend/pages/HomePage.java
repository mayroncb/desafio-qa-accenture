package br.com.accenture.desafioqa.parte2_frontend.pages;

import br.com.accenture.desafioqa.config.ConfigManager;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object representativa da Página Inicial do DemoQA (https://demoqa.com/).
 */
public class HomePage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);

    private static final By HOME_CARDS_CONTAINER = By.cssSelector(".category-cards");

    public void navigateToHomePage() {
        String url = ConfigManager.getBaseUrl();
        LOGGER.info("Navegando para a página inicial: {}", url);
        driver.get(url);
        waitForVisible(HOME_CARDS_CONTAINER);
        removeAdsAndFloatingBanners();
    }

    public void selectCategoryCard(String categoryName) {
        LOGGER.info("Selecionando card de categoria na página inicial: '{}'", categoryName);
        By cardLocator = By.xpath(String.format(
                "//div[contains(@class,'category-cards')]//*[contains(@class,'top-card') or contains(@class,'card')][.//h5[normalize-space()='%s']]",
                categoryName
        ));
        safeClick(cardLocator);
    }
}

