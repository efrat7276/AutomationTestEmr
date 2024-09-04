package utilities;
import extensions.DBAction;
import extensions.UIActions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import org.testng.asserts.SoftAssert;
import java.time.Duration;

public class CommonOps extends Base {

     static String env = null;
     static String sql_server = null;
    public  void initBrowser(String browserType){
        if(browserType.equalsIgnoreCase("chrome"))
            driver = initChromeDriver();
        else throw new RuntimeException("Invalid Browser Type") ;


        //Resulation
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1920,1080) );
        //driver.manage().window().maximize();

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

    public static WebDriver initChromeDriver(){

     //  WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-minimized");
        options.addArguments("window-size=1920,1080");

       // options.setBinary("C:\\Users\\chrome\\ChromeStandaloneSetup.exe");
    //    System. setProperty("webdriver.chrome.driver", "C:\\Users\\chromedriver\\114.0.5735.90\\chromedriver.exe");
        driver = new ChromeDriver(options);
        return driver;
    }

    @BeforeMethod
    public void startSession() {
        initBrowser(Helpers.getData("BrowserName"));


    }
     @BeforeSuite
     public void BeforeSuite() {

        //קבלת פרמטר מהrunner על איזה סביבה להריץ טסטים
         try{
            env = System.getenv("environment");
             sql_server = System.getenv("sql_server");

            if(env == null)
           //   env= "qa";
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

    @AfterClass
    public void closeSession(){
      //  ManageDB.closeConnection();
        driver.quit();
    }

    @AfterMethod
    public static void afterMethod1() {

        driver.quit();

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
        UIActions.clearText(emrLogin.txt_username);
        UIActions.clearText(emrLogin.txt_password);
    }


    public static void reLogin(){

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
        }


        try {
            Alert confirm_popup = driver.switchTo().alert();
            confirm_popup.accept();
        }
        catch (Exception ex){

        }
        UIActions.clearText(emrLogin.txt_username);
        UIActions.clearText(emrLogin.txt_password);

    }

}
