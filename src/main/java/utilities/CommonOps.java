package utilities;
import extensions.DBAction;
import extensions.UIActions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.sikuli.script.Screen;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonOps extends Base {

    static String env = null;
    public static String getEnv(){
        return env;
    }

    public static String getFileName(String desc){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        return desc+"_"+dtf.format(now);
    }

    public static String getData(String nodeName) {

        DocumentBuilder dBuilder;
        Document doc = null;
        File fxmlFile = new File("./Configuration/DataConfig.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fxmlFile);
        }
        catch (Exception e) {
            System.out.println("Exception in reading XMLfile: "+ e);
        }
        doc.getDocumentElement().normalize();
        return  doc.getElementsByTagName(nodeName).item(0).getTextContent();
    }

//    public static String getDataFromData(String nodeName1, String nodeName2) {
//
//        DocumentBuilder dBuilder;
//        Document doc = null;
//        File fxmlFile = new File("./Configuration/DataConfig.xml");
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        try {
//            dBuilder = dbFactory.newDocumentBuilder();
//            doc = dBuilder.parse(fxmlFile);
//        }
//        catch (Exception e) {
//            System.out.println("Exception in reading XMLfile: "+ e);
//        }
//        doc.getDocumentElement().normalize();
//        return  ((Element)doc.getElementsByTagName(nodeName1).item(1)).getTextContent();
//    }


    public static void initBrowser(String browserType){
        if(browserType.equalsIgnoreCase("chrome"))
            driver = initChromeDriver();
        else throw new RuntimeException("Invalid Browser Type") ;


        //Resulation
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1920,1080) );
        //driver.manage().window().maximize();


        //Wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong((getData("Timeout")))));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong((getData("Timeout")))));


        switch (env) {
            case "qa":
                driver.get(getData("url-qa"));
                break;

            case "prod":
                driver.get(getData("url-prod"));
                break;

            case "dev":
                driver.get(getData("url-dev"));
                break;

            case "production":
                driver.get(getData("url-production"));
                break;
        }
      ManagePages.initEmr();
      action = new Actions(driver);
    }

    public static WebDriver initChromeDriver(){
        WebDriverManager.chromedriver().setup();

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized");
//        options.addArguments("disable-infobars");
//        options.addArguments("--disable-notifications");
//        options.addArguments("--auto-open-devtools-for-tabs");
 //       driver = new ChromeDriver(options);

        driver = new ChromeDriver();

        return driver;
    }

    @BeforeClass
    public void startSession() {
        //  String platform = "web";
        if (getData("PlatformName").equalsIgnoreCase("web"))
            initBrowser(getData("BrowserName"));
        else
            throw new RuntimeException("Invalid platform name");


      //  screen = new Screen();
        softAssert = new SoftAssert();

    }

     @BeforeSuite
     public void BeforeSuite() {

         try{
            env = System.getenv("environment");
            if(env == null)
                env= "qa";
         }
        catch (SecurityException e){
           env= "qa";
        }
         switch (env){
             case "qa":
                 ManageDB.openConnection(getData("DBUrl-qa"),getData("DBName"),getData("DBPassword"));
                 break;
             case "prod":
                 ManageDB.openConnection(getData("DBUrl-prod"),getData("DBName"),getData("DBPassword"));
                 break;
             case "dev":
                 ManageDB.openConnection(getData("DBUrl-dev"),getData("DBName"),getData("DBPassword"));
                 break;
             case "production":
                 ManageDB.openConnection(getData("DBUrl-production"),getData("DBName"),getData("DBPassword"));
                 break;

         }

     }


    @AfterClass
    public void closeSession(){
      //  ManageDB.closeConnection();
        driver.quit();
    }

    @AfterMethod
    public static void afterMethod(){

        switch (env) {
            case "qa":
                driver.get(getData("url-qa"));
                break;

            case "prod":
                driver.get(getData("url-prod"));
                break;

            case "dev":
                driver.get(getData("url-dev"));
                break;
        }


        try {
            Alert confirm_popup = driver.switchTo().alert();
            confirm_popup.accept();
        }
        catch (NoAlertPresentException ex){

        }
        UIActions.clearText(emrLogin.txt_username);
        UIActions.clearText(emrLogin.txt_password);
    }

}
