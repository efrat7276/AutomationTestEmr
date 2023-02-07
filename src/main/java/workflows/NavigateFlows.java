package workflows;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import pageObjects.emr.menu.InnerMenuPage;
import utilities.Base;
import utilities.CommonOps;

import java.lang.reflect.Field;



public class NavigateFlows extends CommonOps {


    @Step("goToCategory")
    public static void goToCategory(String categoryName) throws InterruptedException {

        String name = "category_" + categoryName;

        WebElement elem = getWebElementByObjectAndName(Base.innerMenuPage, name);
        Thread.sleep(4000);
        elem.click();
    }

    @Step("goToSubCategory")
    public static void goToSubCategory(String subCategoryName) throws InterruptedException {

        String name = "subcategory_" + subCategoryName;

        WebElement elem = getWebElementByObjectAndName(Base.innerMenuPage, name);
        Thread.sleep(2000);
        elem.click();
    }
//
//    @Step("goToSubCategory")
//    public static void exitFromCardexPage(String subCategoryName)  {
//
//        String name = "category_" + subCategoryName;
//
//        WebElement elem = getWebElementByObjectAndName(Base.innerMenuPage, name);
//        Thread.sleep(5000);
//        elem.click();
//    }

    public static WebElement getWebElementByObjectAndName(Object object, String fieldName) {
        WebElement webElement = null;
        Class<?> validationClass = object.getClass();
        Field[] fields = validationClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == WebElement.class) {
                field.setAccessible(true);
                if (field.getName().equals(fieldName)) {
                    try {
                        webElement = (WebElement) field.get(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
        return webElement;
    }

}