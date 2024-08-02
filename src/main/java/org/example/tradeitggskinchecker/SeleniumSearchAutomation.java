package org.example.tradeitggskinchecker;

import org.example.tradeitggskinchecker.enums.CSSLibrary;
import org.example.tradeitggskinchecker.enums.StatTrack;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.Duration;
import java.util.List;

@Component
public class SeleniumSearchAutomation {

    private final TelegramNotifier telegramNotifier;

    @Autowired
    public SeleniumSearchAutomation(TelegramNotifier telegramNotifier) {
        this.telegramNotifier = telegramNotifier;
    }

    public void run(Weapon weapon) {
        System.out.println("Starting Search for:" + weapon.gunType + " " + weapon.skinName);
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = getWebDriver();
        driver.manage().window().maximize();

        try {
            String tradeItURL = "https://tradeit.gg/ru/csgo/trade";
            driver.get(tradeItURL);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            List<WebElement> buttons = driver.findElements(By.cssSelector(CSSLibrary.MIDDLE_BUTTONS_WIDGETS_CSS));
            for (WebElement button : buttons) {
                if (button.getText().contains("StatTrak")) {
                    button.click();
                    List<WebElement> statTrackButtons = driver.findElements(By.cssSelector(CSSLibrary.MIDDLE_BUTTON_INPUT_CSS));
                    if (weapon.isStattrack == StatTrack.NO) {
                        statTrackButtons.getFirst().click();
                    } else {
                        statTrackButtons.get(1).click();
                    }

                }
                if (button.getText().contains("Флоат")) {
                    button.click();
                    WebElement leftFloatField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CSSLibrary.LEFT_FLOAT_FIELD_INPUT_XPATH)));
                    leftFloatField.click();
                    leftFloatField.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
                    leftFloatField.sendKeys(org.openqa.selenium.Keys.DELETE);
                    leftFloatField.sendKeys(weapon.floatBorders.leftFloatBorder);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WebElement rightFloatField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CSSLibrary.RIGHT_FLOAT_FIELD_INPUT_XPATH)));
                    rightFloatField.click();
                    rightFloatField.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
                    rightFloatField.sendKeys(org.openqa.selenium.Keys.DELETE);
                    rightFloatField.sendKeys(weapon.floatBorders.rightFloatBorder);
                    break;
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSSLibrary.BOT_INVENTORY_SEARCH_CSS)));
            searchField.click();

            searchField.sendKeys(weapon.gunType + " " + weapon.skinName);
            searchField.sendKeys(org.openqa.selenium.Keys.RETURN);


            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CSSLibrary.GUN_INFO_WIDGET_CSS)));


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            List<WebElement> widgets = driver.findElements(By.cssSelector(CSSLibrary.GUN_INFO_WIDGET_CSS));

            for (WebElement widget : widgets) {
                try {
                    WebElement openField = widget.findElement(By.cssSelector(CSSLibrary.OPEN_BUTTON_CSS));
                    openField.click();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    List<WebElement> foundedGuns = driver.findElements(By.cssSelector(CSSLibrary.GUN_INFO_WIDGET_CSS));
                    if (!foundedGuns.getFirst().getText().contains(weapon.skinName)) {
                        System.out.println("\nNothing found for:" + weapon.gunType + " " + weapon.skinName);
                        return;
                    }
                    for (WebElement foundedGun : foundedGuns) {
                        String gun;
                        if (foundedGun.equals(foundedGuns.getFirst())) {
                            gun = "Founded " + weapon.gunType + " " + foundedGun.getText();
                        } else {
                            gun = "Founded " + weapon.gunType + " " + weapon.skinName + "\n" + foundedGun.getText();
                        }
                        System.out.println(gun);
                        telegramNotifier.sendMessage(gun + "\n" + tradeItURL);
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;


                } catch (org.openqa.selenium.NoSuchElementException e) {
                    System.out.println("Element not found in widget.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static WebDriver getWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--proxy-server='direct://'");
        options.addArguments("--proxy-bypass-list=*");
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }
}
