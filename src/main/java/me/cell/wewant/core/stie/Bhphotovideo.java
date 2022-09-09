package me.cell.wewant.core.stie;


import me.cell.wewant.core.Boot;
import me.cell.wewant.core.Crawler;
import me.cell.wewant.core.Result;
import me.cell.wewant.core.utils.PriceUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Bhphotovideo implements Crawler {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
//        ChromeOptions chromeOptions = new ChromeOptions().addArguments("--headless");
//        WebDriver driver = new ChromeDriver(chromeOptions);
        String useragent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.50 Safari/537.36";
        ChromeOptions chromeOptions = new ChromeOptions().addArguments("--window-size=1920,1080").addArguments("user-agent=" + useragent)
                .addArguments("--allow-running-insecure-content").addArguments("--ignore-certificate-errors").addArguments("--user-data-dir=selenium");
        WebDriver driver = new ChromeDriver(chromeOptions);
//        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.bhphotovideo.com/c/product/1433714-REG/canon_rf_35mm_f_1_8_is.html");
        driver.manage().timeouts().implicitlyWait(Duration.of(5, ChronoUnit.SECONDS));
        String l = driver.getPageSource();
//        System.out.println(l);
        WebElement element = driver.findElement(By.className("price_L0iytPTSvv"));
//        System.out.println(element.getText());
        WebElement stockStatus = driver.findElement(By.xpath("//span[@data-selenium='stockStatus']"));
//        System.out.println(stockStatus.getText());
    }

    @Override
    public Optional<Result> single(String url) {

        WebDriver driver = Boot.getWebDriver();
        try {
            driver.get(url);
            driver.manage().timeouts().implicitlyWait(Duration.of(2, ChronoUnit.SECONDS));
            String l = driver.getPageSource();
//        System.out.println(l);
            WebElement element = driver.findElement(By.className("price_L0iytPTSvv"));
            String priceText = element.getText();
//        System.out.println(priceText);
            WebElement stockStatusElement = driver.findElement(By.xpath("//span[@data-selenium='stockStatus']"));
            String stockStatus = stockStatusElement.getText();
//        System.out.println(stockStatus);
            driver.quit();
            BigDecimal price = PriceUtil.convert2Number(priceText);

            return Optional.of(Result.of(stockStatus, price));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Result> list(String url) {
        return Optional.empty();
    }
}
