package me.cell.wewant.tools;


import lombok.extern.slf4j.Slf4j;
import me.cell.wewant.core.Boot;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class YDY {
    public static void main(String[] args) throws Exception {
        checkin(0);
    }

    public static void checkinWithDelay() {
        Random rand = new Random();
        int millis = rand.nextInt(10) * 1000;
        log.info("推迟{}毫秒", millis);
        checkin(millis);
    }

    private static Optional<Integer> checkinTime(String html) {
        Document doc = Jsoup.parse(html);
        Element check_in_times = doc.getElementById("check_in_times");
        if (null == check_in_times) {
            return Optional.empty();
        } else {
            return Optional.of(Integer.valueOf(check_in_times.text()));
        }
    }

    public static void checkin(int delay) {

//        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
        WebDriver driver = Boot.getWebDriver();
        try {
            Thread.sleep(delay);
            driver.get("https://ydy1.com/");
            driver.manage().timeouts().implicitlyWait(Duration.of(2, ChronoUnit.SECONDS));
//        String l = driver.getPageSource();
//        System.out.println(l);
            driver.findElement(By.name("username")).sendKeys("chengbo");
            driver.findElement(By.name("password")).sendKeys("chengboc");
            WebElement loginButton = driver.findElement(By
                    .id("vipLoginBtn"));
            // 点击登录
            loginButton.click();
            Thread.sleep(10000);
            driver.navigate().refresh();
            Thread.sleep(10000);

            Optional<Integer> checkinTime = checkinTime(driver.getPageSource());

            for (int i = 0; i < 2 && !checkinTime.isPresent(); i++) {
                log.info("retry get checkin times (before) :" + i);
                driver.navigate().refresh();
                Thread.sleep(10000);
                checkinTime = checkinTime(driver.getPageSource());
            }

            if (!checkinTime.isPresent()) {
                log.info("get checkin times failed!");
                throw new RuntimeException("get checkin times failed!");
            }

            log.info("before checkin times:{}", checkinTime.get());

            Integer expectValue = checkinTime.get() + 1;
            Optional<Integer> afterCheckIn = doCheckIn(driver);

            for (int i = 0; i < 2 && afterCheckIn.get() != expectValue; i++) {
                log.info("retry  checkin  :" + i);
                driver.navigate().refresh();
                Thread.sleep(10000);
                afterCheckIn = doCheckIn(driver);
            }
            log.info("after checkin times:{}", afterCheckIn.get());


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
//        driver.findElement(By.id("checkInBtn")).click();
//        Thread.sleep(1000);
//        driver.quit();
    }

    private static Optional<Integer> doCheckIn(WebDriver driver) throws IOException {
        ((JavascriptExecutor) driver).executeScript("checkIn();");
        Set<Cookie> cookies = driver.manage().getCookies();
        String cookieStr = cookies.stream().map(c -> c.getName() + ":" + c.getValue()).collect(Collectors.joining(";"));
        System.out.println(cookieStr);
        OkHttpClient client = new OkHttpClient().newBuilder().followRedirects(false).build();
        RequestBody checkinformBody = new FormBody.Builder().build();
        Request checkin = new Request.Builder().url("https://ydy1.com/core/check_in/").addHeader("cookie", cookieStr).post(checkinformBody).build();
        Response checkinresp = client.newCall(checkin).execute();
        checkinresp.close();
        return checkinTime(driver.getPageSource());
    }
}
