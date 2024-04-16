package com.api.soundsurf.music.domain;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CrawlerService {
    public String getMusicGenres(String title, String artist) {
        System.setProperty("webdriver.chrome.driver", "/chromedriver-mac-arm64/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String url = "https://rateyourmusic.com/release/single/" + artist + "/" + title + "/";
        driver.get(url);

        WebElement genreElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.release_pri_genres")));
        String genres = genreElement.getText();

        driver.quit();

        return genres;
    }
}
