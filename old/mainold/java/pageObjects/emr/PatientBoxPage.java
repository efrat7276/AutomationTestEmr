package pageObjects.emr;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static org.testng.Assert.assertTrue;

public class PatientBoxPage {

    @FindBy(how= How.XPATH , using = "//app-patient-detail/div")
    public WebElement bar_deatails_patient;

    @FindBy(how= How.CSS , using = "div.content-container")
    public WebElement div_instruction;

  //  private By bar_deatails_patient = By.xpath("//app-patient-detail/div");




}
