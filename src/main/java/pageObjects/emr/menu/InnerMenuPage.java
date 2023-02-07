package pageObjects.emr.menu;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.HashMap;

public class InnerMenuPage {

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' הוראות רפואיות ']")
    public WebElement category_doctorInstruction;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' מעקב רופא ']")
    public WebElement category_doctorfollowup;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' אבחנות ']")
    public WebElement category_diagnoses;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' קרדקס ']")
    public WebElement category_cardex;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' מאזן נוזלים ']")
    public WebElement category_fluidBalance;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' סיעוד ']")
    public WebElement category_nursing;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' עורך מסמכים ']")
    public WebElement category_documentEditor;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' מנהל חולים ']")
    public WebElement category_hospitalDirector;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' דוחות ']")
    public WebElement category_reports;

    // just nursing

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' אישור אחות ']")
    public WebElement category_nurseConfirmation;


// subcategories for doctorInstruction category

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='תרופות']")
    public WebElement subcategory_drugs;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='כלליות']")
    public WebElement subcategory_general;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='מוצרי דם']")
    public WebElement subcategory_bloodProduct;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='תזונה']")
    public WebElement subcategory_nutrition;



// subcategories for nursing category


    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='הוראות סיעודיות']")
    public WebElement subcategory_nursingIns;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='מעקב סיעודי']")
    public WebElement subcategory_nursingFollowup;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='נקזים וצנתרים']")
    public WebElement subcategory_catheters;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='פצעים']")
    public WebElement subcategory_wounds;


    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='אישור הוראות']")
    public WebElement subcategory_instructionConfirmation;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='עדכון ביצועים']")
    public WebElement subcategory_updateExecution;


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



}
