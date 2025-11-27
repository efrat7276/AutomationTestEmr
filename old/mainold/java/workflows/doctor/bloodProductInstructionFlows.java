package workflows.doctor;

import extensions.UIActions;
import io.qameta.allure.Step;

import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.CommonOps;

public class bloodProductInstructionFlows extends CommonOps {



    @Step(" fill bloodProductForm")
    public static void bloodProdFormAddABloodProduct(boolean addAndClose) {


    }

    @Step("add blood product")
    public static void bloodProductFormAddBloodProduct(boolean addAndClose) {
        UIActions.click(bloodProducts.btn_bloodProductList);
        UIActions.selectFromListByIndex(bloodProducts.bloodProductList, 3);

       // UIActions.click(bloodProducts.btn_solutionBagSizeList);
      //  UIActions.selectFromListByIndex(bloodProducts.solutionBagSizeList, 1);
        UIActions.updateText(bloodProducts.input_amount , "3");
        UIActions.updateText(bloodProducts.inp_comment, "בדיקות אוטו'");
        if(addAndClose)
           wait.until(ExpectedConditions.visibilityOf(  drugForm.btn_addAndClose)) .click();
        else
            drugForm.btn_add.click();
       // bloodProdFormAddABloodProduct(false);


        // למחלקת ילדים יש הוספה..
    }



}
