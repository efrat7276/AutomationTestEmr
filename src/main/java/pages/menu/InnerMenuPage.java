package pages.menu;

import actionUtilies.UIActions;
import drivers.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

import pages.BasePage;

@Slf4j
public class InnerMenuPage extends BasePage {

    // לוקטור בסיס, משמש לתבנית XPath.
    // 1. מחפש את ה-<span> הספציפי כפי שמופיע בדוגמה ששלחת.
    // 2. מחפש כל אלמנט שמכיל את הטקסט, כדי להיות גמיש.
    private final String MENU_ITEM_XPATH_TEMPLATE =
            "//*[normalize-space(text())='%s']";

    /**
     * מקבלת את שם כניסת התפריט בעברית ולוחצת על הכניסה המתאימה.
     * * @param entryName שם הכניסה המדויק (למשל, "תרופות", "רשימת מטופלים").
     */
    public void navigateToMenuEntry(String entryName) {
        // 1. בניית ה-XPath הדינמי
        String dynamicXpath = String.format(MENU_ITEM_XPATH_TEMPLATE, entryName);

        // 2. יצירת אובייקט By
        By targetLocator = By.xpath(dynamicXpath);

        log.info("Navigating to menu entry: " + entryName + " using XPath: " + dynamicXpath);

        try {
            // 3. שימוש ב-UIActions ללחיצה (הכוללת המתנה מובנית)
            // הערה: אם יש בעיית לחיצה, ננסה להשתמש ב-JavaScript Executor (אם צריך).
            UIActions.click(targetLocator);
            log.info("Navigation to menu entry '" + entryName + "' was successful.");

        } catch (Exception e) {
            log.error("❌ Failed to navigate to menu entry '" + entryName + "'. Ensure the name is correct. Error: " + e.getMessage());
            throw new RuntimeException("Failed to navigate in the inner menu", e);
        }
    }

    /**
     * Returns true if a menu entry with the given name is present and displayed.
     */
    public boolean isMenuEntryDisplayed(String entryName) {
        String dynamicXpath = String.format(MENU_ITEM_XPATH_TEMPLATE, entryName);
        By targetLocator = By.xpath(dynamicXpath);
        try {
            return DriverManager.getInstance().findElement(targetLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


}
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'back-to-list top-menu')]//span[text()='רשימת מטופלים']")
//    public WebElement depMeushpazim;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' הוראות רפואיות ']")
//    public WebElement doctorInstruction;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' מעקב רופא ']")
//    public WebElement doctorfollowup;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' גליון מטופל ']")
//    public WebElement patientSheet;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' אבחנות ']")
//    public WebElement diagnoses;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' קרדקס ']")
//    public WebElement cardex;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' מאזן נוזלים ']")
//    public WebElement fluidBalance;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' סיעוד ']")
//    public WebElement nursing;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' עורך מסמכים ']")
//    public WebElement documentEditor;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' הגדרת ייעדים ']")
//    public WebElement goals;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' מנהל חולים ']")
//    public WebElement hospitalDirector;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' דוחות ']")
//    public WebElement reports;
//
//    // just nursing
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' אישור אחות ']")
//    public WebElement nurseConfirmation;
//
//
//// subcategories for doctorInstruction category
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='תרופות']")
//    public WebElement  drugs;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='כלליות']")
//    public WebElement  general;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='מוצרי דם']")
//    public WebElement  bloodProduct;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='תזונה']")
//    public WebElement  nutrition;
//
//
//
//
//// subcategories for nursing category
//
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='הוראות סיעודיות']")
//    public WebElement  nursingIns;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='מעקב סיעודי']")
//    public WebElement  nursingFollowup;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='נקזים וצנתרים']")
//    public WebElement  catheters;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='פצעים']")
//    public WebElement  wounds;
//
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='אישור הוראות']")
//    public WebElement  instructionConfirmation;
//
//    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='עדכון ביצועים']")
//    public WebElement  updateExecution;
//

//
//    public HashMap <String,String> categories = new HashMap<String,String>();
//
//    public void initCategoryHashMap(){
//        categories.put("doctorInstruction", "category_doctorInstruction");
//        categories.put("doctorfollowup", "category_doctorfollowup");
//        categories.put("doctorInstruction", "category_doctorInstruction");
//        categories.put("doctorInstruction", "category_doctorInstruction");
//        categories.put("doctorInstruction", "category_doctorInstruction");
//
//    }



//}
