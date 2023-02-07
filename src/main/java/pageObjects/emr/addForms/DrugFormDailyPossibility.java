package pageObjects.emr.addForms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DrugFormDailyPossibility {

   @FindBy(how=How.XPATH, using="//ul[@aria-labelledby='numberOfTimes_daily']/li")
    public List<WebElement> numberOfTimesDaily;

    @FindBy(how=How.ID, using="numberOfTimes_daily")
    public WebElement btn_numberOfTimesDaily;

    @FindBy(how=How.XPATH, using="//emr-datepicker//i")
    public WebElement btn_datePicker;

    @FindBy(how=How.XPATH, using="//emr-datepicker//div[@class='ngb-dp-day ngb-dp-today']")
    public WebElement currentDay_datePicker;

    @FindBy(how=How.XPATH, using="//emr-datepicker//div[@class='ngb-dp-day']")
    public List<WebElement> enableDays_datePicker;
}
