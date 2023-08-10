package pageObjects.emr.receptionTreatmentPlan;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class NutritionPlan {

    //כלכלה

    @FindBy(how=How.ID, using="nutrition")
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

    @FindBy(how=How.ID, using="nutGlu")
    public WebElement radio_glucose;

    @FindBy(how=How.ID, using="glucos-inp")
    public WebElement input_glucose;

    @FindBy(how=How.ID, using="glu-rate")
    public WebElement input_glu_continous;

     // תוספת חלבון
    @FindBy(how=How.ID, using="isProtein-1")
    public WebElement isProteinYes;

    @FindBy(how=How.ID, using="isProtein-0")
    public WebElement isProteinNo;

   // תוספת מי אורז

    @FindBy(how=How.ID, using="isRiceWater-1")
    public WebElement isRiceWaterYes;

    @FindBy(how=How.ID, using="isRiceWater-0")
    public WebElement isRiceWaterNo;


}


