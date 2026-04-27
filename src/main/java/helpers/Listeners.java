package helpers;

import drivers.DriverManager;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
        System.out.println("-------- Test: " + result.getName() + " Failed -----------");
        System.out.println("Failure message: " + result.getThrowable());
        
        try {
            LogEntries entry = DriverManager.getInstance().manage().logs().get(LogType.BROWSER);
            List<LogEntry> logs = entry.getAll();
            
            if (logs.isEmpty()) {
                log.info("No Console Logs");
            } else {
                for (LogEntry e : logs) {
                    log.info("Message is: {}", e.getMessage());
                    log.info("Level is: {}", e.getLevel());
                }
            }
        } catch (Exception e) {
            log.error("Could not retrieve browser logs: {}", e.getMessage());
        }
        
        // טייק צילום מסך ושמור ל-Allure
        log.info(">>> Calling saveScreenshot() for Allure...");
        saveScreenshot();
        
        // טייק צילום מסך ושמור לתיקייה על הדיסק
        log.info(">>> Calling saveScreenshotFile() to save to disk...");
        try {
            File screenshotFile = saveScreenshotFile();
            if (screenshotFile != null) {
                log.info("✓ Screenshot file saved successfully to: {}", screenshotFile.getAbsolutePath());
            } else {
                log.error("✗ Screenshot file is null - driver may have been closed");
            }
        } catch (Exception e) {
            log.error("✗ Could not save screenshot file: {}", e.getMessage());
        }
    }

    @Attachment(value = "Screen-Shot", type = "image/png")
    public byte[] saveScreenshot() {
        try {
            if (DriverManager.getInstance() != null) {
                log.info("Taking screenshot for Allure...");
                return ((TakesScreenshot) DriverManager.getInstance()).getScreenshotAs(OutputType.BYTES);
            }
        } catch (Exception e) {
            log.error("Error taking screenshot for Allure: {}", e.getMessage());
        }
        return new byte[0];
    }

    public static File saveScreenshotFile() {
        try {
            if (DriverManager.getInstance() != null) {
                log.info("Saving screenshot file to temp directory...");
                File screenshotFile = ((TakesScreenshot) DriverManager.getInstance()).getScreenshotAs(OutputType.FILE);
                
                if (screenshotFile != null && screenshotFile.exists()) {
                    log.info("Screenshot file created successfully at: {}", screenshotFile.getAbsolutePath());
                    log.info("File size: {} bytes", screenshotFile.length());
                    
                    // Copy file to our temp directory
                    File tempDir = new File("temp");
                    if (!tempDir.exists()) {
                        tempDir.mkdirs();
                        log.info("Created temp directory: {}", tempDir.getAbsolutePath());
                    }
                    
                    String fileName = "screenshot_" + System.currentTimeMillis() + ".png";
                    File destFile = new File(tempDir, fileName);
                    
                    Files.copy(screenshotFile.toPath(), destFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    log.info("✓ Screenshot copied to: {}", destFile.getAbsolutePath());
                    
                    return destFile;
                } else {
                    log.error("Screenshot file was not created or does not exist");
                }
            } else {
                log.error("Driver instance is null, cannot take screenshot");
            }
        } catch (Exception e) {
            log.error("Error saving screenshot file: {}", e.getMessage());
        }
        return null;
    }
}
