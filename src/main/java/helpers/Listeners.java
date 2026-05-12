package helpers;

import drivers.DriverManager;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

@Slf4j
public class Listeners implements ITestListener {

    public void onStart(ITestContext result) {
        log.info("--------------------Starting Execution--------------------");
    }

    public void onFinish(ITestContext result) {
        log.info("--------------------Ending Execution--------------------");
    }

    public void onTestStart(ITestResult result) {
        log.info("--------Starting Test: {} -----------", result.getName());
    }

    public void onTestSkipped(ITestResult result) {
        log.info("--------Skipping Test: {} -----------", result.getName());
    }

    public void onTestSuccess(ITestResult result) {
        log.info("-------- Test: {} Passed -----------", result.getName());
    }

    public void onTestFailure(ITestResult result) {   
        log.info(">>> Capturing and saving screenshot for Allure...");
        captureAndSaveScreenshot(result.getName());
    }

    /**
     * Captures screenshot and saves it to allure-results directory.
     * Called from onTestFailure when a test fails.
     */
    @Attachment(value = "Screen-Shot", type = "image/png")
    public byte[] captureAndSaveScreenshot(String testName) {
        try {
            var driver = DriverManager.getInstance();
            if (driver == null) {
                log.error("Driver instance is null");
                return new byte[0];
            }
            
            var screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            var allureDir = new File("allure-results");
            if (!allureDir.exists()) allureDir.mkdirs();
            
            var fileName = FilesHelper.getFileName(testName) + ".png";
            FileUtils.writeByteArrayToFile(new File(allureDir, fileName), screenshotBytes);
            log.info("✓ Screenshot saved: {}", fileName);
            
            return screenshotBytes;
        } catch (Exception e) {
            log.error("Error capturing screenshot: {}", e.getMessage());
            return new byte[0];
        }
    }
}
