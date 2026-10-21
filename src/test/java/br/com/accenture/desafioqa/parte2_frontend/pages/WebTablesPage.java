package br.com.accenture.desafioqa.parte2_frontend.pages;

import br.com.accenture.desafioqa.parte2_frontend.model.WebTableRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Page Object representativa da página Elements > Web Tables.
 * Compatível tanto com a tabela React-Bootstrap/TanStack Table atual do DemoQA quanto com ReactTable.
 */
public class WebTablesPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebTablesPage.class);

    private static final By ADD_NEW_RECORD_BUTTON = By.id("addNewRecordButton");
    private static final By REGISTRATION_MODAL = By.id("registration-form-modal");
    private static final By FIRST_NAME_INPUT = By.id("firstName");
    private static final By LAST_NAME_INPUT = By.id("lastName");
    private static final By EMAIL_INPUT = By.id("userEmail");
    private static final By AGE_INPUT = By.id("age");
    private static final By SALARY_INPUT = By.id("salary");
    private static final By DEPARTMENT_INPUT = By.id("department");
    private static final By SUBMIT_BUTTON = By.id("submit");
    private static final By SEARCH_BOX_INPUT = By.id("searchBox");
    private static final By PAGE_SIZE_SELECT = By.cssSelector(".web-tables-wrapper select.form-control, select[aria-label='rows per page']");
    private static final By TABLE_WRAPPER = By.cssSelector(".web-tables-wrapper");

    public void setPageSizeToMax() {
        try {
            List<WebElement> selects = driver.findElements(PAGE_SIZE_SELECT);
            if (!selects.isEmpty()) {
                WebElement selectEl = selects.get(0);
                scrollIntoView(selectEl);
                Select select = new Select(selectEl);
                select.selectByValue("50");
            }
        } catch (Exception e) {
            LOGGER.debug("Seleção de paginação padrão mantida: {}", e.getMessage());
        }
    }

    public void createNewRecord(WebTableRecord record) {
        LOGGER.info("Criando registro no Web Tables: {}", record);
        safeClick(ADD_NEW_RECORD_BUTTON);
        waitForVisible(REGISTRATION_MODAL);

        fillRegistrationForm(record);
        safeClick(SUBMIT_BUTTON);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(REGISTRATION_MODAL));
    }

    public void editRecordByEmail(String currentEmail, WebTableRecord updatedRecord) {
        LOGGER.info("Editando registro de email '{}' para novos dados: {}", currentEmail, updatedRecord);
        setPageSizeToMax();
        By editButtonLocator = By.xpath(String.format(
                "(//tr[td[normalize-space()='%s']] | //div[@role='row'][div[normalize-space()='%s']])//*[starts-with(@id,'edit-record-')]",
                currentEmail, currentEmail
        ));
        safeClick(editButtonLocator);
        waitForVisible(REGISTRATION_MODAL);

        fillRegistrationForm(updatedRecord);
        safeClick(SUBMIT_BUTTON);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(REGISTRATION_MODAL));
    }

    public void deleteRecordByEmail(String email) {
        LOGGER.info("Deletando registro com email '{}'...", email);
        setPageSizeToMax();
        By deleteButtonLocator = By.xpath(String.format(
                "(//tr[td[normalize-space()='%s']] | //div[@role='row'][div[normalize-space()='%s']])//*[starts-with(@id,'delete-record-')]",
                email, email
        ));
        safeClick(deleteButtonLocator);
    }

    public boolean isRecordPresent(WebTableRecord record) {
        setPageSizeToMax();
        String tableText = waitForVisible(TABLE_WRAPPER).getText();
        return tableText.contains(record.getFirstName())
                && tableText.contains(record.getLastName())
                && tableText.contains(record.getEmail())
                && tableText.contains(record.getDepartment());
    }

    public boolean isEmailPresent(String email) {
        setPageSizeToMax();
        By rowByEmail = By.xpath(String.format(
                "//tr[td[normalize-space()='%s']] | //div[@role='row'][div[normalize-space()='%s']]",
                email, email
        ));
        return !driver.findElements(rowByEmail).isEmpty();
    }

    private void fillRegistrationForm(WebTableRecord record) {
        clearAndType(FIRST_NAME_INPUT, record.getFirstName());
        clearAndType(LAST_NAME_INPUT, record.getLastName());
        clearAndType(EMAIL_INPUT, record.getEmail());
        clearAndType(AGE_INPUT, record.getAge());
        clearAndType(SALARY_INPUT, record.getSalary());
        clearAndType(DEPARTMENT_INPUT, record.getDepartment());
    }
}

