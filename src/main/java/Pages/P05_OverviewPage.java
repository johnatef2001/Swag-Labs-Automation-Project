package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {
    private final By subtotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    private final WebDriver driver;

    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public Float getSubTotal() {
        return Float.parseFloat(Utility.getText(driver, subtotal).replace("Item total: $", ""));
    }

    public Float getTax() {
        return Float.parseFloat(Utility.getText(driver, tax).replace("Tax: $", ""));
    }

    public Float getTotal() {
        LogsUtils.info("Actual Total price:" + (Utility.getText(driver, total).replace("Total: $", "")));
        return Float.parseFloat(Utility.getText(driver, total).replace("Total: $", ""));
    }

    public String calculateTotalPrice() {
        LogsUtils.info("calculated Total price:" + (getSubTotal() + getTax()));
        return String.valueOf(getSubTotal() + getTax());
    }

    public boolean comparingPrices() {
        return calculateTotalPrice().equals(String.valueOf(getTotal()));
    }

    public P06_FinishingOrderPage clickOnFinishButton() {
        Utility.clickOnElement(driver, finishButton);
        return new P06_FinishingOrderPage(driver);

    }
}
