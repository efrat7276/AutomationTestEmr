package pageObjects.emr.receptionTreatmentPlan;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class SolutionPlan {

    @FindBy(how=How.ID, using="solution-inp")
    public WebElement input_solution_item;

    @FindBy(how=How.ID, using="solution-rate")
    public WebElement input_solution_rate;

    // תוספת Mgso4

    @FindBy(how=How.ID, using="Mgso4-1")
    public WebElement isMgsoYes;

    @FindBy(how=How.ID, using="Mgso4-0")
    public WebElement isMgsoNo;

    // תוספת KCL

    @FindBy(how=How.ID, using="KCL-1")
    public WebElement isKCLYes;

    @FindBy(how=How.ID, using="KCL-0")
    public WebElement isKCLNo;

    // תוספת D50W

    @FindBy(how=How.ID, using="D50W-1")
    public WebElement isD50WYes;

    @FindBy(how=How.ID, using="D50W-0")
    public WebElement isD50WNo;

}


