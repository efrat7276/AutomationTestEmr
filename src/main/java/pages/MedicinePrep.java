package pages;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
public class MedicinePrep {
    private final By buttonPrintAllBy = By.xpath("//div[contains(@class,'text-left mb_10')]//button[1][contains(@class, 'btn-secondary')]");
    public MedicinePrep() {
        UIActions.waitForSpinnerToDisappear();
    }
    public void clickPrintAllButton() {
        UIActions.click(buttonPrintAllBy);
    }
    public boolean isMedicinePrepDisplayed() {
        if(UIActions.findElementWithWait(buttonPrintAllBy) != null) {
            boolean isDisplayed = UIActions.findElementWithWait(buttonPrintAllBy).isDisplayed();
            if (isDisplayed) {
                log.info("medicine prep - is displayed.");
            }
            return isDisplayed;
        }
        log.error("medicine prep - is NOT displayed.");
        return false;
    }
}
