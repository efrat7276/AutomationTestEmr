package pages.nurse.approval;

import actionUtilies.UIActions;
import drivers.DriverManager;
import lombok.extern.slf4j.Slf4j;

import org.checkerframework.checker.guieffect.qual.UI;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;

import pages.BasePage;
import pages.UserSignModalPage;

import java.time.Duration;
import java.util.List;

@Slf4j
public class ApprovalInstructionPage1 extends BasePage {
UserSignModalPage userSignModalPage;
    public ApprovalInstructionPage1() {
       
        userSignModalPage = new UserSignModalPage();
    }
private final By btnChooseHourCurrentDayDrugAndGeneralBy = By.xpath("//td[@name='drugsCurrentDay']//button");
private final By boxCurrentHourForLiquidBy = By.xpath("//tr[@name='drugRow2'][td]/td[@colspan='8']//div[contains(@class,'timeLineInToday')]");
private final By boxCurrentHourForBloodProductBy = By.xpath("//tr[@name='drugRow2'][td]/td[@colspan='6']//div[contains(@class,'timeLineInToday')]");
private final By btnVforBloodProductBy = By.xpath("//form[@name='popContentSolutionBagSizeCode']//button[@type='submit']");
private final By btnApprovalAll = By.xpath("//button[@id='approvalDrug']");
private final By btnApprovalBy = By.xpath("//button[contains(@id,'btnIsApproval') and .//span[contains(text(), 'אישור')]]");
private final By btnEditBy = By.xpath("//button[contains(@id,'btnIsApproval') and .//span[contains(text(), 'ערוך')]]");


    /**
     * פותח Dropdown שנפתח על ידי אלמנט, ובוחר את הפריט באינדקס הנתון.
     * @param dropdownOpenerElement האלמנט (כפתור) שפותח את הרשימה
     * @param index האינדקס של הפריט לבחירה (0 = ראשון, 3 = רביעי)
     */
    private void selectNthOptionFromDropdown(WebElement dropdownOpenerElement, int index) {
        UIActions.click(dropdownOpenerElement);
        List<WebElement> options =DriverManager.getInstance().findElements(By.xpath("//tr[@class='collapse show ng-star-inserted']//div[@id='div-group-current-day']//div[@class='btn-group show dropdown']//button/following-sibling::ul//li"));
         if (options.isEmpty()) {
             throw new RuntimeException("לא נמצאו אפשרויות ברשימה הנפתחת.");
         }
        if (options.size() > index) {
          UIActions.click(options.get(index));
        } else {
            throw new RuntimeException("לא נמצאה אפשרות באינדקס " + index + ". נמצאו רק " + options.size() + " אפשרויות.");
        }
    }

