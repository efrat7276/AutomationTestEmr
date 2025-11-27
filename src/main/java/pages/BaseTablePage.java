package pages;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver; // נניח ש-driver זמין
import java.util.List;

/**
 * מחלקת בסיס גנרית לטיפול בפעולות בטבלאות (כגון לחיצה על הפעולה בשורה הראשונה).
 * מחלקה זו אמורה לרשת מה-BasePage הקיים שלך (או לקבל UIActions בקונסטרוקטור).
 */
public abstract class BaseTablePage  extends BasePage {


    /**
     * מבצע לחיצה על כפתור/אייקון הפעולה בשורה הראשונה בטבלה.
     * * @param rowLocator הלוקטור (By) המייצג את כל שורות הטבלה (לדוגמה: By.tagName("tr")).
     * @param actionLocator הלוקטור (By) המייצג את כפתור הפעולה בתוך השורה (לדוגמה: By.xpath(".//i[@class='open-doc-icon']")).
     */
    public void clickActionOnFirstRow(By rowLocator, By actionLocator) {
        // 1. מציאת כל השורות בטבלה, תוך המתנה לטעינה (משתמשים במתודה מה-UIActions שצריכה להחזיר רשימה עם המתנה).
        List<WebElement> rows = UIActions.findElementsWithWait(rowLocator);

        if (rows.isEmpty()) {
            throw new RuntimeException("לא נמצאו שורות בטבלה באמצעות הלוקטור: " + rowLocator.toString());
        }
        WebElement firstRow = rows.get(0);
        try {
            WebElement actionElement = firstRow.findElement(actionLocator);
            actionElement.click(); // לחיצה ישירה היא הדרך הנפוצה ביותר כשכבר יש WebElement
        } catch (Exception e) {
            throw new RuntimeException("כשל בפעולה על שורת הטבלה", e);
        }
    }
}