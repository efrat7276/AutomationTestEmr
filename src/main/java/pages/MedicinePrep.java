package pages;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MedicinePrep {

    Logger logger = LoggerFactory.getLogger(MedicinePrep.class);
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
                logger.info("medicine prep - is displayed.");
            }
            return isDisplayed;
        }
        logger.error("medicine prep - is NOT displayed.");
        return false;
    }
}
