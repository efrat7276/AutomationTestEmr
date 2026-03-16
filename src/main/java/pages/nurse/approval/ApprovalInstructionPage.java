package pages.nurse.approval;

import actionUtilies.UIActions;
import drivers.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pages.BasePage;
import pages.UserSignModalPage;

import java.util.List;

@Slf4j
public class ApprovalInstructionPage extends BasePage {
UserSignModalPage userSignModalPage;
    public ApprovalInstructionPage() {
       
        userSignModalPage = new UserSignModalPage();
    }
  //Drug instruction listHours locators
    private By btnChooseHourCurrentDay = By.xpath("//following-sibling::tr[@name='drugRow2']//td[@name='drugsCurrentDay']//button[@ngbdropdowntoggle]");
//Liquid, Blood product instruction Timeline locators 
private final By timelineLiquidBy = By.xpath("//tr[@name='drugRow2'][td]/td[@colspan='8']");
private final By timelineBloodProductBy = By.xpath("//tr[@name='drugRow2'][td]/td[@colspan='6']");
   
private final By btnApprovalAll = By.xpath("//button[@id='approvalDrug']");

    public void approveDrugsSelectFourthCurrentDayHour() {

        List<WebElement> allDrugToApprovalRows = UIActions.findElementsWithWait(btnChooseHourCurrentDay);

        if (allDrugToApprovalRows.isEmpty()) {
           log.info("No drugs pending approval found.");     
            return;
        }
        log.info("Found {} drugs pending approval.", allDrugToApprovalRows.size());
        for (int i = 0; i < allDrugToApprovalRows.size(); i++) {
            WebElement currentRow = allDrugToApprovalRows.get(i);

            try {
                // 1. מציאת כפתור בחירת שעה יומית יחסית לשורה הנוכחית
                WebElement currentDayBtn = currentRow.findElements(btnChooseHourCurrentDay).get(i);
                // 2. בחירת האופציה הרביעית (אינדקס 3) מהרשימה הנפתחת
                selectNthOptionFromDropdown(currentDayBtn, 4); // 3 = רביעי
                log.info("Selected the fourth current day hour for drug in row {}.", i + 1);
                // 3. לחיצה על כפתור האישור הסופי (יחסי לשורה)
               // WebElement approvalBtn = currentRow.findElements(btnApproveDrugInRow).get(i);
                
              //  approvalBtn.click();
                log.info("Clicked approve for drug in row {}.", i + 1); 

            } catch (Exception e) {
                // ממשיכים לתרופה הבאה
                log.error("Error processing drug in row {}: {}", i + 1, e.getMessage());
                continue;
            }
        }

     

    }

    public void approvalAllLiquidAndBloodProductInstruction(){
        List<WebElement> allLiquidAndBloodProductTimeline = UIActions.findElementsWithWait(timelineLiquidBy);

        if (allLiquidAndBloodProductTimeline.isEmpty()) {
           log.info("No liquid or blood product instructions found in the timeline.");     
            return;
        }
        log.info("Found {} liquid/blood product instructions in the timeline.", allLiquidAndBloodProductTimeline.size());
        for (int i = 0; i < allLiquidAndBloodProductTimeline.size(); i++) {
            WebElement currentTimelineRow = allLiquidAndBloodProductTimeline.get(i);
            try {
                currentTimelineRow.findElements(By.xpath("//div[contains(@class,'timeLineInToday ')]")).get(i).click();
                    log.info("Clicked on timeline entry for liquid/blood product instruction in row {}.", i + 1);
        
                    // log.info("Clicked approve for liquid/blood product instruction in timeline row {}.", i + 1); 
            } catch (Exception e) {
                log.error("Error processing liquid/blood product instruction in timeline row {}: {}", i + 1, e.getMessage());
                continue;
            }

        }
        // לאחר לחיצה על כל כפתורי האישור, מבצעים את תהליך החתימה פעם אחת
      //  userSignModalPage.signModal(username,password);
    }

    /**
     * פותח Dropdown שנפתח על ידי אלמנט, ובוחר את הפריט באינדקס הנתון.
     * @param dropdownOpenerElement האלמנט (כפתור) שפותח את הרשימה
     * @param index האינדקס של הפריט לבחירה (0 = ראשון, 3 = רביעי)
     */
    private void selectNthOptionFromDropdown(WebElement dropdownOpenerElement, int index) {
        // 1. לחיצה על הכפתור לפתיחת הרשימה
        //   wait.until(ExpectedConditions.elementToBeClickable(dropdownOpenerElement));
        dropdownOpenerElement.click();

        // 2. המתנה שרשימת האפשרויות תופיע (המתנה מפורשת ללוקטור הגלובלי הפתוח)
        //  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownOptions));

        List<WebElement> options = DriverManager.getInstance().findElements(By.xpath("//div[contains(@class,'dropdown-menu show')]//button"));
        // log.info("Found {} options in the dropdown.", options.size());
         if (options.isEmpty()) {
             throw new RuntimeException("לא נמצאו אפשרויות ברשימה הנפתחת.");
         }
         log.info("Attempting to select option at index {}.", index);
        if (options.size() > index) {
            // 3. לחיצה על הפריט הרצוי
            options.get(index).click();

            // המתנה שהרשימה תיעלם (מוסיף יציבות)
            // wait.until(ExpectedConditions.invisibilityOfElementLocated(dropdownOptions));
        } else {
            throw new RuntimeException("לא נמצאה אפשרות באינדקס " + index + ". נמצאו רק " + options.size() + " אפשרויות.");
        }
    }

    public void approveAllCurrentDayHourAndVerify(String username, String password){
    //       UIActions.waitForVisible(btnApproval); 
    //    UIActions.click(btnApproval);
    //     userSignModalPage.signModal(username,password);
    //    if( UIActions.waitForInvisibility(btnApproval)){
    //     log.info("All drugs approved successfully.");
    //    }
    //    else{
    //     log.error("Failed to approve drugs.");
    //    }
        UIActions.click(btnApprovalAll);
        userSignModalPage.signModal(username,password);
       
    }

    public void approveAllInstructions(String username, String password){
        approvalAllLiquidAndBloodProductInstruction();

        approveDrugsSelectFourthCurrentDayHour();
        UIActions.click(btnApprovalAll);
        userSignModalPage.signModal(username,password);
    }
       //

    public void approvalAllInstructionByNurseAndVerify(String username, String password){
        UIActions.click(btnApprovalAll);
        userSignModalPage.signModal(username,password);
       //
   }
}
