package pageObjects.emr.addForms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DrugFormWeeklyPossibility {

    @FindBy(how= How.XPATH, using="//button[@id='WeekNumberOfTimes']/following-sibling::ul/li")
    public List<WebElement> weekNumberOfTimesList;

    @FindBy(how=How.ID, using="WeekNumberOfTimes")
    public WebElement btn_WeekNumberOfTimes;
}
