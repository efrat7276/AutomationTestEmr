package extensions;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.sikuli.script.FindFailed;
import utilities.CommonOps;

import java.util.List;

import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

public class Verifications extends CommonOps {

    public static Object existInList;

    @Step("Verify count in List")
    public static void numberOfElementList(List< WebElement> li, int expected){
        wait.until(ExpectedConditions.visibilityOf(li.get(li.size()-1)));
        assertEquals(li.size() , expected);
    }


    // todo - בדיקה לא עובדת- לבדוק מדוע
    @Step("Verify String exist in List")
    public static void existInList(List< WebElement> li, String expected) throws InterruptedException {
        Thread.sleep(1000);
        for(WebElement l: li) {
        if (l.getText().equals(expected));
            {
                assertTrue(1 == 1);
                return;
            }
        }
        assertTrue(1==0);
    }

    @Step("Verify count")
    public static void numberOfElement(int count, int expected){
       // wait.until(ExpectedConditions.visibilityOf(li.get(li.size()-1)));
        assertEquals(count , expected);
    }

    @Step("Verify Text in Element")
    public static void textIsVisible(WebElement elem , String expected){
        wait.until(ExpectedConditions.urlContains("main"));
        assertEquals(elem.getText() , expected);
    }

    @Step("Verify Text in Element")
    public static void textIsContains(WebElement elem , String expected){
        wait.until(ExpectedConditions.urlContains("main"));
        assertTrue(elem.getText().contains(expected));
      //  System.out.println(elem.getText());
    }

    @Step("Verify Element is Display On Screen")
    public static void isElementDisplay(WebElement elem){
        assertTrue(elem.isDisplayed());
    }


    @Step("Verify Text in Input Element")
    public static void textInputIsNotEmpty(WebElement elem){
        assertTrue(!(elem.getAttribute("value").isEmpty()));
    }


    @Step("Verify Text in Input Element")
    public static void textIsDifferentFrom(WebElement elem, String text){
        assertTrue(!elem.getText().equals(text));
    }

    @Step("Verify The Element is Select")
    public static void isSelected(WebElement elem){
        assertTrue(elem.isSelected());
    }

    @Step("Verify Text Element is Not Select")
    public static void isNotSelected(WebElement elem){
        assertTrue(!elem.isSelected());
    }


    @Step("Verify The Element is Displayed")
    public static void isDisplay(WebElement elem){
        assertTrue(elem.isDisplayed());
    }

    @Step("Verify Text Element is Not Displayed")
    public static void isNotDisplay(WebElement elem){
        try{
            elem.isDisplayed();
            fail();
        }
        catch (Exception e){
           assertTrue(1==1);
        }
    }


    @Step("Verify Elements Are Display On Screen")
    public static void visibilityOfElements(List<WebElement> elems){

       for(WebElement elem: elems) {
           softAssert.assertTrue(elem.isDisplayed());
       }
       softAssert.assertAll();
    }

    @Step("Verify Element Visually")
    public static void visualElement(String expectedElement ) {
        try {
            screen.find(getData("ImageRepository") +  expectedElement );
        }
        catch (FindFailed findFailed) {

            System.out.println("Error Compare Image File:" + findFailed);
            fail("Error Compare Image File: + findFailed");
        }
    }
}
