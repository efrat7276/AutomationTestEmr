package pages.addForms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import pages.BasePage;

import java.util.List;
@Slf4j
public class GeneralInstructionPage extends BasePage {

    public GeneralInstructionPage() {
        UIActions.waitForSpinnerToDisappear();
    }

    private By generalInsMenu = By.tagName("general-ins-menu");
    private By generalInstructionCategoryList = By.xpath("//ngb-accordion/div//button");
    private By generalInsSubList = By.xpath("//ngb-accordion//input");
    private By listSelectedGeneralIns = By.xpath("//div[@class='main-box']/ul/li");
    private By btn_possbilities = By.xpath("//div[@class='main-box']//label[text()='תדירות']/following-sibling::button[contains(@class,'dropdown-toggle')]");
    private By possbilityList = By.xpath("//div[@class='main-box']//label[text()='תדירות']/following-sibling::div[contains(@class,'dropdown')]/button");
    private By btn_save = By.xpath("//button[contains(@class,'btn-submit')]");
    private By btn_clear = By.xpath("//button[@class='btn btn-defualt btn-cancel btn-click '][0]");
    private By btn_back = By.xpath("//button[@class='btn btn-defual']");

    private By duplicateInstructionModal = By.xpath("//duplicate-instruction-modal");
    private By duplicateInstructionModalConfirmButton = By.xpath("//duplicate-instruction-modal//button[@id='buttonImport']");
    // כפתור ביצוע בתוך טופס התרופה (מנוסח באופן גנרי לפי טקסט)
    public void addGeneralInstructionAndClose() {
        UIActions.click(generalInstructionCategoryList);
        UIActions.click(generalInsSubList);
        if(UIActions.isPopupAppeared(duplicateInstructionModal))
          UIActions.click(duplicateInstructionModalConfirmButton);

        UIActions.click(btn_possbilities);
         UIActions.selectFromList(possbilityList, "Once Only");
        UIActions.click(btn_save);
        log.info("General instruction added and form closed.");
    }
}
