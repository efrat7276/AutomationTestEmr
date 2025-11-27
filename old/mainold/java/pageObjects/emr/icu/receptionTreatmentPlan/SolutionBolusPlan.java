package pageObjects.emr.icu.receptionTreatmentPlan;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class SolutionBolusPlan {

    @FindBy(how=How.ID, using="solution-bolus-inp")
    public WebElement input_solutionBolus_item;

    @FindBy(how=How.ID, using="solution-bolus-dosage")
    public WebElement input_solutionBolus_dosage;


    @FindBy(how=How.ID, using="solution-bolus-during")
    public WebElement btn_solution_bolus_during;


    @FindBy(how=How.XPATH, using="//button[@id='solution-bolus-during']/following-sibling::ul/li")
    public List<WebElement>  btn_solution_bolus_duringList;

    @FindBy(how=How.ID, using="solution-bolus-other")
    public WebElement input_solution_bolus_duringOther;

}


