package pages;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
public class UserSignModalPage {
    // שדה שם המשתמש
    private final By inputUserName = By.id("user");

    // שדה הסיסמה
    private final By inputPassword = By.id("password");

    // כפתור אישור
    private final By btnConfirm = By.xpath("//app-user-sign-modal//button[contains(@class,'btn-ok')]");

    // כפתור ביטול
    private final By btnCancel = By.xpath("//app-user-sign-modal//button[contains(@class,'btn-cancel')]");

     public UserSignModalPage() {
        UIActions.waitForSpinnerToDisappear();
    }

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
            UIActions.waitForVisible(btnConfirm);
            UIActions.click(btnConfirm);
           
            log.info("approval process completed successfully by user: " + username);

        } catch (Exception e) {
            log.error("approval process failed for user: " + username + ". Error: " + e.getMessage());
       }
    }
}
