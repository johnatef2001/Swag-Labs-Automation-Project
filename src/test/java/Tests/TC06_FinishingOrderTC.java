package Tests;

import Pages.*;
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

public class TC06_FinishingOrderTC {
    private final String USERNAME = DataUtil.getJsonData("validLogin", "username");

    private final String PASSWORD = DataUtil.getJsonData("validLogin", "password");

    private final String FIRSTNAME = DataUtil.getJsonData("information", "fName") + "-" + Utility.getTimeStamp();

    private final String LASTNAME = DataUtil.getJsonData("information", "lName") + "-" + Utility.getTimeStamp();

    private final String ZIPCODE = new Faker().number().digits(5);


    public TC06_FinishingOrderTC() throws FileNotFoundException {
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
    public void finishOrderTC() throws IOException {
        LogsUtils.info("The ValidLoginTC is started");
        //TODO : Login Steps
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton();
        //TODO : Adding products steps
        new P02_LandingPage(getDriver()).addAllProductsToCart()
                .clickOnCartIcon();
        //TODO : Go to checkout Page
        new P03_CartPage(getDriver()).clickOnCheckoutButton();
        //TODO : Filling information steps
        new P04_CheckoutPage(getDriver()).fillingInformationForm(FIRSTNAME, LASTNAME, ZIPCODE)
                .clickOnContinueButton();
        LogsUtils.info(FIRSTNAME + " " + LASTNAME + " " + ZIPCODE);
        //TODO : Go To finishing Order Page
        new P05_OverviewPage(getDriver()).clickOnFinishButton();
        Assert.assertTrue(new P06_FinishingOrderPage(getDriver()).checkVisibilityOfThanksMessage());
        LogsUtils.info("the checkoutStepTwoTC is passed");
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
