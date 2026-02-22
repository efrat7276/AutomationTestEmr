package drivers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverManager {

    private static WebDriver driver ;

    public static WebDriver getInstance(){
        if(driver==null){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--start-minimized");
            options.addArguments("window-size=1920,1080");
           ////todo לדבר עם רינה
            // String projectPath = System.getProperty("user.dir");      
            // System.setProperty("webdriver.chrome.driver", projectPath + "/src/main/java/resources/chromedriver.exe");
            driver=new ChromeDriver(options);

          initBrowser();
        }
        return driver;
    }

    /**
     * init Chrome browser
     *
     * @return
     */
    public static WebDriver initBrowser() {
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = caps.getBrowserName();
        String browserVersion = caps.getBrowserVersion();

        System.out.println("Browser: " + browserName);
        System.out.println("Version: " + browserVersion);

        //Resulation
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));
        //driver.manage().window().maximize();
        return driver;
    }

    public static void quitDriver() {
    if (driver != null) {
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