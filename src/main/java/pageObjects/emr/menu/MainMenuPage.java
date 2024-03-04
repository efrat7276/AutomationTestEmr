package pageObjects.emr.menu;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utilities.CommonOps;

public class MainMenuPage  {

    @FindBy(how = How.XPATH ,using="//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='הכנת תרופות']")
    public WebElement category_drugPreparation;

    @FindBy(how = How.XPATH ,using="//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='רשימת משוחררים']")
    public WebElement category_dischargedList;

    @FindBy(how = How.XPATH ,using="//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='הזמנות']")
    public WebElement category_ordersList;

    @FindBy(how = How.XPATH ,using="//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='דוחות']")
    public WebElement category_reportsList;

    @FindBy(how = How.XPATH ,using="//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='פרוטוקולים']")
    public WebElement category_protocolList;

    @FindBy(how = How.XPATH ,using="//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='חדר ניתוח']")
    public WebElement category_operations;


    @FindBy(how = How.XPATH ,using="//app-inner-menu//div[@class='back-to-list top-menu']")
    public WebElement category_patientList;

}
