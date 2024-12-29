package pageObjects.emr.icu.receptionTreatmentPlan;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class ReceptionTreatmentPlanMain {

    @FindBy(how=How.ID, using="open-nutrition-pln")
    public WebElement div_nutritionPlan;

    @FindBy(how=How.ID, using="open-fluid-pln")
    public WebElement div_openFluidPlan;

    @FindBy(how=How.ID, using="open-fluid-bolus")
    public WebElement div_openFluidBolusPlan;

    @FindBy(how=How.ID, using="open-continious")
    public WebElement div_openContiniouPlan;

    @FindBy(how=How.ID, using="open-common")
    public WebElement div_openDrugCommonPlan;

    @FindBy(how=How.ID, using="saveAllData")
    public WebElement btn_ok;

    @FindBy(how=How.ID, using="clearAllData")
    public WebElement btn_cancel;

}


