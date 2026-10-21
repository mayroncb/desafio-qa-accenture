package br.com.accenture.desafioqa.parte2_frontend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Componente Page Object representativo do menu lateral (Left Panel) do DemoQA.
 */
public class SideMenuComponent extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SideMenuComponent.class);

    public void clickSubmenuItem(String submenuName) {
        LOGGER.info("Clicando no submenu lateral: '{}'", submenuName);
        By submenuLinkLocator = By.xpath(String.format(
                "//div[contains(@class,'left-pannel')]//a[contains(@class,'router-link')][.//span[@class='text' and normalize-space()='%s']] | " +
                "//div[contains(@class,'left-pannel')]//li[contains(@class,'btn-light')]//span[@class='text' and normalize-space()='%s']",
                submenuName, submenuName
        ));
        WebElement element = waitForVisible(submenuLinkLocator);
        scrollIntoView(element);
        safeClick(element);
    }
}

