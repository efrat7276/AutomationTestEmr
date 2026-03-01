package pages.addForms;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import pages.BasePage;

public class BloodProductsPage extends BasePage {

    // Locators copied from previous BloodProducts helper
    private By bloodProductList = By.xpath("//button[@id='btnbloodProductList']/following-sibling::ul/li");
    private By btn_bloodProductList = By.id("btnbloodProductList");

    private By solutionBagSizeList = By.xpath("//button[@id='solutionBagSizeCodeList']/following-sibling::ul/li");
    private By btn_solutionBagSizeList = By.id("solutionBagSizeCodeList");

    private By input_amount = By.name("solutionBagSize");

    private By dropdownDrugUnitMeasureList = By.xpath("//button[@id='dropdownDrugUnitMeasure']/following-sibling::ul/li");
    private By btn_dropdownDrugUnitMeasure = By.id("dropdownDrugUnitMeasure");

    private By inp_comment = By.id("drugComment");

    // Reuse common add buttons used in other forms
    private By btn_add = By.id("btnAdd");
    private By btn_addAndClose = By.id("btnAddAndClose");

    // Open product dropdown and select
    public void selectBloodProduct(String productName) {
        UIActions.click(btn_bloodProductList);
        UIActions.selectFromList(bloodProductList, productName);
    }

    public void selectSolutionBagSize(String sizeDescription) {
        UIActions.click(btn_solutionBagSizeList);
        UIActions.selectFromList(solutionBagSizeList, sizeDescription);
    }

    public void setAmount(String amount) {
        UIActions.clearText(input_amount);
        UIActions.typeText(input_amount, amount);
    }

    public void selectUnitMeasure(String unit) {
        UIActions.click(btn_dropdownDrugUnitMeasure);
        UIActions.selectFromList(dropdownDrugUnitMeasureList, unit);
    }

    public void setComment(String comment) {
        UIActions.typeText(inp_comment, comment);
    }

    /**
     * High level method used by DoctorInstructionPage to add a blood product.
     * This fills basic required fields and clicks 'Add and Close'.
     */
    public void addBloodProduct(String productName, String amount) {
        // small wait for dropdown to load if needed
        try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        selectBloodProduct(productName);
        setAmount(amount);
        setComment("בדיקות אוטומטית - הוספת מוצר דם");
        // finalize
        UIActions.click(btn_addAndClose);
    }

}
