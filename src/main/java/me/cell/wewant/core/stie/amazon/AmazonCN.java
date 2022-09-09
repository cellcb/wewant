package me.cell.wewant.core.stie.amazon;


import me.cell.wewant.core.Boot;
import me.cell.wewant.core.Crawler;
import me.cell.wewant.core.Result;
import me.cell.wewant.core.utils.PriceUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AmazonCN implements Crawler {

    @Override
    public Optional<Result> list(String url) {
        WebDriver driver = Boot.getWebDriver();
        try {
            driver.get(url);
            driver.manage().timeouts().implicitlyWait(Duration.of(2, ChronoUnit.SECONDS));
//        String pageSource = driver.getPageSource();
            List<WebElement> elements = driver.findElements(By.className("a-price"));
//        for (WebElement element : elements) {
//            System.out.println(element.getText());
//        }
            Optional<BigDecimal> min = elements.stream().map(e -> e.getText()).map(PriceUtil::convert2Number).min(Comparator.naturalOrder());
            return Optional.of(Result.of(null, min.get()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return Optional.empty();

    }

    @Override
    public Optional<Result> single(String url) {
        WebDriver driver = Boot.getWebDriver();
        try {
            driver.get(url);
            driver.manage().timeouts().implicitlyWait(Duration.of(2, ChronoUnit.SECONDS));
            String pageSource = driver.getPageSource();
//        System.out.println(pageSource);
            List<WebElement> elements = driver.findElements(By.className("a-price"));
//        for (WebElement element : elements) {
//            System.out.println(element.getText());
//        }
            Optional<BigDecimal> min = elements.stream().filter(e -> e.getText().trim().length() > 0).map(e -> e.getText()).map(PriceUtil::convert2Number).min(Comparator.naturalOrder());
            return Optional.of(Result.of(null, min.get()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return Optional.empty();

    }


}