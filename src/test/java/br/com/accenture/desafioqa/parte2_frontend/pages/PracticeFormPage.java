package br.com.accenture.desafioqa.parte2_frontend.pages;

import br.com.accenture.desafioqa.parte2_frontend.model.StudentFormData;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Page Object representativa da página Forms > Practice Form (Student Registration Form).
 */
public class PracticeFormPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(PracticeFormPage.class);

    private static final By FIRST_NAME_INPUT = By.id("firstName");
    private static final By LAST_NAME_INPUT = By.id("lastName");
    private static final By EMAIL_INPUT = By.id("userEmail");
    private static final By MOBILE_NUMBER_INPUT = By.id("userNumber");
    private static final By DATE_OF_BIRTH_INPUT = By.id("dateOfBirthInput");
    private static final By MONTH_SELECT = By.cssSelector(".react-datepicker__month-select");
    private static final By YEAR_SELECT = By.cssSelector(".react-datepicker__year-select");
    private static final By SUBJECTS_INPUT = By.id("subjectsInput");
    private static final By UPLOAD_PICTURE_INPUT = By.id("uploadPicture");
    private static final By CURRENT_ADDRESS_TEXTAREA = By.id("currentAddress");
    private static final By STATE_CONTAINER = By.id("state");
    private static final By STATE_INPUT = By.cssSelector("#state input");
    private static final By CITY_CONTAINER = By.id("city");
    private static final By CITY_INPUT = By.cssSelector("#city input");
    private static final By SUBMIT_BUTTON = By.id("submit");

    private static final By MODAL_CONTENT = By.cssSelector(".modal-content");
    private static final By MODAL_TITLE = By.id("example-modal-sizes-title-lg");
    private static final By MODAL_BODY_TABLE = By.cssSelector(".modal-body .table");
    private static final By CLOSE_MODAL_BUTTON = By.id("closeLargeModal");

    public void fillEntireForm(StudentFormData student, File uploadFile) {
        removeAdsAndFloatingBanners();
        LOGGER.info("Preenchendo Practice Form para estudante: {} ({})", student.getFullName(), student.getEmail());

        clearAndType(FIRST_NAME_INPUT, student.getFirstName());
        clearAndType(LAST_NAME_INPUT, student.getLastName());
        clearAndType(EMAIL_INPUT, student.getEmail());

        selectGender(student.getGender());
        clearAndType(MOBILE_NUMBER_INPUT, student.getMobileNumber());
        selectDateOfBirth(student.getBirthMonth(), student.getBirthYear(), student.getBirthDay());
        selectSubject(student.getSubject());
        selectHobby(student.getHobby());
        uploadFile(uploadFile);
        clearAndType(CURRENT_ADDRESS_TEXTAREA, student.getCurrentAddress());
        selectStateAndCity(student.getState(), student.getCity());
    }

    public void selectGender(String gender) {
        By genderRadio = By.xpath(String.format(
                "//input[@name='gender' and @value='%s'] | //label[normalize-space()='%s']",
                gender, gender
        ));
        WebElement element = waitForPresent(genderRadio);
        scrollIntoView(element);
        jsClick(element);
    }

    public void selectDateOfBirth(String month, String year, String day) {
        safeClick(DATE_OF_BIRTH_INPUT);
        Select monthDropdown = new Select(waitForVisible(MONTH_SELECT));
        monthDropdown.selectByVisibleText(month);

        Select yearDropdown = new Select(waitForVisible(YEAR_SELECT));
        yearDropdown.selectByVisibleText(year);

        By dayLocator = By.xpath(String.format(
                "//div[contains(@class,'react-datepicker__day') and not(contains(@class,'react-datepicker__day--outside-month')) and normalize-space()='%s']",
                Integer.parseInt(day)
        ));
        safeClick(dayLocator);
    }

    public void selectSubject(String subject) {
        WebElement subjectInput = waitForVisible(SUBJECTS_INPUT);
        scrollIntoView(subjectInput);
        subjectInput.sendKeys(subject);
        By autoCompleteOption = By.xpath(String.format(
                "//div[contains(@class,'subjects-auto-complete__option') and normalize-space()='%s']",
                subject
        ));
        try {
            safeClick(autoCompleteOption);
        } catch (Exception e) {
            subjectInput.sendKeys(Keys.ENTER);
        }
    }

    public void selectHobby(String hobby) {
        By hobbyCheckbox = By.xpath(String.format(
                "//div[@id='hobbiesWrapper']//label[normalize-space()='%s'] | //div[@id='hobbiesWrapper']//input[@type='checkbox'][following-sibling::label[normalize-space()='%s']]",
                hobby, hobby
        ));
        WebElement element = waitForPresent(hobbyCheckbox);
        scrollIntoView(element);
        jsClick(element);
    }

    public void uploadFile(File file) {
        LOGGER.info("Realizando upload do arquivo .txt: {}", file.getAbsolutePath());
        WebElement uploadInput = waitForPresent(UPLOAD_PICTURE_INPUT);
        scrollIntoView(uploadInput);
        uploadInput.sendKeys(file.getAbsolutePath());
    }

    public void selectStateAndCity(String state, String city) {
        removeAdsAndFloatingBanners();
        WebElement stateContainer = waitForVisible(STATE_CONTAINER);
        scrollIntoView(stateContainer);
        WebElement stateInput = waitForPresent(STATE_INPUT);
        stateInput.sendKeys(state);
        stateInput.sendKeys(Keys.ENTER);

        WebElement cityContainer = waitForVisible(CITY_CONTAINER);
        scrollIntoView(cityContainer);
        WebElement cityInput = waitForPresent(CITY_INPUT);
        cityInput.sendKeys(city);
        cityInput.sendKeys(Keys.ENTER);
    }

    public void submitForm() {
        LOGGER.info("Submetendo o formulário Practice Form...");
        removeAdsAndFloatingBanners();
        WebElement submitBtn = waitForPresent(SUBMIT_BUTTON);
        scrollIntoView(submitBtn);
        jsClick(submitBtn);
    }

    public boolean isConfirmationModalDisplayed() {
        return waitForVisible(MODAL_CONTENT).isDisplayed() && waitForVisible(MODAL_TITLE).isDisplayed();
    }

    public String getConfirmationModalTitle() {
        return waitForVisible(MODAL_TITLE).getText().trim();
    }

    public String getModalTableText() {
        return waitForVisible(MODAL_BODY_TABLE).getText();
    }

    public void closeConfirmationModal() {
        LOGGER.info("Fechando o popup de confirmação do formulário...");
        removeAdsAndFloatingBanners();
        WebElement closeBtn = waitForPresent(CLOSE_MODAL_BUTTON);
        scrollIntoView(closeBtn);
        jsClick(closeBtn);

        // No bundle React 18 atual do DemoQA, o handler do botão #closeLargeModal invoca findDOMNode(form).reset()
        // antes de setShow(false); caso o modal permaneça visível, acionamos o onHide nativo do Modal (ESC / backdrop).
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(2))
                    .until(ExpectedConditions.invisibilityOfElementLocated(MODAL_TITLE));
        } catch (Exception ignored) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            if (!driver.findElements( By.cssSelector(".modal.show")).isEmpty()) {
                jsClick(driver.findElement(By.cssSelector(".modal.show")));
            }
            wait.until(ExpectedConditions.invisibilityOfElementLocated(MODAL_TITLE));
        }
    }
}

