package br.com.accenture.desafioqa.parte2_frontend.steps;

import br.com.accenture.desafioqa.parte2_frontend.pages.SortablePage;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Step Definitions do fluxo Interactions > Sortable (Parte 2).
 */
public class SortableSteps {

    private final SortablePage sortablePage = new SortablePage();

    @Quando("utilizo métodos de drag and drop para colocar os elementos na ordem crescente")
    public void utilizoMetodosDeDragAndDropParaColocarOsElementosNaOrdemCrescente() {
        sortablePage.sortElementsInAscendingOrderUsingDragAndDrop();
    }

    @Então("os elementos da lista devem estar ordenados de forma crescente de {string} a {string}")
    public void osElementosDaListaDevemEstarOrdenadosDeFormaCrescenteDeA(String firstItem, String lastItem) {
        List<String> currentOrder = sortablePage.getCurrentListOrder();
        assertEquals(SortablePage.ASCENDING_ORDER, currentOrder,
                "Os elementos da lista Sortable devem estar na ordem crescente esperada: " + SortablePage.ASCENDING_ORDER);
        assertEquals(firstItem, currentOrder.get(0));
        assertEquals(lastItem, currentOrder.get(currentOrder.size() - 1));
    }
}

