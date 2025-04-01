package Listeners;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.IOException;

import static DriverFactory.DriverFactory.getDriver;

public class IInvokedListenerClass implements IInvokedMethodListener {
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
}

    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        if(testResult.getStatus()==ITestResult.FAILURE) {
            try {
                LogsUtils.info("Test Case" + testResult.getName() + "failed");
                Utility.takeScreenshot(getDriver(),testResult.getName()); //validLoginTC-2025-3-21-4-31am
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
