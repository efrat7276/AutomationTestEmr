package pageObjects.emr.addForms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import java.util.List;

public class DrugFormSOSPossibility {

    @FindBy(how= How.XPATH, using="//button[@id='sosMaxTimesPerDay']/following-sibling::ul/li")
    public List<WebElement> sosMaxTimesPerDayList;

    @FindBy(how=How.ID, using="sosMaxTimesPerDay")
    public WebElement btn_sosMaxTimesPerDay;

    @FindBy(how= How.XPATH, using="//button[@id='sosMinTimesPerDay']/following-sibling::ul/li")
    public List<WebElement> sosMinTimesPerDayList;

    @FindBy(how=How.ID, using="sosMinTimesPerDay")
    public WebElement btn_sosMinTimesPerDay;

    @FindBy(how= How.XPATH, using="")
    public List<WebElement> span_maxDosage;

}
