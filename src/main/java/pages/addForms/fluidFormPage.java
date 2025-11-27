package pages.addForms;

import org.openqa.selenium.By;
import pages.BasePage;

public class fluidFormPage extends BasePage {

    private By possibilityContinues = By.xpath("//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Continuous ']");
    private By possibilityTimeLimit = By.xpath("//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Time Limit ']");


}
