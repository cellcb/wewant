package me.cell.wewant.core.stie.lacoste;

import me.cell.wewant.core.Boot;
import me.cell.wewant.core.Crawler;
import me.cell.wewant.core.Result;
import me.cell.wewant.core.utils.PriceUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.transaction.NotSupportedException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class LacosteUS  implements Crawler {
    public static void main(String[] args) {
        LacosteUS lacosteUS = new LacosteUS();
        Optional<Result> single = lacosteUS.single("https://www.lacoste.com/us/lacoste/women/clothing/polos/women-s-slim-fit-stretch-cotton-pique-polo/PF5462-51.html?color=ADY");
        System.out.println(single.get().getPrice());
    }
    @Override
    public Optional<Result> single(String url) {
        WebDriver driver = Boot.getWebDriver();
        try {
            driver.get(url);
            driver.manage().timeouts().implicitlyWait(Duration.of(2, ChronoUnit.SECONDS));
//            String l = driver.getPageSource();
//        System.out.println(l);
            WebElement element = driver.findElement(By.cssSelector("[class='nowrap fs--medium ff-semibold l-hmargin--small ']"));
            String priceText = element.getText();
//        System.out.println(priceText);
//            WebElement stockStatusElement = driver.findElement(By.xpath("//span[@data-selenium='stockStatus']"));
//            String stockStatus = stockStatusElement.getText();
//        System.out.println(stockStatus);
            driver.quit();
            BigDecimal price = PriceUtil.convert2Number(priceText);

            return Optional.of(Result.of("unknow", price));
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
