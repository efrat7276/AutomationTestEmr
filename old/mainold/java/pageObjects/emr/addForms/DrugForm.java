package pageObjects.emr.addForms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DrugForm {

    //inputs

    @FindBy(how = How.ID, using = "selectDrug")
    public WebElement inp_selectDrug;

    @FindBy(how = How.XPATH, using = "//div[@class='form-group code-list']//button")
    public WebElement inp_selectDrugTopList;

    @FindBy(how = How.ID, using = "UnknownDrug")
    public WebElement inp_unknownDrug;

    @FindBy(how = How.ID, using = "drugDosage")
    public WebElement input_drugDosage;


    @FindBy(how = How.ID, using = "drugComment")
    public WebElement input_drugComment;

    @FindBy(how = How.XPATH, using = "div[@class='dir_rtl text-right']")
    public WebElement otherSymbolises;

    // checkboxes

    @FindBy(how = How.XPATH, using = "//div[@class='hospital']/input")
    public WebElement checkbox_DepartmentalStandard;

    @FindBy(how = How.ID, using = "instructionWithExecution")
    public WebElement checkbox_executed;


    // dropdown

    @FindBy(how = How.ID, using = "dropdownDrugUnitMeasure")
    public WebElement btn_unitMeasure;


    @FindBy(how = How.XPATH, using = "//button[@id='btnSelectListDilutionSolution']//following-sibling::ul/li")
    public List<WebElement> dilutedList;

    //:יש גרסאות שזה ככה
//
//    @FindBy(how = How.XPATH, using = "//div[@class='dropdown']//following-sibling::ul/li")
//    public List<WebElement> dilutedList;


    @FindBy(how = How.ID, using = "btnSelectListDilutionSolution")
    public WebElement btn_diluted;


    // גרסת QA


//    @FindBy(how = How.XPATH, using = "//input[@id='typeahead-focus']//following-sibling::ngb-typeahead-window/button")
//    public List<WebElement> dilutedList;
//
//    @FindBy(how = How.ID, using = "typeahead-focus")
//    public WebElement btn_diluted;


    @FindBy(how = How.XPATH, using = "//button[@id='dropdownDrugUnitMeasure']/following-sibling::ul/li")
    public List<WebElement> unitMeasureList;

    @FindBy(how = How.ID, using = "dropdownRouteAdministrationID")
    public WebElement btn_dropdownRouteAdministration;


    @FindBy(how = How.XPATH, using = "//*[@id='dropdownRouteAdministrationID']/span")
    public WebElement span_btn_dropdownRouteAdministration;

    @FindBy(how = How.XPATH, using = "//button[@id='dropdownRouteAdministrationID']/following-sibling::ul/li")
    public List<WebElement> routeAdministrationList;

    //datePicker

//    @FindBy(how = How.ID, using = "btnUndo")
//    public WebElement btn_datePickerStartDay;


    // buttons

    @FindBy(how = How.ID, using = "btnUndo")
    public WebElement btn_undo;

    @FindBy(how = How.ID, using = "btnAdd")
    public WebElement btn_add;

    @FindBy(how = How.ID, using = "btnAddAndClose")
    public WebElement btn_addAndClose;


    @FindBy(how = How.XPATH, using = "//button[@id='approvalDrug']/span[1]")
    public WebElement btn_approval_span;

    @FindBy(how = How.ID, using = "approvalDrug")
    public WebElement btn_approval;

    // divs

    @FindBy(how = How.XPATH, using = "//div[@class='border-container newDrug']/div")
    public List<WebElement> div_newDrugs;

    //possibility
    @FindBy(how = How.XPATH, using = "//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Daily ']")
    public WebElement possibilityDaily;

    @FindBy(how = How.XPATH, using = "//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' SOS ']")
    public WebElement possibilitySOS;
    @FindBy(how = How.XPATH, using = "//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Once Only ']")
    public WebElement possibilityOnceOnly;
    @FindBy(how = How.XPATH, using = "//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' By Hour ']")
    public WebElement possibilityByHour;
    @FindBy(how = How.XPATH, using = "//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Weekly ']")
    public WebElement possibilityWeekly;
    @FindBy(how = How.XPATH, using = "//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Cycle ']")
    public WebElement possibilityCycle;
    @FindBy(how = How.XPATH, using = "//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Time Limit ']")
    public WebElement possibilityTimeLimit;
    @FindBy(how = How.XPATH, using = "//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Continuous ']")
    public WebElement possibilityContinuous ;

    @FindBy(how = How.XPATH, using = "//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Constant Rate ']")
    public WebElement possibilityConstantRate ;

    //modals

    @FindBy(how = How.XPATH, using = "//div[@class='modal-content']")
    public WebElement modal;

    @FindBy(how = How.ID, using = "buttonImport")
    public WebElement btn_modalOK;

    @FindBy(how = How.ID, using = "buttonCancle")
    public WebElement btn_modalCancle;

    //

    // diagnosis

    @FindBy(how = How.XPATH , using = "//*[@id='0}-header']/div/button")
    public WebElement firstCategoryDiagnosis;

    @FindBy(how = How.XPATH , using = "//*[@id='0}']/div/div")
    public List< WebElement> firstDiagnosisInFirstCategory;

}