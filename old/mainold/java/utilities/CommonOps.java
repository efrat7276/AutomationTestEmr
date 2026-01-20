package utilities;
import extensions.UIActions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import org.testng.asserts.SoftAssert;
import java.time.Duration;

public class CommonOps extends Base {

     static String env = null;
     static String sql_server = null;


    @BeforeSuite
    public void BeforeSuite() {

        // הגדרת סביבת הרצה והתחברות לDB של אותה סביבה


        //קבלת פרמטר מהrunner על איזה סביבה להריץ טסטים
        try{
            env = System.getenv("environment");
            sql_server = System.getenv("sql_server");

            if(env == null)
                //    env= "qa";
                env= "prod";
            //      env= "production";

            //env= "automation";

            //   if(sql_server==null)

        }
        catch (SecurityException e){
            env= "qa";
        }


        switch (env){
            case "qa":
                ManageDB.openConnection(Helpers.getData("DBUrl-qa"),Helpers.getData("DBName"),Helpers.getData("DBPassword"));
                break;
            case "prod":
                ManageDB.openConnection(Helpers.getData("DBUrl-prod"),Helpers.getData("DBName"),Helpers.getData("DBPassword"));
                break;
            case "dev":
                ManageDB.openConnection(Helpers.getData("DBUrl-dev"),Helpers.getData("DBName"),Helpers.getData("DBPassword"));
                break;
            case "production":
                ManageDB.openConnection(Helpers.getData("DBUrl-production"),Helpers.getData("DBName"),Helpers.getData("DBPassword"));
                break;
            default:
                // ManageDB.openConnection(Helpers.getData("DBUrl-production"),Helpers.getData("DBName"),Helpers.getData("DBPassword"));
                ManageDB.openConnection(Helpers.getData("DBUrl-dev"),Helpers.getData("DBName"),Helpers.getData("DBPassword"));

        }
    }

    @BeforeMethod
    public void startSession() {
        initBrowser(Helpers.getData("BrowserName"));

    }
    public  void initBrowser(String browserType){

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-minimized");
        options.addArguments("window-size=1920,1080");
        driver = new ChromeDriver(options);
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = caps.getBrowserName();
        String browserVersion = caps.getBrowserVersion();

        System.out.println("Browser: " + browserName);
        System.out.println("Version: " + browserVersion);

        //Resulation
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1920,1080) );
        //driver.manage().window().maximize();


        //move to baseTset
        //Wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong((Helpers.getData("Timeout")))));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong((Helpers.getData("Timeout")))));


        switch (env) {
            case "qa":
                driver.get(Helpers.getData("url-qa"));
                break;

            case "prod":
                driver.get(Helpers.getData("url-prod"));
                break;

            case "dev":
                driver.get(Helpers.getData("url-dev"));
                break;


            case "automation":
                driver.get(Helpers.getData("url-automation"));
                break;

            case "production":
                driver.get(Helpers.getData("url-production"));
                break;
        }
      ManagePages.initEmr();
      action = new Actions(driver);
      softAssert = new SoftAssert();
      //  screen = new Screen();
    }

    @AfterClass
    public void closeSession(){
      //  ManageDB.closeConnection();
       try {
        driver.quit();
       } catch (Exception e) {
        // TODO: handle exception
       } 
    }

    public static void afterMethod(){

        switch (env) {
            case "qa":
                driver.get(Helpers.getData("url-qa"));
                break;

            case "prod":
                driver.get(Helpers.getData("url-prod"));
                break;

            case "dev":
                driver.get(Helpers.getData("url-dev"));
                break;

            case "production":
                driver.get(Helpers.getData("url-production"));
                break;
        }


        try {
            Alert confirm_popup = driver.switchTo().alert();
            confirm_popup.accept();
        }
        catch (Exception ex){

        }
        driver.quit();
 
    }


}
