package pageObjects.emr;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PatientBoxPage {

    @FindBy(how= How.XPATH , using = "//app-patient-detail/div")
    public WebElement bar_deatails_patient;

    @FindBy(how= How.CSS , using = "div.content-container")
    public WebElement div_instruction;


}
