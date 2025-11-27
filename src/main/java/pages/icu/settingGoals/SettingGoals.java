package pages.icu.settingGoals;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SettingGoals {

    @FindBy(how=How.ID, using="tab-1")
    public WebElement settingGoalsTab;


    @FindBy(how=How.ID, using="tab-2")
    public WebElement receptionPlanTab;


    @FindBy(how=How.ID, using="tab-3")
    public WebElement doctorInstructionTab;

}


