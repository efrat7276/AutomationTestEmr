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
    // ==========================================================
    // Drug Locators 💊
    // ==========================================================
    private By drugToApprovalRow = By.xpath("//tr[@name='drugRow1'][./td[8]//button[contains(@id,'btnIsApproval')]]");
    private By btnChooseHourCurrentDay = By.xpath("//following-sibling::tr[@name='drugRow2']//td[@name='drugsCurrentDay']//button[@ngbdropdowntoggle]");

    private final By btnApproveDrugInRow = By.xpath("./td[8]//button[contains(@id,'btnIsApproval')]");
    private final By dropdownOptions =
            By.xpath("//ul[contains(@class,'dropdown-menu') and contains(@class,'show')]/li");

    private final By btnApproval = By.xpath("//button[@id='approvalDrug']");

    public void approveDrugsSelectFourthCurrentDayHourAndVerify(String username, String password) {

        List<WebElement> allDrugToApprovalRows = UIActions.findElementsWithWait(drugToApprovalRow);

        if (allDrugToApprovalRows.isEmpty()) {
           log.info("No drugs pending approval found.");     
            return;
        }
        log.info("Found {} drugs pending approval.", allDrugToApprovalRows.size());
        for (int i = 0; i < allDrugToApprovalRows.size(); i++) {
            WebElement currentRow = allDrugToApprovalRows.get(i);

            try {
                // 1. מציאת כפתור בחירת שעה יומית יחסית לשורה הנוכחית
                WebElement currentDayBtn = currentRow.findElement(btnChooseHourCurrentDay);
                // 2. בחירת האופציה הרביעית (אינדקס 3) מהרשימה הנפתחת
                selectNthOptionFromDropdown(currentDayBtn, 4); // 3 = רביעי
                log.info("Selected the fourth current day hour for drug in row {}.", i + 1);
                // 3. לחיצה על כפתור האישור הסופי (יחסי לשורה)
                WebElement approvalBtn = currentRow.findElement(btnApproveDrugInRow);
                
                approvalBtn.click();
                log.info("Clicked approve for drug in row {}.", i + 1); 

            } catch (Exception e) {
                // ממשיכים לתרופה הבאה
                log.error("Error processing drug in row {}: {}", i + 1, e.getMessage());
                continue;
            }
        }

       UIActions.waitForVisible(btnApproval); 
       UIActions.click(btnApproval);
        userSignModalPage.signModal(username,password);
       if( UIActions.waitForInvisibility(btnApproval)){
        log.info("All drugs approved successfully.");
       }
       else{
        log.error("Failed to approve drugs.");
       }

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

        List<WebElement> options = DriverManager.getInstance().findElements(dropdownOptions);
        if (options.size() > index) {
            // 3. לחיצה על הפריט הרצוי
            options.get(index).click();

            // המתנה שהרשימה תיעלם (מוסיף יציבות)
            // wait.until(ExpectedConditions.invisibilityOfElementLocated(dropdownOptions));
        } else {
            throw new RuntimeException("לא נמצאה אפשרות באינדקס " + index + ". נמצאו רק " + options.size() + " אפשרויות.");
        }
    }


    public void approvalAllInstructionByNurseAndVerify(String username, String password){
        UIActions.click(btnApproval);
        userSignModalPage.signModal(username,password);
       //
   }
}
