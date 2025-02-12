package RegressionTest.nursing;

import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.nurse.wouds.woundFlows;

public class woundsRegression extends CommonOps {


    int patient_num = 4 ;
    @Test(description ="test addition bedsore" )
    @Description("add to patient bedsore wound")
    public void test01_addToPatientBedsore() throws InterruptedException {

        WebFlows.login('n');
        WebFlows.patientBoxEntry(patient_num);

        // navigate to cardex page
        if(UIActions.isExist(cardexPage.i_arrow))
            UIActions.click(cardexPage.i_arrow);
        NavigateFlows.goToCategory("nursing");
        NavigateFlows.goToSubCategory("wounds");

        // add wound
        woundFlows.addNewWound("פצע לחץ",0,2,1);

        //verification
        Verifications.isElementDisplay(woundPage.button_addWound);

    }

    @Test(description ="test addition open surgical wound" )
    @Description("add to patient surgical wound")
    public void test01_addToPatientOpenSurgicalWound() throws InterruptedException {

        WebFlows.login('n');
        WebFlows.patientBoxEntry(patient_num);

        // navigate to cardex page
        if(UIActions.isExist(cardexPage.i_arrow))
            UIActions.click(cardexPage.i_arrow);
        NavigateFlows.goToCategory("nursing");
        NavigateFlows.goToSubCategory("wounds");

        // add wound
        woundFlows.addNewWound("פצע ניתוחי",1,0,0);

        //verification
        Verifications.isElementDisplay(woundPage.button_addWound);

    }

    @Test(description ="test addition open surgical wound" )
    @Description("add to patient surgical wound")
    public void test01_addToPatientClosedSurgicalWound() throws InterruptedException {

        WebFlows.login('n');
        WebFlows.patientBoxEntry(patient_num);

        // navigate to cardex page
        if(UIActions.isExist(cardexPage.i_arrow))
            UIActions.click(cardexPage.i_arrow);
        NavigateFlows.goToCategory("nursing");
        NavigateFlows.goToSubCategory("wounds");

        // add wound
        woundFlows.addNewWound("פצע ניתוחי",2,0,0);

        //verification
        Verifications.isElementDisplay(woundPage.button_addWound);

    }

    ////טסט לחבישות בפצע ניתוחי //

}
