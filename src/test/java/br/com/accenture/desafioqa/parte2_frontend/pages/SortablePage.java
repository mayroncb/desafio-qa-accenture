package br.com.accenture.desafioqa.parte2_frontend.pages;

import br.com.accenture.desafioqa.config.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object representativa da página Interactions > Sortable.
 * Utiliza métodos de Drag and Drop (Actions API + despacho de eventos DragEvent HTML5 para compatibilidade com react-dnd)
 * para posicionar os elementos na ordem crescente ("One", "Two", "Three", "Four", "Five", "Six").
 */
public class SortablePage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SortablePage.class);

    public static final List<String> ASCENDING_ORDER = Arrays.asList("One", "Two", "Three", "Four", "Five", "Six");
    public static final List<String> DISORDERED_INITIAL = Arrays.asList("Six", "Two", "One", "Four", "Five", "Three");

    private static final By LIST_ITEMS = By.cssSelector(
            "#demo-tabpane-list .list-group-item, #sortableContainer .vertical-list-container .list-group-item, #sortableContainer .list-group-item, .vertical-list-container .list-group-item, div[id*='tabpane-list'] .list-group-item"
    );
    private static final By LIST_TAB = By.id("demo-tab-list");

    public void ensureSortablePageLoaded() {
        removeAdsAndFloatingBanners();
        if (!driver.getCurrentUrl().contains("/sortable")) {
            LOGGER.info("Garantindo navegação para a página /sortable...");
            driver.get(ConfigManager.getBaseUrl() + "/sortable");
            removeAdsAndFloatingBanners();
        }

        try {
            List<WebElement> tabs = driver.findElements(LIST_TAB);
            if (!tabs.isEmpty() && tabs.get(0).isDisplayed()) {
                WebElement tab = tabs.get(0);
                String ariaSelected = tab.getAttribute("aria-selected");
                if ("false".equalsIgnoreCase(ariaSelected)) {
                    safeClick(tab);
                }
            }
        } catch (Exception ignored) {
        }
    }

    public List<String> getCurrentListOrder() {
        ensureSortablePageLoaded();
        waitForVisible(LIST_ITEMS);
        return driver.findElements(LIST_ITEMS).stream()
                .filter(WebElement::isDisplayed)
                .map(el -> el.getText().trim())
                .collect(Collectors.toList());
    }

    /**
     * Caso a lista no DemoQA inicie já em ordem ("One" a "Six"), primeiro embaralha os itens via Drag and Drop
     * para o estado desordenado ilustrado no slide 8 do desafio ("Six", "Two", "One", "Four", "Five", "Three")
     * e, na sequência, executa a ordenação crescente completa ("One" a "Six") via Drag and Drop.
     */
    public void sortElementsInAscendingOrderUsingDragAndDrop() {
        ensureSortablePageLoaded();
        List<String> initialOrder = getCurrentListOrder();
        LOGGER.info("Ordem inicial encontrada na página Sortable: {}", initialOrder);

        if (initialOrder.equals(ASCENDING_ORDER)) {
            LOGGER.info("Desordenando os elementos primeiro via Drag and Drop para demonstrar a reordenação visual...");
            applyTargetOrderWithDragAndDrop(DISORDERED_INITIAL);
            LOGGER.info("Ordem após embaralhamento via Drag and Drop: {}", getCurrentListOrder());
        }

        LOGGER.info("Ordenando os elementos em ordem crescente {} utilizando Drag and Drop...", ASCENDING_ORDER);
        applyTargetOrderWithDragAndDrop(ASCENDING_ORDER);

        List<String> finalOrder = getCurrentListOrder();
        LOGGER.info("Ordem final obtida na página Sortable: {}", finalOrder);
    }

    private void applyTargetOrderWithDragAndDrop(List<String> targetOrder) {
        for (int targetIndex = 0; targetIndex < targetOrder.size(); targetIndex++) {
            String desiredLabel = targetOrder.get(targetIndex);
            List<String> currentOrder = getCurrentListOrder();
            int currentIndex = currentOrder.indexOf(desiredLabel);

            if (currentIndex != -1 && currentIndex != targetIndex) {
                List<WebElement> elements = driver.findElements(LIST_ITEMS).stream()
                        .filter(WebElement::isDisplayed)
                        .collect(Collectors.toList());

                WebElement sourceElement = elements.get(currentIndex);
                WebElement targetElement = elements.get(targetIndex);

                LOGGER.info("   -> Drag & Drop do item '{}' (posição {} -> posição {})",
                        desiredLabel, currentIndex, targetIndex);
                performDragAndDrop(sourceElement, targetElement, desiredLabel, currentIndex, targetIndex);
            }
        }
    }

    private void performDragAndDrop(WebElement source, WebElement target, String desiredLabel, int sourceIndex, int targetIndex) {
        scrollIntoView(target);

        // 1. Executa o gesto de Drag and Drop via Selenium Actions API
        try {
            int yOffset = (sourceIndex > targetIndex) ? -15 : 15;
            Actions actions = new Actions(driver);
            actions.clickAndHold(source)
                    .pause(Duration.ofMillis(60))
                    .moveToElement(target, 0, yOffset)
                    .pause(Duration.ofMillis(60))
                    .release()
                    .perform();
        } catch (Exception e) {
            LOGGER.debug("Actions dragAndDrop concluído com fallback HTML5: {}", e.getMessage());
        }

        // 2. Caso o container utilize react-dnd HTML5Backend (DemoQA atual), dispara a sequência DragEvent HTML5
        // cruzando o ponto médio vertical (rect.bottom - rect.top)/2 exigido pelo hover handler do react-dnd
        List<String> afterActionsOrder = getCurrentListOrder();
        if (afterActionsOrder.indexOf(desiredLabel) != targetIndex) {
            List<WebElement> freshElements = driver.findElements(LIST_ITEMS).stream()
                    .filter(WebElement::isDisplayed)
                    .collect(Collectors.toList());
            int freshSourceIndex = afterActionsOrder.indexOf(desiredLabel);
            WebElement freshSource = freshElements.get(freshSourceIndex);
            WebElement freshTarget = freshElements.get(targetIndex);

            String html5DndScript =
                    "const src = arguments[0];" +
                    "const tgt = arguments[1];" +
                    "const moveUp = arguments[2];" +
                    "const dataTransfer = new DataTransfer();" +
                    "const srcRect = src.getBoundingClientRect();" +
                    "const tgtRect = tgt.getBoundingClientRect();" +
                    "const startX = srcRect.left + srcRect.width / 2;" +
                    "const startY = srcRect.top + srcRect.height / 2;" +
                    "const dropX = tgtRect.left + tgtRect.width / 2;" +
                    "const dropY = moveUp ? (tgtRect.top + 4) : (tgtRect.bottom - 4);" +
                    "src.dispatchEvent(new DragEvent('dragstart', { bubbles: true, cancelable: true, dataTransfer, clientX: startX, clientY: startY }));" +
                    "tgt.dispatchEvent(new DragEvent('dragenter', { bubbles: true, cancelable: true, dataTransfer, clientX: dropX, clientY: dropY }));" +
                    "tgt.dispatchEvent(new DragEvent('dragover', { bubbles: true, cancelable: true, dataTransfer, clientX: dropX, clientY: dropY }));" +
                    "tgt.dispatchEvent(new DragEvent('drop', { bubbles: true, cancelable: true, dataTransfer, clientX: dropX, clientY: dropY }));" +
                    "src.dispatchEvent(new DragEvent('dragend', { bubbles: true, cancelable: true, dataTransfer, clientX: dropX, clientY: dropY }));";

            boolean moveUp = freshSourceIndex > targetIndex;
            ((JavascriptExecutor) driver).executeScript(html5DndScript, freshSource, freshTarget, moveUp);
        }
    }
}
