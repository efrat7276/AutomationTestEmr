package workflows.nurse.wouds;
import extensions.UIActions;
import io.qameta.allure.Step;
import utilities.CommonOps;
import workflows.doctor.generalInstructionFlows;

public class bedsoreWoundFlows extends CommonOps {



    @Step("add degree wound bedsore")
    public static void addDegree2WoundForBedsore() throws InterruptedException {

        UIActions.click(bedsoreMoreFieldsToAddPage.button_grade);
        UIActions.click(bedsoreMoreFieldsToAddPage.option_woundDegree.get(1));

        UIActions.click(bedsoreMoreFieldsToAddPage.button_woundTissueDescription);
        UIActions.click(bedsoreMoreFieldsToAddPage.option_woundMultiSelect.get(1));
        UIActions.click(bedsoreMoreFieldsToAddPage.button_woundTissueDescription);

        UIActions.click(woundFormPage.button_woundColor);
        UIActions.click(woundFormPage.option_woundColor.get(1));

        UIActions.click(woundFormPage.button_provisionAmount);
        UIActions.click(woundFormPage.option_provisionAmount.get(1));

//        Thread.sleep(1000);
//
        UIActions.click(woundFormPage.button_provisionType);
        UIActions.click(woundFormPage.option_provisionType.get(2));

        UIActions.updateText(woundFormPage.input_provisionTypeComment,"10");

        UIActions.click(woundFormPage.button_skinAround);
        UIActions.click(woundFormPage.option_skinAround.get(1));
        UIActions.click(woundFormPage.button_skinAround);

        addTreatmentInstruction();
    }

    public static void addTreatmentInstruction() throws InterruptedException {

        generalInstructionFlows.generalFormAddGeneralInsDaily(1, 1, 1, false, false, false);
    }

}
