package pages.icu.patientSheet;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class PatientSheetMain {

//    @FindBy(how=How.ID, using="open-nutrition-pln")
//    public WebElement div_nutritionPlan;

    @FindBy(how=How.XPATH, using="//table[@id='deviceToPatientLogType']//tr[contains(@id,'deviceToPatientLogType_tr_result')]")
    public WebElement rowTable_devicesResult;

    @FindBy(how=How.XPATH, using="//table[@id='deviceToPatientLogType']//tr[contains(@id,'deviceToPatientLogType_tr_result')]//td")
    public List< WebElement> cell_devicesResult;

    @FindBy(how=How.XPATH, using="//table[@id='deviceToPatientLogType']//tr[contains(@id,'deviceToPatientLogType_tr_result')]//td[contains(@class,'nowColor')]")
    public List< WebElement> cell_devicesResult_nowColum;

    @FindBy(how=How.XPATH, using="//table[@id='deviceToPatientLogType']//tr[contains(@id,'deviceToPatientLogType_tr_result')]//td[contains(@class,'nowColor')]//following-sibling::td")
    public List< WebElement> cell_devicesResult_propertiesColum;


    @FindBy(how=How.ID, using="ngb-popover-36567")
    public  WebElement popUp_actions;

    @FindBy(how=How.XPATH, using="//ngb-popover-window//ul//li[1]")
    public  WebElement li_confirmAction;

    @FindBy(how=How.XPATH, using="//ngb-popover-window//ul//li[2]")
    public  WebElement li_errorAction;

    @FindBy(how=How.XPATH, using="//ngb-popover-window//ul//li[3]")
    public  WebElement li_comment;

    @FindBy(how=How.XPATH, using="//ngb-popover-window//textarea[@id='actionRemark']")
    public  WebElement textInput_comment;

    @FindBy(how=How.XPATH, using="//ngb-popover-window//ul//li[4]")
    public  WebElement li_history;

    @FindBy(how=How.XPATH, using="//ngb-popover-window//button[2]")
    public  WebElement button_ok;


    @FindBy(how=How.XPATH, using="//ngb-popover-window//button[1]")
    public  WebElement button_cancel;
    @FindBy(how=How.XPATH, using="//ngb-popover-window//input")
    public  WebElement checkBox_markAsError;

}


