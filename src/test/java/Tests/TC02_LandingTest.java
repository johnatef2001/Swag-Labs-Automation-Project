package Tests;

import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utilities.DataUtil;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtil.getPropertyValue;
import static Utilities.Utility.getAllCookies;
import static Utilities.Utility.restoreSession;

public class TC02_LandingTest {
    private final String USERNAME = DataUtil.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtil.getJsonData("validLogin", "password");
    private Set<Cookie> cookies;

    public TC02_LandingTest() throws FileNotFoundException {
    }


    @BeforeClass
    public void login() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("page redirected to the URL");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton();
        cookies = getAllCookies(getDriver());
        quitDriver();
    }

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("page redirected to the URL");
        restoreSession(getDriver(), cookies);
        getDriver().get(getPropertyValue("environment", "HOME_URL"));
        getDriver().navigate().refresh();

    }


    @Test
    public void checkingNumberOfSelectedProductsTC() {
        LogsUtils.info("The ValidLoginTC is started");

        new P02_LandingPage(getDriver()).addAllProductsToCart();
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductWithCart());
        LogsUtils.info("The ValidLoginTC is passed");
    }

    @Test
    public void addingRandomProductsToCartTC() {
        LogsUtils.info("The ValidLoginTC is started");

        new P02_LandingPage(getDriver()).addRandomProducts(3, 6);
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductWithCart());
        LogsUtils.info("The ValidLoginTC is passed");
    }

    @Test
    public void clickOnCartIcon() throws IOException {
        LogsUtils.info("The ValidLoginTC is started");
        new P02_LandingPage(getDriver()).clickOnCartIcon();
        Assert.assertTrue(Utility.VerifyURL(getDriver(), DataUtil.getPropertyValue("environment", "CART_URL")));
        LogsUtils.info("The ValidLoginTC is passed");
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }

    @AfterClass
    public void deleteSession() {
        cookies.clear();
    }
}
