package pageObjects.emr.receptionTreatmentPlan;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pageObjects.emr.generalComponents.NumberOfTimeListComponent;
import utilities.Base;

import java.util.List;

public class NutritionPlan extends Base {

//    public WebElement[][] dailyList ;
//
//    public WebElement[][] getDailyList() {
//        dailyList= new WebElement[][];
//        return dailyList;
//    }
//
//   public int getCountList(){
//
//       return driver.findElement().getSize();
//       // return 1;
 //   }
    //כלכלה

    @FindBy(how=How.ID, using="glucos")
    public WebElement radio_economy;

    @FindBy(how=How.ID, using="nutrition-inp")
    public WebElement input_economyDesc;

    @FindBy(how=How.ID, using="nut-rate")
    public WebElement input_nut_continous;

    @FindBy(how=How.ID, using="water-rate")
    public WebElement checkBox_water;

    @FindBy(how=How.ID, using="water-item")
    public WebElement input_water_rate;


    //גלוקוז

    @FindBy(how=How.ID, using="nutrition")
    public WebElement radio_glucos;

    @FindBy(how=How.ID, using="glucos-inp")
    public WebElement input_glucos;

    @FindBy(how=How.ID, using="glu-rate")
    public WebElement input_glu_continous;

     // תוספת חלבון
    @FindBy(how=How.ID, using="isProtein-1")
    public WebElement isProteinYes;

    @FindBy(how=How.ID, using="isProtein-0")
    public WebElement isProteinNo;

    @FindBy(how=How.ID, using="protein-inp")
    public WebElement input_protein;

    @FindBy(how=How.ID, using="isProtein-dosage")
    public WebElement input_protein_dosage;


    @FindBy(how=How.ID, using="isProtein-comment")
    public WebElement input_proteinComment;

   // תוספת מי אורז

    @FindBy(how=How.ID, using="isRiceWater-1")
    public WebElement isRiceWaterYes;

    @FindBy(how=How.ID, using="isRiceWater-0")
    public WebElement isRiceWaterNo;

    @FindBy(how=How.ID, using="isRiceWater-comment")
    public WebElement input_RiceWaterComment;

    // IV

    @FindBy(how=How.ID, using="fluids-inp")
    public WebElement input_solution;

    // רשימה של מספר פעמים ביום
//
//    @FindBy(how=How.XPATH, using="//button[@name='numberOfTimes_daily']/following-sibling::ul")
//    public List<WebElement> list_numberOfTime;

    @FindBy(how=How.NAME, using="numberOfTimes_daily")
    public List<WebElement> list_numberOfTime;

}


