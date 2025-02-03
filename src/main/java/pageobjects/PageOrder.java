/*Заказ самоката. Нужно проверить весь флоу позитивного сценария с двумя наборами данных. Проверить точки входа в сценарий, их две: кнопка «Заказать» вверху страницы и внизу.
Из чего состоит позитивный сценарий:
Нажать кнопку «Заказать». На странице две кнопки заказа.
Заполнить форму заказа.
Проверить, что появилось всплывающее окно с сообщением об успешном создании заказа..*/

package pageobjects;
import org.openqa.selenium.*;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class PageOrder {
    private final WebDriver driver;

    // Локаторы для баннера куки
    private static final By CookieBannerButton = By.className("App_CookieButton__3cvqF");

    // Локаторы для первой страницы
    private static final By OrderButtonHeader = By.xpath("//*[@id='root']/div/div[1]/div[1]/div[2]/button[1]");
    private static final By OrderButtonDown = By.xpath("//*[@id='root']/div/div/div[4]/div[2]/div[5]/button");
    private static final By NameButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[1]/input");
    private static final By SurnameButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[2]/input");
    private static final By AddressButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[3]/input");
    private static final By MetroButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[4]/div/div/input");
    private static final By TelButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[5]/input");
    private static final By NextButton = By.xpath("//*[@id='root']/div/div[2]/div[3]/button");

    // Локаторы для второй страницы
    private static final By DateButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[1]/div/div/input");
    private static final By PeriodButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[2]/div/div");
    private static final By BlackButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[3]/label[1]/input");
    private static final By GreyButton = By.xpath("//*[@id='grey']");
    private static final By CommentButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[4]/input");
    private static final By SubmitButton = By.xpath("//*[@id='root']/div/div[2]/div[3]/button[2]");
    private static final By YesButton = By.xpath("//*[@id='root']/div/div[2]/div[5]/div[2]/button[2]");
    private static final By PopapButton = By.xpath("//*[@id='root']/div/div[2]/div[5]/div[1]");

    public PageOrder(WebDriver driver) {
        this.driver = driver;
    }

    // Метод для закрытия баннера куки
    public void closeCookieBanner() {
        driver.findElement(CookieBannerButton).click();
    }

    //Скролл до элемента кнопки "Заказать"
    public void scrollPageOrder() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
                driver.findElement(OrderButtonDown));
    }

    // Нажать на нижнюю кнопку  "Заказать"
    public void clickOrderButtonDown() {
        driver.findElement(OrderButtonDown).click();
    }

    // Нажать на верхнюю кнопку "Заказать"
    public void clickOrderButtonHeader() {
        driver.findElement(OrderButtonHeader).click();
    }

    // Заполнить первую страницу заказа
    public void enterDataFirstPageOrder(String name, String surname, String address, int metro, String phoneNumber) {
        driver.findElement(NameButton).sendKeys(name);  // Заполнить "Имя"
        driver.findElement(SurnameButton).sendKeys(surname);  // Заполнить "Фамилия"
        driver.findElement(AddressButton).sendKeys(address);  // Заполнить "Адрес"
        driver.findElement(MetroButton).click();  // Нажать на "Станция метро"

        // Выбор станции метро из списка
        By allMetroStation = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[4]/div/div[2]/ul/li");
        List<WebElement> elements = driver.findElements(allMetroStation);
        elements.get(metro - 1).click();  // Выбор станции метро по индексу

        driver.findElement(TelButton).sendKeys(phoneNumber);  // Заполнить "Телефон"
        driver.findElement(NextButton).click();  // Нажать на кнопку "Далее"
    }

    // Заполнить вторую страницу заказа
    public void enterDataSecondPageOrder(String color, String date, int rentalDays, String comment) {
        // Выбор цвета самоката
        if ("чёрный жемчуг".equals(color)) {
            driver.findElement(BlackButton).click();
        } else if ("серая безысходность".equals(color)) {
            driver.findElement(GreyButton).click();
        }

        // Выбор даты
        driver.findElement(DateButton).sendKeys(date);
        driver.findElement(DateButton).sendKeys(Keys.RETURN);

        // Выбор срока аренды
        driver.findElement(PeriodButton).click();
        By allDayForOrder = By.className("Dropdown-option");
        List<WebElement> elements = driver.findElements(allDayForOrder);
        elements.get(rentalDays - 1).click();  // Выбор срока аренды

        driver.findElement(CommentButton).sendKeys(comment);  // Заполнить "Комментарий"
        driver.findElement(SubmitButton).click();  // Нажать на кнопку "Заказать"
        driver.findElement(YesButton).click();  // Подтверждение заказа в попапе
    }

    // Попап подтверждения оформления заказа
    public boolean successfullyText() {
        String successfullOrder = driver.findElement(PopapButton).getText();
        return successfullOrder.contains("Заказ оформлен");
    }
}