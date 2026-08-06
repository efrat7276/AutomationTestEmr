package pages.nurse.prepMedicine;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

@Slf4j
public class prepMedcinePage extends BasePage {

    public prepMedcinePage() {
        UIActions.waitForSpinnerToDisappear();
    }

    private final By filterHourBtn = By.xpath("//medicine-prep-filter//div/label[text()='שעה']/parent::div");
    private final By filterHourList = By.xpath("//medicine-prep-filter//div/label[text()='שעה']/parent::div//ul/p-dropdownitem/li");

}
