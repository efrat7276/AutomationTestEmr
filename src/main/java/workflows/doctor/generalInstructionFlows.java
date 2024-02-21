package workflows.doctor;

import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pageObjects.emr.addForms.GeneralInstructionPage;
import utilities.CommonOps;
import utilities.ManageDDT;

import java.util.List;


public class generalInstructionFlows extends CommonOps {


    @Step(" go to newGeneral")
    public static void chooseGeneralInstruction(int categoryIndex, int subCategoryIndex ) throws InterruptedException {

        UIActions.click(generalInstructionPage.generalInstructionCategoryList.get(categoryIndex-1));
       // UIActions.click(generalInstructionPage.generalInsSubList.get(subCategoryIndex-1).findElement(By.tagName("input")));
        Thread.sleep(1000);
        UIActions.click(generalInstructionPage.generalInsSubList.get(subCategoryIndex-1));

        try { doctorFlows.confirmModalSameInstruction();}catch (Exception ex){}
    }

    @Step(" go to newGeneral")
    public static void chooseGeneralInstruction(String categoryDescription, String subCategoryDescription ) {
        UIActions.selectFromList(generalInstructionPage.generalInstructionCategoryList , categoryDescription);
        UIActions.selectFromListInsideAnotherTag(generalInstructionPage.generalInsSubList , subCategoryDescription);
    }

    @Step(" add details to generalIns")
    public static void addCommentToIns() {
        generalInstructionPage.listSelectedGeneralIns.get(0).findElement(By.name("itemDescrption")).sendKeys("בדיקות אוטו'");

    }


    @Step(" choose possibility daily generalIns")
    public static void choosePossibilityDaily(int numberOfTime) {

        UIActions.click(generalInstructionPage.btn_possbilities);
        UIActions.selectFromList(generalInstructionPage.possbilityList, "Daily");
        UIActions.click(generalInstructionPage.btn_numberOfTime);
        UIActions.selectFromList(generalInstructionPage.numberOfTimeList, String.valueOf(numberOfTime));
    }

    @Step(" choose possibility once-only generalIns")
    public static void choosePossibilityOnceOnly() {

        UIActions.click(generalInstructionPage.btn_possbilities);
        UIActions.selectFromList(generalInstructionPage.possbilityList, "Once Only");
    }



    @Step(" go to newGeneral")
    public static void saveGeneralInstructionsSelected()  {

        UIActions.click(generalInstructionPage.btn_save);
       // Thread.sleep(5000);
       // doctorFlows.approvalInstruction();

    }

    @Step("add generalIns to patient")
    public static void addAndSaveGeneralInsDailyToPatient(int numberOfTime , boolean isFuture) throws InterruptedException {

        doctorFlows.newGeneralIns();
        chooseGeneralInstruction(1,1);
        choosePossibilityDaily(numberOfTime);
        if(isFuture==true)
            doctorFlows.chooseFutureDay();
        addCommentToIns();

        Thread.sleep(1000);

        saveGeneralInstructionsSelected();
        Verifications.textIsContains(doctorInstructionPage.title , "הוראות רפואיות");
    }

    @Step("add generalIns to patient")
    public static void addAndSaveGeneralInsOnceOnlyToPatient() throws InterruptedException {

        doctorFlows.newGeneralIns();
        chooseGeneralInstruction(1,1);
        addCommentToIns();
        choosePossibilityOnceOnly();
        Thread.sleep(1000);

        saveGeneralInstructionsSelected();
        Verifications.textIsContains(doctorInstructionPage.title , "הוראות רפואיות");
    }




    //GeneralInstruction

    @Step("fill generalForm for addNewGeneralInsDaily")
    public static void generalFormAddGeneralInsDaily(int category, int sub_category  , int numberOfTime ,boolean isFuture , boolean withExecution , boolean addAndSave) throws InterruptedException {

        chooseGeneralInstruction(category, sub_category);
        generalInstructionPage.listSelectedGeneralIns.get(0).findElement(By.name("itemDescrption")).sendKeys("בדיקות אוטו'");
        choosePossibilityDaily(numberOfTime);
        if(isFuture==true)
            doctorFlows.chooseFutureDay();
        if(addAndSave)
            UIActions.click(generalInstructionPage.btn_save);

    }

    @Step("fill DrugForm for addNewGeneralOnceOnly")
    public static void generalFormAddGeneralInsOnceOnly(int category, int sub_category , String hour ,boolean isFuture, boolean addAndSave) throws InterruptedException {

        chooseGeneralInstruction(category, sub_category);
        generalInstructionPage.listSelectedGeneralIns.get(0).findElement(By.name("itemDescrption")).sendKeys("בדיקות אוטו'");
        if(hour!=null)
          choosePossibilityOnceOnly();
        if(isFuture==true)
            doctorFlows.chooseFutureDay();
        if(addAndSave)
            UIActions.click(generalInstructionPage.btn_save);

    }
//
//
//    @Step("choose future date")
//    public static void chooseFutureDate()  {
//        doctorFlows.chooseFutureDay();
//    }
}
