package pageObjects.emr.nurse.approval;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class ApprovalInstructionPage {

    @FindBy(how = How.NAME , using = "//drug-liquidation//*[@name='drugRow1']//button[@class='btn-nurse-instructions']")
    public List<WebElement>  drugToApprovalList;

    @FindBy(how = How.NAME , using = "//drug-liquidation//*[@name='drugRow2']//td[@name='drugsCurrentDay']")
    public List<WebElement> drugHoursButtonCurrentDay;

    @FindBy(how = How.NAME , using = "drugRow2")
    public List<WebElement>  hoursApprovalList;

    @FindBy(how = How.NAME , using = "drugsCurrentDay")
    public List<WebElement>  currentDayList;

    @FindBy(how = How.NAME , using = "drugsRoutine")
    public List<WebElement>  routineList;

    //for daily 1 in day and once only

    @FindBy(how= How.XPATH , using = "//table[@class='table main-table liquidationDrug']//div[@id='div-group-current-day']//button")
    public List<WebElement>  btn_drug;

    @FindBy(how= How.XPATH , using = "//div[@id='div-group-current-day']//button/following-sibling::ul[@class='small_dr_button dropdown-menu show']/li")
    public List<WebElement>  ul_drug;


    @FindBy(how= How.XPATH , using = "//tr[@name='drugRow1']//button[@tabindex='5']")
    public List<WebElement>  btns_approveToDrug;

//  general-ins

    @FindBy(how = How.NAME , using = "//general-liquidation//*[@name='drugRow1']//button[@class='btn-nurse-instructions']")
    public List<WebElement>  generalToApprovalList;

    @FindBy(how = How.NAME , using = "//general-liquidation//*[@name='drugRow2']//td[@name='drugsCurrentDay']")
    public List<WebElement> genHoursButtonCurrentDay;

    @FindBy(how= How.XPATH , using = "//general-liquidation//table[@class='table main-table']//div[@id='div-group-current-day']//button")
    public List<WebElement>  btn_gen;

    @FindBy(how= How.XPATH , using = "//general-liquidation//table[@class='table main-table']//div[@id='div-group-current-day']//button/following-sibling::ul[@class='small_dr_button dropdown-menu show']/li")
    public List<WebElement>  ul_gen;
    // btn for daily 1 in day and once only

    @FindBy(how = How.ID , using = "approvalDrug")
    public WebElement btn_approvalDrug;


   @FindBy(how = How.XPATH , using ="//input[@type='checkbox']")
   public List<WebElement> days_checkBoxList;


   // hour-bar

   // solution
    @FindBy(how = How.XPATH , using ="//solution-liquidation//div[@class='timelineListFrame']")
    public List<WebElement> solution_scale_hourList;

    @FindBy(how = How.XPATH , using ="//solution-liquidation//div[@name='solutionInstructionsTypeListStatusId2']//div[@class='timelineListFrame']//div[contains(@class,'timeLineInToday')]")
    public List<WebElement> solution_scale_currentHourList;

    // bloodProd
    @FindBy(how = How.XPATH , using ="//blood-product-liquidation//div[@class='timelineListFrame']")
    public List<WebElement> bloodP_scale_hourList;

    @FindBy(how = How.XPATH , using ="//blood-product-liquidation//div[@name='solutionInstructionsTypeListStatusId2']//div[@class='timelineListFrame']//div[contains(@class,'timeLineInToday')]")
    public List<WebElement> bloodP_scale_currentHour;


    @FindBy(how = How.XPATH , using ="//div[@class='solutionBagSizeDynamicPopover']//button[@class='btn btn-default dropdown-toggle'][1]")
    public WebElement btn_bagsList;

    @FindBy(how = How.XPATH , using ="//div[@class='solutionBagSizeDynamicPopover']//button[@class='btn btn-default dropdown-toggle'][1]//following-sibling::ul/li")
    public List<WebElement> bagsList;


    @FindBy(how = How.XPATH , using ="//div[@class='solutionBagSize']/div[4]/button")
    public WebElement btn_V_liquid;


    @FindBy(how = How.XPATH , using ="//form[@name='popContentSolutionBagSizeCode']/div[@class='solutionBagSize']/div[2]/button")
    public WebElement btn_V_blood;

    // span

    @FindBy(how = How.XPATH, using="//button[@id='approvalDrug']/span[2]")
    public WebElement span_numberForApproval;

    //title

    @FindBy(how= How.CLASS_NAME , using = "navbar-text-s-heder")
    public List<WebElement>  title_instructionToApproval;

}
