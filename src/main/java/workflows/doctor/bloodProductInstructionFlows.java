package workflows.doctor;

import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Step;
import pageObjects.emr.addForms.BloodProducts;
import utilities.CommonOps;

public class bloodProductInstructionFlows extends CommonOps {



    @Step(" fill bloodProductForm")
    public static void bloodProdFormAddABloodProduct(boolean addAndClose) {


    }

    @Step("add blood product")
    public static void bloodProductFormAddBloodProduct(boolean addAndClose) {
        UIActions.click(bloodProducts.btn_bloodProductList);
        UIActions.selectFromListByIndex(bloodProducts.bloodProductList, 3);
        UIActions.updateText(bloodProducts.inp_comment, "בדיקות אוטו'");
        UIActions.click(bloodProducts.btn_solutionBagSizeList);
        UIActions.selectFromListByIndex(bloodProducts.solutionBagSizeList, 1);
        if(addAndClose)
            drugForm.btn_addAndClose.click();
        else
            drugForm.btn_add.click();
        bloodProdFormAddABloodProduct(false);


        // למחלקת ילדים יש הוספה..
    }



}
