package pages.addForms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import actionUtilies.UIActions;

import java.util.List;

public class GeneralInstructionPage {

    public By generalInsMenu = By.tagName("general-ins-menu");
    public By generalInstructionCategoryList = By.xpath("//ngb-accordion/div//button");
    public By generalInsSubList = By.xpath("//ngb-accordion//input");
    public By listSelectedGeneralIns = By.xpath("//div[@class='main-box']/ul/li");
    public By btn_possbilities = By.xpath("//div[@class='main-box']//label[text()='תדירות']/following-sibling::button[contains(@class,'dropdown-toggle')]");
    public By possbilityList = By.xpath("//div[@class='main-box']//label[text()='תדירות']/following-sibling::div[contains(@class,'dropdown')]/button");
    public By btn_numberOfTime = By.xpath("//div[@class='main-box']//label[text()='פעמים']/following-sibling::button");
    public By numberOfTimeList = By.xpath("//div[@class='main-box']//label[text()='פעמים']/following-sibling::div[contains(@class,'dropdown')]/button");
    public By btn_save = By.xpath("//button[contains(@class,'btn-submit')]");
    public By btn_clear = By.xpath("//button[@class='btn btn-defualt btn-cancel btn-click '][0]");
    public By btn_back = By.xpath("//button[@class='btn btn-defual']");

    private By duplicateInstructionModal = By.xpath("//duplicate-instruction-modal");
    private By duplicateInstructionModalConfirmButton = By.xpath("//duplicate-instruction-modal//button[@id='buttonImport']");
    // כפתור ביצוע בתוך טופס התרופה (מנוסח באופן גנרי לפי טקסט)
    public void addGeneralInstructionAndClose() {
        UIActions.click(generalInstructionCategoryList);
        UIActions.click(generalInsSubList);
        if(UIActions.waitForVisible(duplicateInstructionModal))
          UIActions.click(duplicateInstructionModalConfirmButton);

        UIActions.click(btn_possbilities);
         UIActions.selectFromList(possbilityList, "Once Only");
        UIActions.click(btn_save);
    }
}
