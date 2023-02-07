package pageObjects.emr.menu;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utilities.CommonOps;

public class MainMenuPage  {

    @FindBy(how = How.XPATH ,using="//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='הכנת תרופות']")
    public WebElement mainCategory_drugPreparation;

    @FindBy(how = How.XPATH ,using="//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='רשימת מטופלים']")
    public WebElement mainCategory_patientList;

}
