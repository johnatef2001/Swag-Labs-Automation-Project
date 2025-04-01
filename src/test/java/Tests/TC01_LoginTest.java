package Tests;

import Pages.P01_LoginPage;
import Utilities.DataUtil;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtil.getPropertyValue;

public class TC01_LoginTest {

    private final String USERNAME = DataUtil.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtil.getJsonData("validLogin", "password");

    public TC01_LoginTest() throws FileNotFoundException {
    }

    @BeforeMethod
    public void setup() throws IOException {
        //condition ? true : false
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : getPropertyValue("environment", "Browser");
        LogsUtils.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("page redirected to the URL");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void validLoginTC() throws IOException {
        LogsUtils.info("The ValidLoginTC is started");
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTc(getPropertyValue("environment", "HOME_URL")));
        LogsUtils.info("The ValidLoginTC is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }


}
