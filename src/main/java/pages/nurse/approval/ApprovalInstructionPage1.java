package pages.nurse.approval;

import actionUtilies.UIActions;
import drivers.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
private By btnChooseHourCurrentDayDrugAndGeneralBy = By.xpath("//tr[@class='collapse show ng-star-inserted']//div[@id='div-group-current-day']//button");
private final By timesHourBy = By.xpath("//tr[@class='collapse show ng-star-inserted']//div[@id='div-group-current-day']//button/following-sibling::ul");

private final By timelineLiquidBy = By.xpath("//tr[@name='drugRow2'][td]/td[@colspan='8']//div[contains(@class,'timeLineInToday')]");
private final By timelineBloodProductBy = By.xpath("//tr[@name='drugRow2'][td]/td[@colspan='6']//div[contains(@class,'timeLineInToday')]");
private final By btnVforBloodProductBy = By.xpath("//form[@name='popContentSolutionBagSizeCode']//button[@type='submit']");
private final By btnApprovalBy = By.xpath("//button[contains(@id,'btnIsApproval')]");

private final By btnApprovalAll = By.xpath("//button[@id='approvalDrug']");

public void approveDrugsAndGeneralSelectCurrentDayHour(){
    List<WebElement> allToApprovalRows = UIActions.findElementsWithWait(btnChooseHourCurrentDayDrugAndGeneralBy);
    for (WebElement currentRow : allToApprovalRows) {
         selectNthOptionFromDropdown(currentRow, 4); // 3 = רביעי
         log.info("Selected the fourth current day hour for drug instruction.");
  
    }
}
  

 public void approvalAllLiquidInstruction(){
        List<WebElement> allLiquidTimeline = UIActions.findElementsWithWait(timelineLiquidBy);

        if (allLiquidTimeline.isEmpty()) {
           log.info("No liquid instructions found in the timeline.");     
            return;
        }
        log.info("Found {} liquid instructions in the timeline.", allLiquidTimeline.size());
        for (int i = 0; i < allLiquidTimeline.size(); i++) {
        WebElement currentTimelineRow = allLiquidTimeline.get(i);
        try {
                currentTimelineRow.findElements(By.xpath("//div[contains(@class,'timeLineInToday ')]")).get(i).click();
                    log.info("Clicked on timeline entry for liquid instruction in row {}.", i + 1);
        
                    // log.info("Clicked approve for liquid instruction in timeline row {}.", i + 1); 
            } catch (Exception e) {
                log.error("Error processing liquid instruction in timeline row {}: {}", i + 1, e.getMessage());
                continue;
            }
        }
     }

     
    public void approvalAllbloodProduct(){
        List<WebElement> allBloodProductTimeline = UIActions.findElementsWithWait(timelineBloodProductBy);

        if (allBloodProductTimeline.isEmpty()) {
           log.info("No blood product instructions found in the timeline.");     
            return;
        }
        log.info("Found {} blood product instructions in the timeline.", allBloodProductTimeline.size());
        for (int i = 0; i < allBloodProductTimeline.size(); i++) {
            WebElement currentTimelineRow = allBloodProductTimeline.get(i);
            try {
                currentTimelineRow.findElements(By.xpath("//tr[@name='drugRow2'][td]/td[@colspan='6']//div[contains(@class,'timeLineInToday ')]")).get(i).click();
                    log.info("Clicked on timeline entry for blood product instruction in row {}.", i + 1);
                    UIActions.click(btnVforBloodProductBy);
                    // log.info("Clicked approve for blood product instruction in timeline row {}.", i + 1); 
            } catch (Exception e) {
                log.error("Error processing blood product instruction in timeline row {}: {}", i + 1, e.getMessage());
                continue;
            }
        }
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
         log.info("Attempting to select option at index {}.", index);
        if (options.size() > index) {
          UIActions.click(options.get(index));
            log.info("Selected option at index {} successfully.", index);
        } else {
            throw new RuntimeException("לא נמצאה אפשרות באינדקס " + index + ". נמצאו רק " + options.size() + " אפשרויות.");
        }
    }

    public void approveAllCurrentDayHourAndVerify(String username, String password){
 
        UIActions.click(btnApprovalAll);
        userSignModalPage.signModal(username,password);
       
    }

    public void approveAllInstructions(String username, String password){
     UIActions.waitForSpinnerToDisappear();
     approveDrugsAndGeneralSelectCurrentDayHour();
     approvalAllLiquidInstruction();
      approvalAllbloodProduct();
    WebDriverWait wait = new WebDriverWait(DriverManager.getInstance(), Duration.ofSeconds(10));
    List<WebElement> approvalAllBtn = DriverManager.getInstance().findElements(btnApprovalBy);
    if (approvalAllBtn.isEmpty()) {
        log.warn("Approval All button is not displayed after processing individual instructions.");
    } 
    else {
        for (int i = 0; i < approvalAllBtn.size(); i++) {
            try {
                WebElement btn = DriverManager.getInstance().findElements(btnApprovalBy).get(i);
                ((JavascriptExecutor) DriverManager.getInstance()).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
                wait.until(ExpectedConditions.elementToBeClickable(btn));
                btn.click();
                log.info("Clicked on approval button #" + (i + 1));
            }
            catch (ElementClickInterceptedException e) {
                log.warn("Click intercepted for button " + i + ". Trying JS click as fallback.");
                WebElement btn = DriverManager.getInstance().findElements(btnApprovalBy).get(i);
                ((JavascriptExecutor) DriverManager.getInstance()).executeScript("arguments[0].click();", btn);
            } catch (Exception e) {
                log.error("Failed to click button " + i + ": " + e.getMessage());
            }
        }
    }
        
     UIActions.click(btnApprovalAll);
     userSignModalPage.signModal(username,password);
     verifyAllInstructionsApproved();
       

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
       //
   }
}
