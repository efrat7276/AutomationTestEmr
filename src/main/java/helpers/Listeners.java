package helpers;

import drivers.DriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
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
        captureAndSaveScreenshot();
    }

 
    
    public void captureAndSaveScreenshot() { // הורדתי פרמטרים מיותרים ל-Allure
    try {
        var driver = DriverManager.getInstance();
        if (driver == null) return;
        
        // 1. צילום המסך
        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        
        // 2. הפקודה הישירה של Allure - זה תמיד עובד!
        Allure.addAttachment("Failed Screen", new ByteArrayInputStream(screenshotBytes));
        
        // 3. אופציונלי: תשאירי את השמירה הידנית שלך לתיקייה נפרדת (לא של אלור) אם את צריכה
        // FileUtils.writeByteArrayToFile(new File("C:/Temp/snap.png"), screenshotBytes);
        
        log.info("✓ Screenshot attached to Allure");
        
    } catch (Exception e) {
        log.error("Error: {}", e.getMessage());
    }
}
}
