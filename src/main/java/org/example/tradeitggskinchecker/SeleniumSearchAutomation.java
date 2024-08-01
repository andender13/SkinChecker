package org.example.tradeitggskinchecker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;



public class SeleniumSearchAutomation {

    public static void run() {

        // Укажите путь к ChromeDriver
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");

        // Настройка опций для ChromeDriver
        ChromeOptions options = new ChromeOptions();
         options.addArguments("--headless"); // Закомментируйте для проверки в графическом режиме
        // Инициализация WebDriver
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        try {
            // Открыть страницу
            driver.get("https://tradeit.gg/ru/csgo/trade");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            List<WebElement> buttons = driver.findElements(By.cssSelector(".v-expansion-panel-title.font-size-16.text-capitalize.font-weight-medium.align-center"));
            for (WebElement button : buttons) {
                if (button.getText().contains("StatTrak")) {
                    button.click();
                    List<WebElement> statTrackButtons = driver.findElements(By.cssSelector(".v-label.v-label--clickable"));
                    String STAT_TRACK = "No";
                    if (STAT_TRACK.equals("No")) {
                        statTrackButtons.getFirst().click();
                    } else {
                        statTrackButtons.get(1).click();
                    }

                }
                if (button.getText().contains("Флоат")) {
                    button.click();
                    WebElement leftFloatField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='From' and @type='number']")));
                    leftFloatField.click();
                    leftFloatField.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
                    leftFloatField.sendKeys(org.openqa.selenium.Keys.DELETE);
                    leftFloatField.sendKeys("0.151");
                    try {
                        Thread.sleep(500); // Небольшая пауза для полного обновления результатов
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WebElement rightFloatField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='To' and @type='number']")));
                    rightFloatField.click();
                    rightFloatField.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
                    rightFloatField.sendKeys(org.openqa.selenium.Keys.DELETE);
                    rightFloatField.sendKeys("0.2");
                    break;
                }
            }
            try {
                Thread.sleep(2000); // Небольшая пауза для полного обновления результатов
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input#siteInventory-search")));
            searchField.click();

            // Ввести название скина и нажать Enter
            searchField.sendKeys("Temukau");
            searchField.sendKeys(org.openqa.selenium.Keys.RETURN); // Нажать Enter для выполнения поиска

            // Печать для отладки
            System.out.println("Search completed, waiting for results...");

            // Ожидание появления результатов поиска
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".item-cell.item.item-md.rounded.gray-700")));


            try {
                Thread.sleep(2000); // Небольшая пауза для полного обновления результатов
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            List<WebElement> widgets = driver.findElements(By.cssSelector(".item-cell.item.item-md.rounded.gray-700"));

            // Перебрать виджеты и найти нужный
            for (WebElement widget : widgets) {
                try {
                    WebElement openField = widget.findElement(By.cssSelector(".v-btn__content"));
                    openField.click();
                    try {
                        Thread.sleep(2000); // Небольшая пауза для полного обновления результатов
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    List<WebElement> foundedGuns = driver.findElements(By.cssSelector(".item-cell.item.item-md.rounded.gray-700"));
                    if (!foundedGuns.getFirst().getText().contains("Temukau")) {
                        System.out.println("Nothing found");
                        return;
                    }
                    for (WebElement foundedGun : foundedGuns) {
                        System.out.println("Founded gun:\n");
                        System.out.println(foundedGun.getText());
                    }
                    try {
                        Thread.sleep(2000); // Небольшая пауза для полного обновления результатов
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;


                } catch (org.openqa.selenium.NoSuchElementException e) {
                    // Если элемент не найден, игнорируем и продолжаем
                    System.out.println("Element not found in widget.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Закрыть браузер
            driver.quit();
        }
    }
}
