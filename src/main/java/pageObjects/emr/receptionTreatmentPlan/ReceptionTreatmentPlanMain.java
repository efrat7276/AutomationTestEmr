package pageObjects.emr.receptionTreatmentPlan;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class ReceptionTreatmentPlanMain {

    @FindBy(how=How.ID, using="open-nutrition-pln")
    public WebElement div_nutritionPlan;

    @FindBy(how=How.XPATH, using="open-fluid-pln")
    public WebElement div_fluidPlan;

    @FindBy(how=How.ID, using="open-fluid-bolus")
    public WebElement div_openFluidBolus;

    @FindBy(how=How.ID, using="open-continious")
    public WebElement div_openContinious;

    @FindBy(how=How.ID, using="open-common")
    public WebElement div_openDrugCommon;
//
//    @FindBy(how=How.XPATH, using="//button[1]")
//    public WebElement btn_ok;

}


