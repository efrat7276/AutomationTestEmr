package pages;

import actionUtilies.UIActions;
import drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {

    protected WebDriver driver = DriverManager.getInstance();

   public   UserSignModalPage userSignModalPage = new UserSignModalPage();

   private final String TITLE_TEMPLATE = "//span[normalize-space(text())='%s']";

   public Boolean verificationTitleIsDisplay(String titleText){
     String dynamicXpath=  String.format(TITLE_TEMPLATE, titleText);
       By targetLocator = By.xpath(dynamicXpath);
      return  driver.findElement( targetLocator).isDisplayed();
   }

}
