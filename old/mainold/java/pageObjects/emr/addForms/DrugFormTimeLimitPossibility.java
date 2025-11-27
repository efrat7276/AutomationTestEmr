package pageObjects.emr.addForms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utilities.CommonOps;

import java.util.List;

public class DrugFormTimeLimitPossibility {


    @FindBy(how= How.XPATH, using="//button[@id='solutionDurationList']/following-sibling::ul/li")
    public List<WebElement> durationList;

    @FindBy(how=How.ID, using="solutionDurationList")
    public WebElement btn_duration;
}
