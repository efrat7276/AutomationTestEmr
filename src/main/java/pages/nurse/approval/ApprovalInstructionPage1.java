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
import org.openqa.selenium.support.ui.WebDriverWait;
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
private final  By btnApprovalBy = By.xpath("//button[contains(@id,'btnIsApproval')]");
private final By tabInstructionForApprovalBy = By.xpath("//li[@id='ngbNav_patient_sheet1']//span[2]");
private final By btnApprovalAll = By.xpath("//button[@id='approvalDrug']");

public void approveDrugsAndGeneralSelectCurrentDayHour(){
    List<WebElement> allToApprovalRows = UIActions.findElementsWithWait(btnChooseHourCurrentDayDrugAndGeneralBy);
    int rowCount = allToApprovalRows.size();
    for (WebElement currentRow : allToApprovalRows) {
        if(UIActions.isExist(currentRow) && currentRow.isEnabled())
          { 
            selectNthOptionFromDropdown(currentRow, 4);
            log.info("selected the first hour in current day for  drug and general instructions.");
          }  
    }
    log.info("Selected the first hour in current day for {} drug and general instructions", rowCount);
}

 public void approvalAllLiquidInstruction(){  {
        List<WebElement> allLiquidTimeline = UIActions.findElementsWithWait(boxCurrentHourForLiquidBy);
        int rowCount = allLiquidTimeline.size();
        for (int i = 0; i < allLiquidTimeline.size(); i++) {
        try {
                if (!allLiquidTimeline.isEmpty()) {
                    allLiquidTimeline.get(i).click();
                    log.info("Clicked on timeline entry for liquid instruction in row {}.", i + 1);
                }
            } catch (Exception e) {
                log.error("Error processing liquid instruction in timeline row {}: {}", i + 1, e.getMessage());
                continue;
            }
        }
        log.info("Clicked on all liquid instruction timelines");
}

     }
     
    public void approvalAllbloodProduct(){
        List<WebElement> allBloodProductTimeline = UIActions.findElementsWithWait(boxCurrentHourForBloodProductBy);

        if (allBloodProductTimeline.isEmpty()) {
           log.info("No blood product instructions found in the timeline.");     
            return;
        }
        for (int i = 0; i < allBloodProductTimeline.size(); i++) {
            try {
                if (!allBloodProductTimeline.isEmpty()) {
                    allBloodProductTimeline.get(0).click();
                    log.info("Clicked on timeline entry for blood product instruction in row {}.", i + 1);
                    UIActions.click(btnVforBloodProductBy);
                }
            } catch (Exception e) {
                log.error("Error processing blood product instruction in timeline row {}: {}", i + 1, e.getMessage());
                continue;
            }
        }
        log.info("Clicked on all blood product instruction timelines and approved them.");
     }

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

    public void approveAllInstructionsAndVerify(boolean drugOrGeneral, boolean liquid, boolean bloodProduct, String username, String password){
     UIActions.waitForSpinnerToDisappear();
     if (drugOrGeneral) {
         approveDrugsAndGeneralSelectCurrentDayHour();
     }
     if (liquid) {
         approvalAllLiquidInstruction();
     }
     if (bloodProduct) {
         approvalAllbloodProduct();
     }

    List<WebElement> approvalAllBtn = UIActions.findElementsWithWait(btnApprovalBy);
     log.info("Found {} approval buttons to click.", approvalAllBtn.size());
    UIActions.waitForElementClickable(approvalAllBtn.get(0));
   int expectedButtons = approvalAllBtn.size(); 
   int index = 0;
    log.info("Waiting for {} approval buttons to be clickable.", expectedButtons);
    if(expectedButtons==0)   
        {log.info("No instructions found to renew.");
         return;
      }
        else {
            log.info("Found {} instructions to renew.", expectedButtons);
        }
    do {
    List<WebElement> allApprovalBtn = UIActions.findElementsWithWait(btnApprovalBy);
    if (!allApprovalBtn.get(0).isSelected()) {
        UIActions.waitForElementClickable(allApprovalBtn.get(0));
        WebElement button = allApprovalBtn.get(0);
        WebDriver driver = DriverManager.getInstance();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", button);
        
        log.info("Clicked approval button at index: {}", index);
    }
    index++;
    if (expectedButtons == 1) {
        break;
    }
    expectedButtons = UIActions.findElementsWithWait(btnApprovalBy).size();
} while (expectedButtons > 0);
   
     UIActions.click(btnApprovalAll);
     userSignModalPage.signModal(username,password);
     UIActions.waitForSpinnerToDisappear();
     String text = DriverManager.getInstance().findElement(tabInstructionForApprovalBy).getText();
     Assert.assertTrue(text.contains("0"), "Some instructions were not approved. Remaining count: " + text);
     log.info("Clicked on approval button for all"); 
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
