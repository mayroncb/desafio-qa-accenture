package br.com.accenture.desafioqa.parte2_frontend.steps;

import br.com.accenture.desafioqa.parte2_frontend.pages.HomePage;
import br.com.accenture.desafioqa.parte2_frontend.pages.SideMenuComponent;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;

/**
 * Step Definitions compartilhados de navegação na página inicial e no menu lateral do DemoQA.
 */
public class CommonNavigationSteps {

    private final HomePage homePage = new HomePage();
    private final SideMenuComponent sideMenu = new SideMenuComponent();

    @Dado("que acesso o site DemoQA na página inicial")
    public void queAcessoOSiteDemoQANaPaginaInicial() {
        homePage.navigateToHomePage();
    }

    @Quando("escolho a opção {string} na página inicial")
    public void escolhoAOpcaoNaPaginaInicial(String categoryName) {
        homePage.selectCategoryCard(categoryName);
    }

    @Quando("clico no submenu {string}")
    public void clicoNoSubmenu(String submenuName) {
        sideMenu.clickSubmenuItem(submenuName);
    }
}

