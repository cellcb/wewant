package me.cell.wewant.tools;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class GG {
    public static void main(String[] args)  {
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
//        ChromeOptions chromeOptions = new ChromeOptions().addArguments("--headless");
        String useragent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.50 Safari/537.36";
        ChromeOptions chromeOptions = new ChromeOptions().addArguments("--window-size=1920,1080").addArguments("user-agent=" + useragent)
                .addArguments("--allow-running-insecure-content").addArguments("--ignore-certificate-errors").addArguments("--user-data-dir=selenium");

        Proxy proxy = new Proxy();
        proxy.setAutodetect(false);
//        proxy.setSocksProxy("192.168.5.249:3213");
        proxy.setHttpProxy("192.168.5.249:3213");
//        proxy.setProxyType(Proxy.ProxyType.DIRECT);
        chromeOptions.addArguments("start-maximized");

        chromeOptions.setCapability("proxy", proxy);

        WebDriver driver = new ChromeDriver(chromeOptions);
        try {

            driver.manage().window().maximize();
            driver.get("https://www.google.com");
//        driver.get("https://www.amazon.cn/ref=z_cn?tag=zcn0e-23");
            driver.manage().timeouts().implicitlyWait(Duration.of(5, ChronoUnit.SECONDS));
            String l = driver.getPageSource();
            Thread.sleep(2000);
            driver.navigate().refresh();
//        System.out.println(stockStatus.getText());
        }catch (Exception e){
            System.out.println(e);
        }finally {
            driver.quit();
        }
    }

    public static void checkin() throws InterruptedException, IOException {
//        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");

//        driver.findElement(By.id("checkInBtn")).click();
//        Thread.sleep(1000);
//        driver.quit();
    }
}
