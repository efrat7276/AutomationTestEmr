package pages;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import pages.BasePage;

@Slf4j
public class DrugsSection extends BasePage {

    public DrugsSection() {
        UIActions.waitForSpinnerToDisappear();
    }

    private final By btns_addDrug = By.id("btnAddMedicine");
    private final By btn_importDrug = By.id("btnImportMedicine");
    private final By btn_addDilution = By.id("btnDilution");


}

