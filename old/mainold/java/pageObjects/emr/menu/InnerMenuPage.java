package pageObjects.emr.menu;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.HashMap;

public class InnerMenuPage {


    @FindBy(how = How.XPATH ,using="//div[contains(@class,'back-to-list top-menu')]//span[text()='רשימת מטופלים']")
    public WebElement depMeushpazim;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' הוראות רפואיות ']")
    public WebElement doctorInstruction;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' מעקב רופא ']")
    public WebElement doctorfollowup;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' גליון מטופל ']")
    public WebElement patientSheet;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' אבחנות ']")
    public WebElement diagnoses;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' קרדקס ']")
    public WebElement cardex;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' מאזן נוזלים ']")
    public WebElement fluidBalance;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' סיעוד ']")
    public WebElement nursing;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' עורך מסמכים ']")
    public WebElement documentEditor;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' הגדרת ייעדים ']")
    public WebElement goals;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' מנהל חולים ']")
    public WebElement hospitalDirector;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' דוחות ']")
    public WebElement reports;

    // just nursing

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-parent')]/span[text()=' אישור אחות ']")
    public WebElement nurseConfirmation;


// subcategories for doctorInstruction category

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='תרופות']")
    public WebElement  drugs;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='כלליות']")
    public WebElement  general;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='מוצרי דם']")
    public WebElement  bloodProduct;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='תזונה']")
    public WebElement  nutrition;




// subcategories for nursing category


    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='הוראות סיעודיות']")
    public WebElement  nursingIns;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='מעקב סיעודי']")
    public WebElement  nursingFollowup;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='נקזים וצנתרים']")
    public WebElement  catheters;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='פצעים']")
    public WebElement  wounds;


    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='אישור הוראות']")
    public WebElement  instructionConfirmation;

    @FindBy(how = How.XPATH ,using="//div[contains(@class,'list-group-item menu-child')]/span[text()='עדכון ביצועים']")
    public WebElement  updateExecution;


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
