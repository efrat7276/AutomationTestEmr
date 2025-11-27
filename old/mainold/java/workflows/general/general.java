package workflows.general;

import org.openqa.selenium.By;
import utilities.CommonOps;

public class general extends CommonOps {


    public static int searchDrugByName(String nameDrug) {
        String drugDisplay;
        int i;
        for (i = 0; i <= doctorInstructionPage.drugList.size(); i++) {
            drugDisplay= doctorInstructionPage.drugList.get(i).findElement(By.id("Drug Name")).getText();
            if (drugDisplay.contains(nameDrug))
                break;
        }
        return i;
        //UIActions.click(doctorInstructionPage.editIconList.get(i));

//        UIActions.clearText(drugForm.input_drugDosage);
//        UIActions.updateText(drugForm.input_drugDosage, String.valueOf(dosage));
//        UIActions.click(drugForm.btn_add);
//        approvalInstruction();
        //     Verifications
    }}
