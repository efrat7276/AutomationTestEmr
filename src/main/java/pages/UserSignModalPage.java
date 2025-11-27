package pages;

import actionUtilies.UIActions;
import org.openqa.selenium.By;

public class UserSignModalPage {

    // שדה שם המשתמש
    private final By inputUserName = By.id("user");

    // שדה הסיסמה
    private final By inputPassword = By.id("password");

    // כפתור אישור
    private final By btnConfirm = By.xpath("//form//button[text()='אישור']");

    // כפתור ביטול
    private final By btnCancel = By.xpath("//form//button[text()='ביטול']");

    /**
     * מזינה שם משתמש וסיסמה למודל החתימה ולוחצת על כפתור האישור,
     * באמצעות מתודות עזר המטפלות בהמתנות (UIActions).
     * * @param username שם המשתמש/קוד חותם
     * @param password סיסמת החתימה
     */
    public  void signModal(String username, String password ) {
        try {
            UIActions.clearText(inputUserName);
            // 1. מילוי שדה שם המשתמש
            UIActions.typeText(inputUserName, username);
            // 2. מילוי שדה הסיסמה
            UIActions.typeText(inputPassword, password);
            // 3. לחיצה על כפתור האישור
            UIActions.click(btnConfirm);

            // אופציונלי: ודא שהמודל נסגר, בהנחה שיש ל-UIActions מתודה כזו
             UIActions.waitForInvisibility(btnConfirm);

        } catch (Exception e) {
            System.err.println("❌ כשל בתהליך החתימה במודל. שגיאה: " + e.getMessage());
            // זורקים שגיאת זמן ריצה כדי להפיל את המקרה (Case)
            throw new RuntimeException("כשל בחתימה/התחברות במודל החתימה", e);
        }
    }
}
