package workflows.nurse.wouds;
import extensions.UIActions;
import io.qameta.allure.Step;
import utilities.CommonOps;
import workflows.WebFlows;

import javax.annotation.Nullable;

public class woundFlows extends CommonOps {


    @Step("add new wound bedsore")
    public static void addNewWound(String woundDescription,@Nullable int closeOrOpen  , @Nullable int  degree ,@Nullable int treatmentInstructionCount) throws InterruptedException {

        UIActions.click(woundPage.button_addWound);
        Thread.sleep(500);
        UIActions.click(woundFormPage.button_woundToAddList);
        UIActions.selectFromList(woundFormPage.options_woundType, woundDescription);
        if (woundDescription == "פצע ניתוחי")
            surgicalWoundFlows.closedOrOpenAndLocation(closeOrOpen);
        else {
            UIActions.click(woundFormPage.dropdown_woundLocation);
            UIActions.click(woundFormPage.options_woundLocation.get(1));
            UIActions.click(woundFormPage.dropdown_woundSide);
            UIActions.click(woundFormPage.option_woundSide);


        }

        if (woundDescription == "פצע לחץ"){
          if(degree == 1 || degree==2)
              bedsoreWoundFlows.addDegree2WoundForBedsore();
    }
        addAnsSaveWound();
    }








    public static void addAnsSaveWound() throws InterruptedException {

        UIActions.click(woundPage.button_saveWound);
        WebFlows.userSignConfirm();
    }

}