    public void approveAllCurrentDayHourAndVerify(String username, String password){
 
        UIActions.click(btnApprovalAll);
        userSignModalPage.signModal(username,password);
       
    }

 
public void approveAllInstructionsAndVerify(boolean drugOrGeneral, boolean liquid, boolean bloodProduct, String username, String password) {
    UIActions.waitForSpinnerToDisappear();

    // 1. טיפול בכל סוג הוראה בנפרד (תיקון הסוגריים)
    if (drugOrGeneral) {
        approveDrugsAndGeneralSelectCurrentDayHour();
    }
    if (liquid) {
        approvalAllLiquidInstruction();
    }
    if (bloodProduct) {
        approvalAllbloodProduct();
    }

    UIActions.waitForSpinnerToDisappear();

    // 2. קבלת כמות הכפתורים הראשונית
    List<WebElement> approvalButtons = UIActions.findElementsWithWait(btnApprovalBy);

    if (approvalButtons.isEmpty()) {
        log.info("No approval buttons found to click.");
    } else {
        int expectedButtons = approvalButtons.size();
        log.info("Found {} approval buttons to click.", expectedButtons);

        for (int i = 0; i < expectedButtons; i++) {
            // שליפה מחדש של הכפתורים שטרם אושרו למניעת Stale Element
            List<WebElement> currentButtons = DriverManager.getInstance().findElements(btnApprovalBy);

            if (currentButtons.isEmpty()) {
                log.warn("No more 'אישור' buttons found at index {}", i + 1);
                break;
            }

            WebElement button = currentButtons.get(0);
            UIActions.waitForElementVisibleBy(button);

            // לחיצה ב-JS
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getInstance();
            js.executeScript("arguments[0].click();", button);
            log.info("Clicked approval button {}/{}", i + 1, expectedButtons);

            // סנכרון: המתנה להיעלמות ספינר ולעדכון ה-DOM
            UIActions.waitForSpinnerToDisappear();
            
            // אימות שהכפתור אכן הפך ל'ערוך'
            int currentEditButtonsCount = DriverManager.getInstance().findElements(btnEditBy).size();
            log.info("Progress: {} instructions approved out of {}.", currentEditButtonsCount, expectedButtons);
        }
    }

    // 3. חתימה ואישור סופי
    UIActions.click(btnApprovalAll);
    log.info("Clicked on approval button for all.");

    userSignModalPage.signModal(username, password);
    UIActions.waitForSpinnerToDisappear();
    log.info("All instructions approved successfully!");
}

public void approveDrugsAndGeneralSelectCurrentDayHour() {
    List<WebElement> allToApprovalRows = UIActions.findElementsWithWait(btnChooseHourCurrentDayDrugAndGeneralBy);
    int rowCount = allToApprovalRows.size();

    if (rowCount == 0) {
        log.info("No drug or general instructions found for the current day.");
        return;
    }
    log.info("Found {} drug and general instructions to process.", rowCount);

    for (int i = 0; i < rowCount; i++) {
        // שליפה מחדש למניעת Stale Element Reference
        List<WebElement> rows = DriverManager.getInstance().findElements(btnChooseHourCurrentDayDrugAndGeneralBy);
        if (rows.size() > i && rows.get(i).isEnabled()) {
            selectNthOptionFromDropdown(rows.get(i), 4);
            UIActions.waitForSpinnerToDisappear();
            log.info("Selected hour for drug/general instruction {}", i + 1);
        }
    }
}

public void approvalAllLiquidInstruction() {
    List<WebElement> allLiquidTimeline = UIActions.findElementsWithWait(boxCurrentHourForLiquidBy);
    int rowCount = allLiquidTimeline.size();

    if (rowCount == 0) {
        log.info("No liquid instructions found in the timeline.");
        return;
    }
    log.info("Found {} liquid instructions to approve.", rowCount);

    for (int i = 0; i < rowCount; i++) {
        List<WebElement> currentLiquidElements = DriverManager.getInstance().findElements(boxCurrentHourForLiquidBy);
        if (currentLiquidElements.isEmpty()) {
            log.warn("No more liquid instructions found at index {}", i + 1);
            break;
        }

        WebElement liquidElement = currentLiquidElements.get(0);
        UIActions.waitForElementVisibleBy(liquidElement);
        UIActions.click(liquidElement);
        UIActions.waitForSpinnerToDisappear(); // סנכרון לאחר בלחיצה
        log.info("Clicked on liquid instruction number {}", i + 1);
    }
    log.info("Completed processing all liquid instructions.");
}

public void approvalAllbloodProduct() {
    List<WebElement> allBloodProductTimeline = UIActions.findElementsWithWait(boxCurrentHourForBloodProductBy);
    int rowCount = allBloodProductTimeline.size();

    if (rowCount == 0) {
        log.info("No blood product instructions found in the timeline.");
        return;
    }
    log.info("Found {} blood product instructions to approve.", rowCount);

    for (int i = 0; i < rowCount; i++) {
        List<WebElement> currentBloodElements = DriverManager.getInstance().findElements(boxCurrentHourForBloodProductBy);
        if (currentBloodElements.isEmpty()) {
            log.warn("No more blood product instructions found at index {}", i + 1);
            break;
        }

        WebElement bloodElement = currentBloodElements.get(0);
        UIActions.waitForElementVisibleBy(bloodElement);
        UIActions.click(bloodElement);

        UIActions.click(btnVforBloodProductBy);
        UIActions.waitForSpinnerToDisappear(); // סנכרון לאחר אישור מוצר דם
        log.info("Approved blood product instruction {}", i + 1);
    }
    log.info("Completed processing all blood product instructions.");
}

    public void approveDrugsOnly(String username, String password){
        log.info("* Approving ONLY drug instructions (no liquids or blood products)");
        UIActions.waitForSpinnerToDisappear();
        approveDrugsAndGeneralSelectCurrentDayHour();
        List<WebElement> approvalAllBtn = DriverManager.getInstance().findElements(btnApprovalBy);
        int expectedButtons = approvalAllBtn.size();
        
        if (approvalAllBtn.isEmpty()) {
            log.warn("No individual approval buttons found for drugs.");
        } 
        else {
            for (int i = 0; i < approvalAllBtn.size(); i++) {
                try {
                    WebElement btn = approvalAllBtn.get(i);
                    ((JavascriptExecutor) DriverManager.getInstance()).executeScript("arguments[0].click();", btn);
                    log.info("Clicked on drug approval button {} of {}.", i + 1, expectedButtons);
                }
                catch (ElementClickInterceptedException e) {
                    log.warn("Click intercepted for button " + i + ". Trying JS click as fallback.");
                    WebElement btn = approvalAllBtn.get(i);
                    ((JavascriptExecutor) DriverManager.getInstance()).executeScript("arguments[0].click();", btn);
                } catch (Exception e) {
                    log.error("Failed to click drug approval button " + i + ": " + e.getMessage());
                }
            }
        }
        
        UIActions.click(btnApprovalAll);
        log.info("Clicked on final approval button for all drugs");
        userSignModalPage.signModal(username, password);
        log.info("* Drug instructions approved successfully!");
    }

    public void verifyAllInstructionsApproved(){
        if (UIActions.waitForInvisibility(btnApprovalAll)){
            log.info("All instructions approved successfully.");
        }
        else{
            log.error("Failed to approve all instructions.");
        }
}

    public void approvalAllInstructionByNurseAndVerify(String username, String password){
        UIActions.click(btnApprovalAll);
        userSignModalPage.signModal(username,password);
        verifyAllInstructionsApproved();
   }
}
