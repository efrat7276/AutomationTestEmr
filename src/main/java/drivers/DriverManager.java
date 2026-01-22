package drivers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
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
            String projectPath = System.getProperty("user.dir");      
            System.setProperty("webdriver.chrome.driver", projectPath + "/src/main/java/resources/chromedriver.exe");
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
}
