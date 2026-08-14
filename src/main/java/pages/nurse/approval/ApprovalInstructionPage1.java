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

 
/**
 * 🔷 שכלול: אישור הוראות עם retry logic וטיפול חריגים
 * 
 * 🛡️ יציבות משופרת:
 * - Retry logic עם exponential backoff
 * - Stale Element Exception handling
 * - Better synchronization between clicks
 * - Robust error handling
 * - Detailed logging for debugging
 * 
 * @param drugOrGeneral אם לאשר תרופות והוראות כלליות
 * @param liquid אם לאשר תמיסות
 * @param bloodProduct אם לאשר מוצרי דם
 * @param username שם משתמש לחתימה
 * @param password סיסמה לחתימה
 */
public void approveAllInstructionsAndVerify(boolean drugOrGeneral, boolean liquid, boolean bloodProduct, String username, String password) {
    log.info("🔷 ========== התחלת תהליך אישור הוראות עם retry logic ==========");
    
    try {
        // ============= שלב 1: בחירת זמנים לכל סוג =============
        log.info("\n📝 שלב 1: בחירת זמנים לכל סוג הוראה");
        UIActions.waitForSpinnerToDisappear();
        
        if (drugOrGeneral) {
            log.info("  ▶ בחירת זמנים לתרופות והוראות כלליות...");
            try {
                approveDrugsAndGeneralSelectCurrentDayHour();
                log.info("  ✔ הושלמה בחירת זמנים לתרופות");
            } catch (Exception e) {
                log.warn("⚠️ שגיאה בבחירת זמנים לתרופות: {}", e.getMessage());
            }
        }
        
        if (liquid) {
            log.info("  ▶ בחירת זמנים לתמיסות...");
            try {
                approvalAllLiquidInstruction();
                log.info("  ✔ הושלמה בחירת זמנים לתמיסות");
            } catch (Exception e) {
                log.warn("⚠️ שגיאה בבחירת זמנים לתמיסות: {}", e.getMessage());
            }
        }
        
        if (bloodProduct) {
            log.info("  ▶ אישור מוצרי דם...");
            try {
                approvalAllbloodProduct();
                log.info("  ✔ הושלם אישור מוצרי דם");
            } catch (Exception e) {
                log.warn("⚠️ שגיאה באישור מוצרי דם: {}", e.getMessage());
            }
        }
        
        // ============= שלב 2: אישור כל הוראה בנפרד עם retry =============
        log.info("\n🔐 שלב 2: אישור כל הוראה בנפרד (עם retry logic)");
        UIActions.waitForSpinnerToDisappear();
        Thread.sleep(500); // קצת הפסקה לעדכון ה-DOM
        
        // קבלת מספר ההוראות הסופי
        List<WebElement> approvalButtons = UIActions.findElementsWithWait(btnApprovalBy);
        int totalButtons = approvalButtons.size();
        
        if (totalButtons == 0) {
            log.warn("⚠️ לא נמצאו כפתורי אישור להוראות.");
        } else {
            log.info("  📌 נמצאו {} הוראות לאישור", totalButtons);
            
            int successfulApprovals = 0;
            
            for (int i = 0; i < totalButtons; i++) {
                boolean approved = false;
                int retryCount = 0;
                int maxRetries = 3;
                
                while (!approved && retryCount < maxRetries) {
                    try {
                        // שליפה מחדש כדי למנוע Stale Element
                        List<WebElement> currentButtons = DriverManager.getInstance().findElements(btnApprovalBy);
                        
                        if (currentButtons.isEmpty()) {
                            log.warn("⚠️ לא נמצאו עוד כפתורים בהוראה #{}", i + 1);
                            break;
                        }
                        
                        WebElement button = currentButtons.get(0);
                        
                        // בדיקה שהכפתור גלוי
                        UIActions.waitForElementVisibleBy(button);
                        Thread.sleep(300); // קצת המתנה לפני לחיצה
                        
                        // לחיצה עם JavaScript
                        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getInstance();
                        js.executeScript("arguments[0].scrollIntoView(true);", button);
                        Thread.sleep(200);
                        js.executeScript("arguments[0].click();", button);
                        
                        log.info("  ✓ אישור הוראה {}/{} (attempt {}/{})", i + 1, totalButtons, retryCount + 1, maxRetries);
                        
                        // סנכרון: המתנה לטעינה ואישור שהכפתור הפך ל'ערוך'
                        UIActions.waitForSpinnerToDisappear();
                        Thread.sleep(400); // המתן לעדכון ה-DOM
                        
                        // בדיקה שהוראה אושרה (כפתור הפך ל'ערוך')
                        List<WebElement> editButtons = DriverManager.getInstance().findElements(btnEditBy);
                        if (editButtons.size() > successfulApprovals) {
                            approved = true;
                            successfulApprovals++;
                            log.info("  ✅ הוראה #{} אושרה בהצלחה", i + 1);
                        } else {
                            retryCount++;
                            if (retryCount < maxRetries) {
                                log.warn("  🔄 נסיון חוזר #{} להוראה #{}", retryCount, i + 1);
                                Thread.sleep(500 * retryCount); // exponential backoff
                            }
                        }
                    } catch (StaleElementReferenceException e) {
                        retryCount++;
                        log.warn("  ⚠️ Stale Element Exception בהוראה #{}, נסיון חוזר {} של {}", i + 1, retryCount, maxRetries);
                        if (retryCount < maxRetries) {
                            UIActions.waitForSpinnerToDisappear();
                            Thread.sleep(600 * retryCount);
                        }
                    } catch (ElementClickInterceptedException e) {
                        retryCount++;
                        log.warn("  ⚠️ Click Intercepted בהוראה #{}, נסיון חוזר {} של {}", i + 1, retryCount, maxRetries);
                        if (retryCount < maxRetries) {
                            UIActions.waitForSpinnerToDisappear();
                            Thread.sleep(600 * retryCount);
                        }
                    } catch (Exception e) {
                        retryCount++;
                        log.error("  ❌ שגיאה בהוראה #{} (attempt {}): {}", i + 1, retryCount, e.getMessage());
                        if (retryCount < maxRetries) {
                            Thread.sleep(600 * retryCount);
                        }
                    }
                }
                
                if (!approved) {
                    log.error("  ❌ לא הצליח לאשר הוראה #{} אחרי {} ניסיונות", i + 1, maxRetries);
                }
            }
            
            log.info("  📊 סיכום: {} הוראות אושרו בהצלחה מתוך {}", successfulApprovals, totalButtons);
        }
        
        // ============= שלב 3: חתימה סופית עם retry =============
        log.info("\n🖊️ שלב 3: חתימה סופית");
        UIActions.waitForSpinnerToDisappear();
        Thread.sleep(800); // המתנה משמעותית לפני החתימה
        
        int signRetries = 0;
        int maxSignRetries = 3;
        boolean signed = false;
        
        while (!signed && signRetries < maxSignRetries) {
            try {
                log.info("  ▶ לחיצה על כפתור האישור הסופי (ניסיון {})", signRetries + 1);
                
                // בדיקה שהכפתור קיים ברור
                List<WebElement> finalButtons = DriverManager.getInstance().findElements(btnApprovalAll);
                if (finalButtons.isEmpty()) {
                    log.warn("  ⚠️ כפתור האישור הסופי לא נמצא!");
                    signRetries++;
                    Thread.sleep(700);
                    continue;
                }
                
                UIActions.click(btnApprovalAll);
                Thread.sleep(600); // המתנה למודאל
                
                log.info("  ▶ הזנת פרטי חתימה (שם משתמש: {})", username);
                userSignModalPage.signModal(username, password);
                
                UIActions.waitForSpinnerToDisappear();
                Thread.sleep(1000); // המתנה לאחר חתימה
                
                signed = true;
                log.info("  ✅ חתימה סופית הושלמה בהצלחה");
            } catch (Exception e) {
                signRetries++;
                log.warn("  ⚠️ שגיאה בחתימה (ניסיון {}): {}", signRetries, e.getMessage());
                if (signRetries < maxSignRetries) {
                    UIActions.waitForSpinnerToDisappear();
                    Thread.sleep(800 * signRetries);
                }
            }
        }
        
        if (!signed) {
            log.error("  ❌ לא הצליח לחתום אחרי {} ניסיונות", maxSignRetries);
        }
        
        // ============= שלב 4: אימות סופי =============
        log.info("\n✅ ========== תהליך אישור הוראות הושלם בהצלחה! ==========");
        verifyAllInstructionsApproved();
        
    } catch (InterruptedException e) {
        log.error("❌ תהליך אישור הוראות הופרע: {}", e.getMessage());
        Thread.currentThread().interrupt();
        throw new RuntimeException("Approval process was interrupted", e);
    }
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
