package pages;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import pages.addForms.DrugFormPage;
import pages.addForms.GeneralInstructionPage;
import pages.addForms.BloodProductsPage;

import static org.testng.Assert.assertTrue;


public class DoctorInstructionPage extends BasePage{


    DrugFormPage drugForm =new DrugFormPage();
    GeneralInstructionPage generalInstructionForm = new GeneralInstructionPage();
    BloodProductsPage bloodForm = new BloodProductsPage();
    //title locator
    private By doctorInstructionsTitle = By.xpath("//span[contains(@class,'lan-title-ng-view-Hierarchy-a') and text()='הוראות רופא']");

    // Locator ל-span השני של הכותרת
    private By getSecondTitleSpanLocator() {
        return By.xpath("(//span[contains(@class,'lan-title-ng-view-Hierarchy-a')])[2]");
    }

    // בדיקה אם הטקסט בצד השני של הכותרת מתאים
    public boolean isSecondTitleDisplayed(InstructionType type) {
        String actualText = UIActions.getText(getSecondTitleSpanLocator()).trim();
        System.out.println("DEBUG: actualText = '" + actualText + "' | expected = '" + type.getDescription() + "'");
        return actualText.contains(type.getDescription());
    }

    // Assert עם הודעת שגיאה ברורה
    public void verifySecondTitle(InstructionType type) {
        String actualText = UIActions.getText(getSecondTitleSpanLocator()).trim();
        if (!isSecondTitleDisplayed(type)) {
            throw new AssertionError("Expected title to contain '" + type.getDescription() + "' but got '" + actualText + "'");
        }
    }
    // buttons
    private By btnAddMedicine = By.id("btnAddMedicine");               // תרופה
    private By btnAddGeneralInstruction = By.xpath("//section[@id='generalIns']//button"); // הוראה כללית
    private By btnAddNutrition = By.xpath("//section[@id='nutrition']//button[@id='btnAddMedicine']"); // תזונה
    private By btnAddBloodProduct = By.xpath("//section[@id='blood-product']//button"); // מוצר דם
    private By btnImportMedicine = By.id("btnImportMedicine");          // יבוא תרופה
    private By btnAddFluid = By.id("btnAddMedicine");                      // נוזל / דילול
    private By btn_approvalDrug = By.id("approvalDrug");

    /**
     * click add medicine drug
     */
    public void clickButtonAddInstruction(InstructionType type) {
        switch (type) {
            case MEDICINE:
                UIActions.click(btnAddMedicine);
                break;
            case BLOOD:
                UIActions.click(btnAddBloodProduct);
                break;
            case FLUID:
                UIActions.click(btnAddFluid);
                break;
            case GENERAL:
                UIActions.click(btnAddGeneralInstruction);
                break;
            case NUTRITION:
                UIActions.click(btnAddMedicine);
                break;
            case IMMEDIATE:
          //      UIActions.click(btns_addImmediate);
                break;
            case TREATMENT_PROTOCOL:
           //     UIActions.click(btns_addTreatmentProtocol);
                break;
            case MEDICINE_PROTOCOL:
          //      UIActions.click(btns_addMedicineProtocol);
                break;
            case IMPORT_MEDICINE:
         //       UIActions.click(btns_importMedicine);
                break;
            case OTC:
           //     UIActions.click(btns_addOTC);
                break;
        }

        // בדיקת הכותרת הדינמית אחרי לחיצה
        verifySecondTitle(type);
    }


    // פונקציות Full Action – דוגמה למספר סוגי הוראות
    public void addMedicineFull(String name, String frequency, String dose, String amount, String username, String password) {
        clickButtonAddInstruction(InstructionType.MEDICINE);

        drugForm.addOneMedicine(name, frequency, dose, null, amount, null, null, null, null, null, null);
        approveAndVerifyInstructions(username, password);
    }

    public void addFluidFull(String name, String frequency, String dose, String amount, String username, String password) {
        clickButtonAddInstruction(InstructionType.FLUID);
        drugForm.addFluid(name, frequency, dose, amount);
        approveAndVerifyInstructions(username, password);
    }

    public void addBloodProductFull(String name, String amount, String username, String password) {
        clickButtonAddInstruction(InstructionType.BLOOD);
       bloodForm.addBloodProduct(name, amount); // לדוגמה, פונקציה ב-BloodForm
        approveAndVerifyInstructions(username, password);
    }

    public void addGeneralInstructionFull(String instructionText, String username, String password) {
        clickButtonAddInstruction(InstructionType.GENERAL);
     //  generalInstructionForm.addGeneralInstruction(instructionText);
        approveAndVerifyInstructions(username, password);
    }


    /**
     * sign all the instruction
     */
    public void clickButtonSign(){
        UIActions.click(btn_approvalDrug);
        //  assertTrue(UIActions.findElement())
    }

    public void approvalAllInstruction(String username, String password){
     clickButtonSign();
     userSignModalPage.signModal(username, password);
    }

    public void approveAndVerifyInstructions(String username, String password) {
        approvalAllInstruction(username, password);
        verifyDoctorApproval();
    }

    public void verifyDoctorApproval(){
        assertTrue(driver.findElement(btn_approvalDrug).getText().contains("0"));
    }

}
