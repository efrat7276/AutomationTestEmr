package drivers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
@Slf4j
public class DriverManager {

    private static WebDriver driver ;

    public static WebDriver getInstance(){
        if(driver==null){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--start-maximized");  // Maximize on startup
            options.addArguments("--no-first-run");
            options.addArguments("--no-default-browser-check");
            driver = new ChromeDriver(options);
            initBrowser();
        }
        return driver;
    }

    /**
     * Initialize Chrome browser with maximum resolution for both local and remote machines.
     */
    public static WebDriver initBrowser() {
        log.info("Initializing browser with maximum resolution");
        
        try {
            Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
            String browserName = caps.getBrowserName();
            String browserVersion = caps.getBrowserVersion();
            
            log.info("Browser: {}", browserName);
            log.info("Version: {}", browserVersion);
            
            // Maximize window - works for both local and remote machines
            driver.manage().window().maximize();
            
            // Get actual screen size after maximization
            Dimension windowSize = driver.manage().window().getSize();
            log.info("Window size: {}x{}", windowSize.getWidth(), windowSize.getHeight());
            
            return driver;
        } catch (Exception e) {
            log.error("Error initializing browser: {}", e.getMessage());
            return driver;
        }
    }

    public static void quitDriver() {

    if (driver != null) {
        log.info("Quitting driver and cleaning up resources");
        driver.quit();
        driver = null;
    }
  }

    /**
     * Return a WebDriverWait tied to the current driver instance.
     * Creates a new wait each call; if you need per-thread reuse consider ThreadLocal.
     */
    public static WebDriverWait getWait() {
      // Use ThreadLocal to provide a per-thread WebDriverWait instance.
      // This prevents sharing a single WebDriverWait between parallel test threads.
      return WaitHolder.WAIT_THREADLOCAL.get();
    }

    // Inner holder for ThreadLocal wait instances
    private static class WaitHolder {
        private static final ThreadLocal<WebDriverWait> WAIT_THREADLOCAL = ThreadLocal.withInitial(() ->
                new WebDriverWait(getInstance(), Duration.ofSeconds(30))
        );
    }
}