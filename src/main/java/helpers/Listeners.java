package helpers;

import drivers.DriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.util.List;

public class Listeners implements ITestListener {

    public void onStart(ITestContext result) {
        System.out.println("--------------------Starting Execution--------------------");
    }

    public void onFinish(ITestContext result) {
        System.out.println("--------------------Ending Execution--------------------");
    }

    public void onTestStart(ITestResult result) {
        System.out.println("--------Starting Test: " + result.getName() + "------------");
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println("--------Skipping Test: " + result.getName() + "------------");
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("-------- Test: " + result.getName() + " Passed -----------");
    }

    public void onTestFailure(ITestResult result) {
        System.out.println("-------- Test: " + result.getName() + " Failed -----------");
        try {
            LogEntries entry = DriverManager.getInstance().manage().logs().get(LogType.BROWSER);
            List<LogEntry> logs = entry.getAll();
            
            if (logs.isEmpty()) {
                System.out.println("No Console Logs");
            } else {
                for (LogEntry e : logs) {
                    System.out.println("Message is: " + e.getMessage());
                    System.out.println("Level is: " + e.getLevel());
                }
            }
        } catch (Exception e) {
            System.out.println("Could not retrieve browser logs: " + e.getMessage());
        }
        saveScreenshot();
    }

    @Attachment(value = "Screen-Shot", type = "image/png")
    public byte[] saveScreenshot() {
        if (DriverManager.getInstance() != null) {
            return ((TakesScreenshot) DriverManager.getInstance()).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

    public static File saveScreenshotFile() {
        if (DriverManager.getInstance() != null) {
            return ((TakesScreenshot) DriverManager.getInstance()).getScreenshotAs(OutputType.FILE);
        }
        return null;
    }
}
