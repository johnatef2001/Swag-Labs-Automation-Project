package Tests;

import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
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

public class TC03_CartTest {
    private final String USERNAME = DataUtil.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtil.getJsonData("validLogin", "password");

    public TC03_CartTest() throws FileNotFoundException {
    }

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("page redirected to the URL");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void comparingPricesTC() throws IOException {
        LogsUtils.info("The ValidLoginTC is started");
        String totalPrice = new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton()
                .addRandomProducts(3, 6)
                .getTotalPriceOfSelectedProducts();
        new P02_LandingPage(getDriver()).clickOnCartIcon();
        Assert.assertTrue(new P03_CartPage(getDriver()).comparingPrices(totalPrice));
        LogsUtils.info("the comparingPricesTC is passed");
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
