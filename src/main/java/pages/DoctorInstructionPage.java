package pages;

import actionUtilies.UIActions;
import helpers.Constants;

import org.checkerframework.checker.units.qual.g;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;

import pages.addForms.DrugFormPage;
import pages.addForms.GeneralInstructionPage;
import pages.addForms.BloodProductsPage;
import actionUtilies.UIActions;
import static org.testng.Assert.assertTrue;

import java.util.List;


public class DoctorInstructionPage extends BasePage{

  
   protected static final Logger logger = LoggerFactory.getLogger(DoctorInstructionPage.class);
       
       DrugFormPage drugForm = new DrugFormPage();
        GeneralInstructionPage generalInstructionForm = new GeneralInstructionPage();
        BloodProductsPage bloodForm = new BloodProductsPage();
        public DoctorInstructionPage() {
        UIActions.waitForSpinnerToDisappear();
    }

    


    //title locator
    private By doctorInstructionsTitle = By.xpath("//span[contains(@class,'lan-title-ng-view-Hierarchy-a') and text()='הוראות רופא']");

    // Locator ל-span השני של הכותרת
    private By getSecondTitleSpanLocator() {
        return By.xpath("(//span[contains(@class,'lan-title-ng-view-Hierarchy-a')])[2]");
    }

    // בדיקה אם הטקסט בצד השני של הכותרת מתאים
    public boolean isSecondTitleDisplayed(InstructionType type) {
        String actualText = UIActions.getText(getSecondTitleSpanLocator()).trim();
        logger.debug("DEBUG: actualText = '{}' | expected = '{}'", actualText, type.getDescription());
        return actualText.contains(type.getDescription());
    }

    // Assert עם הודעת שגיאה ברורה
    public void verifySecondTitle(InstructionType type) {
        if(type == InstructionType.NUTRITION)
            type = InstructionType.MEDICINE;
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
    private By chekBoxList = By.xpath("//*[@id='Renew']//label[input[@type='checkbox']]"); // כל ה-checkbox של חידוש הוראות



    /**
     * click add medicine drug
     */
    public void clickButtonAddInstruction(InstructionType type) {
        logger.info("Clicking 'Add' button for instruction type: {}", type);
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
                UIActions.click(btnAddNutrition);
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
    public void addMedicineFullAndVerify(String name, String frequency, String dose, String amount, String username, String password) {
      logger.info("Attempting to add medicine with details - Name: {}, Frequency: {}, Dose: {}, Amount: {}", name, frequency, dose, amount);
        clickButtonAddInstruction(InstructionType.MEDICINE);
        drugForm.addOneMedicine(name, frequency, dose, amount, null, null, null, null, null, null, false);
        approveAndVerifyInstructions(username, password);
    }

    public void addFluidFull(String name, String frequency, String dose, String amount, String username, String password) {
        clickButtonAddInstruction(InstructionType.FLUID);
        drugForm.addFluid(name, frequency, dose, amount);
        approveAndVerifyInstructions(username, password);
    }

       public void addNutritionFull(String name, String frequency, String dose, String amount, String username, String password) {
        clickButtonAddInstruction(InstructionType.NUTRITION);
        // For nutrition daily, "amount" represents times per day. Map it to timesDaily.
        drugForm.addOneMedicine(name, frequency, dose, amount, null, null, null, null, null, null, false);
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


    public void approveAndVerifyInstructions(String username, String password) {
        logger.info("Approving instructions with username: {}", username);
        clickButtonSign();
        userSignModalPage.signModal(username, password);
        UIActions.waitForVisible(btn_approvalDrug);
        verifyDoctorApproval();
    }
   public void renewAllInstructions() {
    logger.info("Renewing all instructions...");
     int index=0;
    //לבדוק בכלל אם יש רשימה ולא הכול
    int count = UIActions.findElementsWithWait(chekBoxList).size();
    do{
     List<WebElement> checkBoxes = UIActions.findElementsWithWait(chekBoxList);
      WebElement cb =  checkBoxes.get(0);
        if (!cb.isSelected()) {
            UIActions.waitForElementClickable(cb);
            UIActions.clickWithRetry(cb, chekBoxList);
   
            index++;
            if(count==1)
                break;
        } 
     count = UIActions.findElementsWithWait(chekBoxList).size();
     }
     while(count>0);
     approveAndVerifyInstructions(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    public void verifyDoctorApproval() {
         if (UIActions.getText(btn_approvalDrug).contains("0")) {
            logger.info("All instructions approved successfully.");
            assertTrue(true);
        } else {
            logger.error("Some instructions were not approved.");
            assertTrue(false);
    }
    }
}
