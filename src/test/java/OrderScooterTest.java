import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageobjects.PageOrder;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderScooterTest {

    private WebDriver driver;
    private PageOrder pageOrder;

    // Параметры теста
    @Parameterized.Parameter(0)
    public String name;
    @Parameterized.Parameter(1)
    public String surname;
    @Parameterized.Parameter(2)
    public String address;
    @Parameterized.Parameter(3)
    public int metro;
    @Parameterized.Parameter(4)
    public String phoneNumber;
    @Parameterized.Parameter(5)
    public String color;
    @Parameterized.Parameter(6)
    public String date;
    @Parameterized.Parameter(7)
    public int rentalDays;
    @Parameterized.Parameter(8)
    public String comment;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        pageOrder = new PageOrder(driver);

        // Закрыть баннер с cookie, если он есть
        pageOrder.closeCookieBanner();
    }

    //Заказ по верхней кнопке:
    @Test
    public void testOrderScooterThroughHeaderButton() {
        pageOrder.clickOrderButtonHeader(); // Нажатие на верхнюю кнопку "Заказать"
        pageOrder.enterDataFirstPageOrder(name, surname, address, metro, phoneNumber);
        pageOrder.enterDataSecondPageOrder(color, date, rentalDays, comment);
        assertTrue(pageOrder.successfullyText());
    }

    //Заказ по нижней кнопке:
    @Test
    public void testOrderScooterThroughDownButton() {
        pageOrder.scrollPageOrder(); // Скролим  страницу
        pageOrder.clickOrderButtonDown(); // Нажать на нижнюю кнопку "Заказать"
        pageOrder.enterDataFirstPageOrder(name, surname, address, metro, phoneNumber);
        pageOrder.enterDataSecondPageOrder(color, date, rentalDays, comment);
        assertTrue(pageOrder.successfullyText());
    }


    @After
    public void teardown() {
        driver.quit();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"Максим", "Максимов", "Москва", 3, "+79264456848", "чёрный жемчуг", "02.02.2025", 2, "Везите быстро"},
                {"Денис", "Денисов", "Москва", 15, "+79651861551", "серая безысходность", "03.02.2025", 3, ""},
                {"Иван ", "Иванов", "Москва", 10, "+79684516987", "чёрный жемчуг", "04.02.2025", 4, "Не опаздывайте"}
        });
    }
}