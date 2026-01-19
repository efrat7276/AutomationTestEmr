package pages.nurse.approval;

import actionUtilies.UIActions;
import drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

public class ApprovalInstructionPage extends BasePage {

    // ==========================================================
    // Drug Locators 💊
    // ==========================================================
    private By drugToApprovalRow = By.xpath("//tr[@name='drugRow1'][./td[8]//button[contains(@id,'btnIsApproval')]]");
    private By btnChooseHourCurrentDay = By.xpath("//following-sibling::tr[@name='drugRow2']//td[@name='drugsCurrentDay']//button[@ngbdropdowntoggle]");

    private final By btnApproveDrugInRow = By.xpath("./td[8]//button[contains(@id,'btnIsApproval')]");
    private final By dropdownOptions =
            By.xpath("//ul[contains(@class,'dropdown-menu') and contains(@class,'show')]/li");

    private final By btnApproval = By.xpath("//button[@id='approvalDrug']");

    public void approveDrugsSelectFourthCurrentDayHour() {

        List<WebElement> allDrugToApprovalRows = UIActions.findElementsWithWait(drugToApprovalRow);

        if (allDrugToApprovalRows.isEmpty()) {
            System.out.println("אין תרופות ממתינות לפירוק.");
            return;
        }

        System.out.println("נמצאו " + allDrugToApprovalRows.size() + " תרופות לאישור.");

        for (int i = 0; i < allDrugToApprovalRows.size(); i++) {
            WebElement currentRow = allDrugToApprovalRows.get(i);

            try {
                // 1. מציאת כפתור בחירת שעה יומית יחסית לשורה הנוכחית
                WebElement currentDayBtn = currentRow.findElement(btnChooseHourCurrentDay);

                // 2. בחירת האופציה הרביעית (אינדקס 3) מהרשימה הנפתחת
                selectNthOptionFromDropdown(currentDayBtn, 4); // 3 = רביעי

                // 3. לחיצה על כפתור האישור הסופי (יחסי לשורה)
                WebElement approvalBtn = currentRow.findElement(btnApproveDrugInRow);
              //  wait.until(ExpectedConditions.elementToBeClickable(approvalBtn));
                approvalBtn.click();

                System.out.println("✅ תרופה " + (i + 1) + " אושרה בהצלחה.");

            } catch (Exception e) {
                System.err.println("❌ כשל באישורים עבור תרופה " + (i + 1) + ". שגיאה: " + e.getMessage());
                // ממשיכים לתרופה הבאה
                continue;
            }
        }
        System.out.println("סיום תהליך אישור התרופות.");

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
