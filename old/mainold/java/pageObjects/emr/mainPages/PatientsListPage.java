package pageObjects.emr.mainPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PatientsListPage {


    @FindBy(how= How.LINK_TEXT , using = "רשימת מטופלים")
    public WebElement menu_patientList;


    @FindBy(how= How.LINK_TEXT , using = "רשימת משוחררים")
    public WebElement menu_dischargeList;


    @FindBy(how= How.LINK_TEXT , using = "דוחות")
    public WebElement menu_reports;


    @FindBy(how= How.LINK_TEXT , using = "פרוטוקולים")
    public WebElement menu_protocols;

    @FindBy(how= How.XPATH , using = "//app-patients")
    public WebElement patients_body;

    @FindBy(how= How.XPATH , using = "//p-table[contains(@class,'depMeushpazim-table patients-table')]//tr")
    public List< WebElement> list_patients;

    @FindBy(how= How.ID , using = "bed_side_dropdown")
    public Select select_size_bed;

    @FindBy(how= How.ID , using = "status_dropdown")
    public WebElement select_status;

    @FindBy(how= How.ID , using = "alerts_dropdown")
    public WebElement select_alerts;

    @FindBy(how= How.CSS , using = "btn[title='רענן']")
    public WebElement btn_reload;

    @FindBy(how= How.XPATH , using = "//patients-filter//input")
    public WebElement txt_search;

    @FindBy(how= How.ID , using = "dropdownBasic1")
    public WebElement btn_listDepartment;

    @FindBy(how= How.XPATH , using = "//div[@class='line-number']//span")
    public WebElement span_patientNumber;
    //span

    @FindBy(how= How.XPATH , using = "//button[@dontlock='true']/span")
    public WebElement span_nameDepartment;

    @FindBy(how= How.XPATH , using = "//ul[contains(@class,'navbar-nav')]/li[5]//span[5]")
    public WebElement nameUserConnect;


}
