package com.service.openapi.trade.naverapi.service;

import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
@Slf4j
public class SeleniumTest {
    private static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private static final String WEB_DRIVER_PATH = "/Users/sangik.lee/personal-project/web_driver/chromedriver";

    @Test
    public void testWebCrawl() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        final String baseUrl = "https://itemscout.io/category";

        final WebDriver webDriver = new ChromeDriver();

        this.crawlUrl(webDriver, baseUrl);

        webDriver.close();
    }

    private void crawlUrl(final WebDriver webDriver, final String baseUrl) {
        webDriver.get(baseUrl);

        WebDriverWait wait = new WebDriverWait(webDriver, 10L);
        wait.until(f -> Objects.nonNull(webDriver.findElement(By.className("d-inline-block"))));

        final List<WebElement> firstElements = webDriver.findElements(By.className("d-inline-block"));
        firstElements.forEach(e -> log.info(e.getText()));

        firstElements.get(0).click();

        wait.until(f -> Objects.nonNull(webDriver.findElement(By.className("v-menu__content"))));

        final List<WebElement> listItemElements = webDriver.findElements(By.className("v-list-item__title"));
        final List<WebElement> listItemElementss = webDriver.findElements(By.xpath("<div data-v-14a0a0ed=\"\""));

        listItemElements.forEach(e -> log.info(e.getText()));
        listItemElementss.forEach(e -> log.info(e.getText()));
    }
}
