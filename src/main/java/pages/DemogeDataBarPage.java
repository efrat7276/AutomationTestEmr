package pages;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import pages.BasePage;

import java.util.List;

@Slf4j
public class DemogeDataBarPage extends BasePage {

    public DemogeDataBarPage() {
        UIActions.waitForSpinnerToDisappear();
    }

    private final By btn_collapse_patient_deatiles = By.xpath("//demog-data-bar/div/div[1]/div/div[2]/div/div[2]/div[2]/i");
    private final By sherut_label = By.xpath("//span[@class='value-label']");
    private final By sherut_number = By.xpath("//div[@class='line-number']/span[2]");
    private final By labels_value = By.xpath("//span[@class='value-label ']");

    //::following-sibling::brother
}
