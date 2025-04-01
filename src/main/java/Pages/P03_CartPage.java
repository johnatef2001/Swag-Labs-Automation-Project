package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {

    static float totalPrice = 0;

    private final By checkoutButton = By.id("checkout");

    private final WebDriver driver;

    private final By pricesOfSelectedProductsLocater = By.xpath("//button[.=\"Remove\"]//preceding-sibling::div[@class='inventory_item_price']");


    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPrice() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(pricesOfSelectedProductsLocater);
            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {
                By elements = By.xpath("(//button[.=\"Remove\"]//preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");
                String fullText = Utility.getText(driver, elements);
                LogsUtils.info("Total Price" + totalPrice);
                totalPrice += Float.parseFloat(fullText.replace("$", ""));
            }
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    public boolean comparingPrices(String price) {
        return getTotalPrice().equals(price);
    }

    public P04_CheckoutPage clickOnCheckoutButton() {
        Utility.clickOnElement(driver, checkoutButton);
        return new P04_CheckoutPage(driver);
    }

}
