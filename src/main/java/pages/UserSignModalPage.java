package pages;

import actionUtilies.UIActions;



import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserSignModalPage {

    Logger logger = LoggerFactory.getLogger(UserSignModalPage.class);
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
            UIActions.typeText(inputUserName, username);
            UIActions.typeText(inputPassword, password);
            UIActions.click(btnConfirm);
            UIActions.waitForInvisibility(btnConfirm);
            logger.info("approval process completed successfully by user: " + username);

        } catch (Exception e) {
            logger.error("approval process failed for user: " + username + ". Error: " + e.getMessage());
       }
    }
}
