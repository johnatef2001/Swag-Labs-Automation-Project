package Tests;

import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
import Pages.P04_CheckoutPage;
import Utilities.DataUtil;
import Utilities.LogsUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtil.getPropertyValue;

public class TC04_CheckoutTest {
    private final String USERNAME = DataUtil.getJsonData("validLogin", "username");

    private final String PASSWORD = DataUtil.getJsonData("validLogin", "password");

    private final String FIRSTNAME = DataUtil.getJsonData("information", "fName") + "-" + Utility.getTimeStamp();

    private final String LASTNAME = DataUtil.getJsonData("information", "lName") + "-" + Utility.getTimeStamp();

    private final String ZIPCODE = new Faker().number().digits(5);


    public TC04_CheckoutTest() throws FileNotFoundException {
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("page redirected to the URL");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void checkoutStepOneTC() throws IOException {
        LogsUtils.info("The ValidLoginTC is started");
        //TODO : Login Steps
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton();
        //TODO : Adding products steps
        new P02_LandingPage(getDriver()).addRandomProducts(3, 6)
                .clickOnCartIcon();
        //TODO : Go to checkout Page
        new P03_CartPage(getDriver()).clickOnCheckoutButton();
        //TODO : Filling information steps
        new P04_CheckoutPage(getDriver()).fillingInformationForm(FIRSTNAME, LASTNAME, ZIPCODE)
                .clickOnContinueButton();
        LogsUtils.info(FIRSTNAME + " " + LASTNAME + " " + ZIPCODE);
        Assert.assertTrue(Utility.VerifyURL(getDriver(), getPropertyValue("environment", "CHECKOUT_URL")));
        LogsUtils.info("the checkoutStepOneTC is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }
}
