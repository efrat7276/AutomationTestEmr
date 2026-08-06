package pages.addForms;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import pages.BasePage;

@Slf4j
public class DrugFormTimeLimitPossibility extends BasePage {

    public DrugFormTimeLimitPossibility() {
        UIActions.waitForSpinnerToDisappear();
    }

    private By durationList = By.xpath("//button[@id='solutionDurationList']/following-sibling::ul/li");

    private By btn_duration = By.id("solutionDurationList");
}
