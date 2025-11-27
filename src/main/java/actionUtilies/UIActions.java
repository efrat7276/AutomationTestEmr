package actionUtilies;

import drivers.DriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

public class UIActions {


    static WebDriverWait wait = new WebDriverWait(DriverManager.getInstance(), Duration.ofSeconds(10));

    /**
     *
     * @param by locator to find element
     * @return
     * find the element by locator and return element
     */
   public static WebElement findElementWithWait(By by){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     *
     * @param by locator to find list elements
     * @return
     * find the element by locator and return list element
     */
   public static List<WebElement> findElementsWithWait(By by){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }
    static boolean containsAnyDigit(String s){
        for (int i = 1; i < 10; i++){
           if(s.contains(String.valueOf(i))){
               return true;
           }
        }
        return false;
    }

    @Step("Wait for text in element")
    public static void waitForDigit(WebElement elem){

        //wait.until(x -> containsAnyDigit(elem.getText()));
        elem.click();
    }

    @Step("Wait for text in element")
    public static void waitForText(WebElement elem , String text){

        //wait.until(ExpectedConditions.textToBePresentInElement(elem, text));
        elem.click();
    }

    public static void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        System.out.println("Clicking element: " + locator);
        element.click();
    }

    public static void typeText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        System.out.println("Typing text '" + text + "' into element: " + locator);
        element.clear();
        element.sendKeys(text);
    }
    public static boolean isElementDisplayed(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        boolean displayed = element.isDisplayed();
        System.out.println("Checking if element " + locator + " is displayed: " + displayed);
        return displayed;
    }

    @Step("Clear Text Element")
    public static void  clearText(By by){

        WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        element.clear();
    }



    /**
     * ממתין עד שאלמנט המזוהה על ידי הלוקטור יהפוך לבלתי נראה או יוסר מה-DOM.
     * שימושי במיוחד לאחר לחיצה על כפתור שסוגר מודל או מסיר ספינר טעינה.
     *
     * @param locator הלוקטור (By) של האלמנט שעתיד להיעלם.
     * @return true אם האלמנט נעלם בהצלחה, false אם חלה שגיאת Timeout.
     */
    public static boolean waitForInvisibility(By locator) {
        try {
            System.out.println("מתחיל המתנה להיעלמות האלמנט: " + locator.toString());

            // המתנה מפורשת (Explicit Wait)
            // ExpectedConditions.invisibilityOfElementLocated מטפל גם באלמנט שמוסר מה-DOM וגם באלמנט שהופך לבלתי נראה (opacity: 0, visibility: hidden, display: none).
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

            System.out.println("✔ האלמנט נעלם בהצלחה.");
            return true;

        } catch (TimeoutException e) {
         //   System.err.println("❌ Timeout: האלמנט עדיין גלוי לאחר " + wait.getTimeout().getSeconds() + " שניות. לוקטור: " + locator.toString());
            // ניתן לבחור לזרוק את השגיאה או להחזיר false
            // throw e;
            return false;
        } catch (Exception e) {
            System.err.println("❌ אירעה שגיאה בלתי צפויה בזמן ההמתנה: " + e.getMessage());
            return false;
        }
    }
    @Step("Select text from Dropdown")
    public static void  updateDropdown(WebElement elem, String text){

        wait.until(ExpectedConditions.visibilityOf(elem));
        Select dropdown = new Select(elem);
        dropdown.selectByVisibleText(text);
    }

    @Step("Mouse Hover Element")
    public static void mouseHover(WebElement elem1){

       // action.moveToElement(elem1).click().build().perform();
    }

   @Step("Mouse Hover two Elements")
    public static void mouseHover(WebElement elem1, WebElement elem2){

        //action.moveToElement(elem1).moveToElement(elem2).click().build().perform();
    }

    @Step("Select text from List")
    public static void selectFromList(By list , String text){

        List<WebElement>elemntsList= wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(list));
        for(WebElement li : elemntsList){

            if(li.getText().equalsIgnoreCase(text)){
              li.click();
                break;
            }
        }
    }

    @Step("Search text from List")
    public static void searchInList(List<WebElement> list , String text){

        for(WebElement li : list){
            if(li.getText().contains(text)){
                //click(li);
                break;
            }
        }
    }
    @Step("Select text from ListInsideAnotherTag")
    public static void selectFromListInsideAnotherTag(List<WebElement> list , String text){

        for(WebElement li : list){
            if(li.findElement(By.tagName("a")).getText().equals(text)){
             //   click(li);
                break;
            }
        }
    }

//    @Step("Select text from ListInsideAnotherTag")
//    public static void selectFromListTextContains(List<WebElement> list , String text){
//
//        for(WebElement li : list){
//            if(li.findElement(By.tagName("a")).getText().equals(text)){
//                click(li);
//                break;
//            }
//        }
//    }

    @Step("Select from List by index")
    public static void selectFromListByIndex(List<WebElement> list , int index){

        list.get(index).click();

    }

    @Step("check if the element exist ")
    public static boolean isDisplay(WebElement elem) {

           if( elem.isDisplayed())
            return true;
           else
            return false;
    }

    @Step("check if the element exist ")
    public static boolean isExist(WebElement elem) {

        try {
            elem.getTagName();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("check if the element exist ")
    public static boolean isExist(List< WebElement> elem) {

        try {
            elem.get(0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static String getText(By by) {

        return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText();
    }
}



