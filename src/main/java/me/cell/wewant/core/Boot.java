package me.cell.wewant.core;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Boot {
    
    @NotNull
    public static WebDriver getWebDriver() {
        String chromeDriverPath = System.getProperty("WEBDRIVER_CHROME_DRIVER_PATH", "/opt/homebrew/bin/chromedriver");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        String useragent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.50 Safari/537.36";
        ChromeOptions chromeOptions = new ChromeOptions()
                .addArguments("--headless")
                .addArguments("--window-size=1920,1080").addArguments("user-agent=" + useragent)
                .addArguments("--allow-running-insecure-content").addArguments("--ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(chromeOptions);
//        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }
}
