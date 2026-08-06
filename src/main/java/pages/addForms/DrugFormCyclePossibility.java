package pages.addForms;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import pages.BasePage;

@Slf4j
public class DrugFormCyclePossibility extends BasePage {

    public DrugFormCyclePossibility() {
        UIActions.waitForSpinnerToDisappear();
    }

    private By numberOfTimesCycle = By.xpath("//ul[@aria-labelledby='numberOfTimes_daily']/li");

    private By btn_numberOfTimesCycle = By.id("numberOfTimes_daily");
}
