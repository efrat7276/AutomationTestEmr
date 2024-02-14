package pageObjects.emr.icu.receptionTreatmentPlan;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utilities.Base;

import java.util.List;

public class CommonMedicationPlan extends Base {


    @FindBy(how=How.ID, using="glucos")
    public WebElement radio_economy;


    @FindBy(how=How.XPATH, using="//drugs-for-chooose[2]//input[contains(@id,'check')]")
    public List<WebElement> list_selectMedication;

    @FindBy(how=How.XPATH, using="//drugs-for-chooose[2]//select[contains(@id,'route-admin')]")
    public List<WebElement> list_routeAdminSelect;


    @FindBy(how=How.XPATH, using="//drugs-for-chooose[2]//select[contains(@id,'route-admin')][5]/option")
    public List<WebElement> list_routeAdmin;

}


