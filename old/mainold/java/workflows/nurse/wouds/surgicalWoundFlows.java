package workflows.nurse.wouds;
import extensions.UIActions;
import io.qameta.allure.Step;
import utilities.CommonOps;

public class surgicalWoundFlows extends CommonOps {



    @Step("close or open ")
    public static void closedOrOpenAndLocation(int closedOrOpen) throws InterruptedException {
        if(closedOrOpen==1) {
            UIActions.click(surgicalWoundMoreFieldsToAddPage.inp_openedWound);
            UIActions.click(woundFormPage.button_woundColor);
            UIActions.click(woundFormPage.option_woundColor.get(1));
            UIActions.click(woundFormPage.button_provisionAmount);
            UIActions.click(woundFormPage.option_provisionAmount.get(1));
            UIActions.click(woundFormPage.button_provisionType);
            UIActions.click(woundFormPage.option_provisionType.get(1));
            UIActions.click(woundFormPage.button_skinAround);
            UIActions.click(woundFormPage.option_skinAround.get(1));
            UIActions.click(woundFormPage.button_skinAround);
            UIActions.updateText(surgicalWoundMoreFieldsToAddPage.txt_comment, "הערה פצע ניתוחי בטסט אוטומטי");
        }
        else {
            UIActions.click(surgicalWoundMoreFieldsToAddPage.inp_closedWound);
            UIActions.click(surgicalWoundMoreFieldsToAddPage.btn_closeOfEdges);
            UIActions.click(surgicalWoundMoreFieldsToAddPage.option_closeOfEdges.get(2));
     //      UIActions.click(surgicalWoundMoreFieldsToAddPage.dropdown_marginalCondition);
      //      UIActions.click(surgicalWoundMoreFieldsToAddPage.options_marginalCondition.get(1));
            UIActions.click(surgicalWoundMoreFieldsToAddPage.dropdown_amountOfSecretion);
            UIActions.click(surgicalWoundMoreFieldsToAddPage.options_amountOfSecretion.get(1));

        }
        //

        //surgical wound location

        UIActions.updateText(surgicalWoundMoreFieldsToAddPage.location,"מיקום פצע בהוספה בטסט אוטומטי");


    }

    public static void addTreatmentInstruction() throws InterruptedException {


    }

}
