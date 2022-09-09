package me.cell.wewant.core.tools;


import me.cell.wewant.core.Boot;
import okhttp3.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class YDY {
    public static void main(String[] args) throws Exception {
        checkin();
    }

    public static void checkin() {

//        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
        WebDriver driver = Boot.getWebDriver();
        try {
            //随机推迟
            Random rand = new Random();
            Thread.sleep(rand.nextInt(10) * 1000);

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

            String home = driver.getPageSource();
            WebElement check_in_times = driver.findElement(By
                    .id("check_in_times"));
            System.out.println(check_in_times.getText());
            ((JavascriptExecutor) driver).executeScript("checkIn();");

//            System.out.println("-----------------------");
//            System.out.println(home);

            Set<Cookie> cookies = driver.manage().getCookies();

            String cookieStr = cookies.stream().map(c -> c.getName() + ":" + c.getValue()).collect(Collectors.joining(";"));
            System.out.println(cookieStr);
            OkHttpClient client = new OkHttpClient().newBuilder().followRedirects(false).build();
            RequestBody checkinformBody = new FormBody.Builder().build();
            Request checkin = new Request.Builder().url("https://ydy1.com/core/check_in/").addHeader("cookie", cookieStr).post(checkinformBody).build();
            Response checkinresp = client.newCall(checkin).execute();
            System.out.println(checkinresp.body().string());
            System.out.println(checkinresp.code());
            checkinresp.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
//        driver.findElement(By.id("checkInBtn")).click();
//        Thread.sleep(1000);
//        driver.quit();
    }
}
