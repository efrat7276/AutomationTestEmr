package pages.addForms;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import pages.BasePage;

@Slf4j
public class fluidFormPage extends BasePage {

    public fluidFormPage() {
        UIActions.waitForSpinnerToDisappear();
    }

    private By possibilityContinues = By.xpath("//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Continuous ']");
    private By possibilityTimeLimit = By.xpath("//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Time Limit ']");


}
