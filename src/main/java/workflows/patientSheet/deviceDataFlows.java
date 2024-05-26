package workflows.patientSheet;
import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import utilities.CommonOps;

import java.util.List;

public class deviceDataFlows extends CommonOps {

    static WebElement cell=patientSheetMain.cell_devicesResult_nowColum.get(1);;
    static Actions action = new Actions(driver);

    @Step("confirm_device_data")
    public static void confirm_device_data() throws InterruptedException {

        action.contextClick(cell).perform();
        try {

            UIActions.click(patientSheetMain.li_confirmAction);
        }
        catch (Exception e){
            System.out.println("no success to do an action on the cell ");
        }
        //  System.out.println( "the cell's font-weight is : "+ cell.findElement(By.tagName("div")).getCssValue("font-weight"));
        //האם להוסיף גם ולדציה ?
    }

    @Step("make_error_device_data")
    public static void make_error_device_data() {

        action.contextClick(cell).perform();
        try {
            UIActions.click(patientSheetMain.li_errorAction);
        }
        catch (Exception e) {
            System.out.println("no success to do an error action on the cell ");
        }
    }

    @Step("add_a_comment_device_data")
    public static void add_a_comment_device_data() throws InterruptedException {
        action.contextClick(cell).perform();
        try {

            UIActions.click(patientSheetMain.li_comment);
            patientSheetMain.textInput_comment.sendKeys("הערה בדיקות");
            UIActions.click(patientSheetMain.button_ok);
        }
        catch (Exception e) {
            System.out.println("no success to add a comment action on the cell ");
        }
    }
    @Step("add_an_error_comment_device_data")
    public static void add_an_error_comment_device_data() throws InterruptedException {
        action.contextClick(cell).perform();
        try {
            UIActions.click( patientSheetMain.li_comment);
            patientSheetMain.textInput_comment.sendKeys("הערה בדיקות + שגיאה");
            UIActions.click(patientSheetMain.checkBox_markAsError);
            UIActions.click(patientSheetMain.button_ok);
        }
        catch (Exception e){
            System.out.println("no success to add an error comment action on the cell ");
        }
    }
}