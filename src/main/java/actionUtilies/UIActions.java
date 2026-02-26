
package actionUtilies;

import drivers.DriverManager;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import pages.addForms.DrugFormPage;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

@Slf4j
public class UIActions {


    static WebDriverWait wait = DriverManager.getWait();

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
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
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
    public static boolean waitForText(By elem , String text){

         wait.until(x -> (findElementWithWait(elem).getText()).contains(text));
         try{
           click(elem);
           return true;
         }catch (Exception e){
             return false;
         }
       

    }

    public static void click(By locator) {
    try {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    } catch (StaleElementReferenceException e) {
        // Retry once
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    } catch (ElementClickInterceptedException e) {
        safeClick(locator);
    } catch (TimeoutException e) {
        throw new RuntimeException("Failed to click element: " + locator, e);
    }
}
    public static void click(WebElement element) {
    wait.until(ExpectedConditions.visibilityOf(element));
       element.click();
}  

    /**
     * Safe click by locator with retries for transient UI errors (stale/intercepted).
     * Throws RuntimeException if all attempts fail.
     */
    public static void safeClick(By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
                el.click();
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException | TimeoutException e) {
                attempts++;
                waitForSpinnerToDisappear();
                try { Thread.sleep(200); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
        throw new RuntimeException("safeClick failed after retries for: " + locator);
    }

   
  

    public static void typeText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
       // log.info("Typing text '{}' into element: {}", text, locator);
        element.clear();
        element.sendKeys(text);
    }
    public static boolean isElementDisplayed(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        boolean displayed = element.isDisplayed();
      //  log.info("Checking if element {} is displayed: {}", locator, displayed);
        return displayed;
    }

    @Step("Clear Text Element")
    public static void  clearText(By by){

        WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        element.clear();
    }
        /**
     * ממתין להיעלמות הספינר הכללי של טעינה <emr-spinner>
     * יש לקרוא לפונקציה זו בכניסה לכל מסך לפני כל פעולה.
     */
    public static void waitForSpinnerToDisappear() {
        By spinner = By.tagName("emr-spinner");
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(spinner));
        } catch (Exception e) {
     //       log.error("Error while waiting for spinner to disappear: {}", e.getMessage());
        }
    }
    
    public static void waitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForElementClickable(By element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
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

            // המתנה מפורשת (Explicit Wait)
            // ExpectedConditions.invisibilityOfElementLocated מטפל גם באלמנט שמוסר מה-DOM וגם באלמנט שהופך לבלתי נראה (opacity: 0, visibility: hidden, display: none).
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

  //       log.info("Element located by " + locator + " has become invisible or removed from DOM.");
            return true;

        } catch (TimeoutException e) {
         //   System.err.println("❌ Timeout: האלמנט עדיין גלוי לאחר " + wait.getTimeout().getSeconds() + " שניות. לוקטור: " + locator.toString());
            // ניתן לבחור לזרוק את השגיאה או להחזיר false
            // throw e;
            return false;
        } catch (Exception e) {
       //     log.error("Unexpected error while waiting for element to become invisible: " + e.getMessage());
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

        log.info("select from list :{} , text to select: {}", list, text);
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
    public static boolean waitForVisible(By locator) {
        try {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            log.error("❌ אירעה שגיאה בלתי צפויה בזמן ההמתנה: " + e.getMessage());
            return false;
        }
    }

}



