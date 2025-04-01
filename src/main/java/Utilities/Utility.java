package Utilities;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Utility {
    private static final String SCREENSHOT_PATH = "test-outputs/ScreenShots/";
    //TODO : click on element

    public static void clickOnElement(WebDriver driver, By locater) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(locater));
        driver.findElement(locater).click();
    }

    // TODO : send data
    public static void sendData(WebDriver driver, By locater, String text) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.visibilityOfElementLocated(locater));
        driver.findElement(locater).sendKeys(text);
    }

    //TODO :get text
    public static String getText(WebDriver driver, By locater) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.visibilityOfElementLocated(locater));
        return driver.findElement(locater).getText();
    }

    //TODO: general wait
    public static WebDriverWait generalWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    //TODO: scrolling
    public static void scrolling(WebDriver driver, By locater) {
        ((JavascriptExecutor) driver).executeScript("arguement[0].scrollIntoView();", findWebElement(driver, locater));
    }

    // TODO : take By and return WebElement
    public static WebElement findWebElement(WebDriver driver, By locater) {
        return driver.findElement(locater);
    }

    //TODO : date
    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy-mm-dd-h-m-ssa").format(new Date());
    }


    // TODO :screenshot
    public static void takeScreenshot(WebDriver driver, String screenshotName) throws IOException {
        try {
            //Capture screenshot Using TakesScreenshot
            File screenScr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            //save screenshot to a file if needed
            File screenDest = new File(SCREENSHOT_PATH + screenshotName + "-" + getTimeStamp() + ".png");

            //Attach Screenshot to Allure
            FileUtils.copyFile(screenScr, screenDest);
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenDest.getPath())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO : Selecting from dropdown
    public static void selectingFromDropdown(WebDriver driver, By locater, String option) {
        new Select(findWebElement(driver, locater)).selectByVisibleText(option);
    }

    //TODO : random choice
    public static int generateRandomNumber(int upperBound) {
        return new Random().nextInt(upperBound) + 1;
    }

    // TODO : generate Unique number
    public static Set<Integer> generateUniqueNumbers(int numberOfProductsNeeded, int totalNumbersOfProducts) {
        Set<Integer> generatedNumbers = new HashSet<>();
        while (generatedNumbers.size() < numberOfProductsNeeded) {
            int randomNumber = generateRandomNumber(totalNumbersOfProducts);
            generatedNumbers.add(randomNumber);
        }
        return generatedNumbers;
    }

    //TODO:verify url
    public static boolean VerifyURL(WebDriver driver, String expectedURL) {
        try {
            generalWait(driver).until(ExpectedConditions.urlToBe(expectedURL));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static File getLatestFile(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        assert files != null;
        if (files.length == 0)
            return null;
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }

    public static Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    public static void restoreSession(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies)
            driver.manage().addCookie(cookie);
    }

}
