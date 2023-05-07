package workflows.doctor;
import com.google.common.util.concurrent.Uninterruptibles;
import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.emr.addForms.DrugForm;
import sun.awt.windows.ThemeReader;
import utilities.CommonOps;
import workflows.WebFlows;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class doctorFlows extends CommonOps {

    @Step(" click on newDrug")
    public static void newDrug(){

        UIActions.click(doctorInstructionPage.btns_addDrug.get(0));
        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
    }

    @Step(" click on newGeneralInstruction")
    public static void newGeneralIns(){

        doctorInstructionPage.btn_addGeneralIns.click();
      //  Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
    }

    @Step(" click on new blood product")
    public static void newBloodProduct(){

        doctorInstructionPage.btn_addBloodProd.click();
//        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

    }

    @Step(" click on newNutrition")
    public static void newNutrition(){

        doctorInstructionPage.btn_addNutrition.click();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
    }

    @Step(" click on ReturnAndApproval")
    public static void clickReturnAndApproval(){

      UIActions.click( drugForm.btn_undo);
        UIActions.click(doctorInstructionPage.btn_approvalDrug);
    }

    @Step(" click on return")
    public static void clickReturn(){

        UIActions.click( drugForm.btn_undo);
    }

    @Step(" click on approvalInstructions")
    public static void approvalInstruction(){

      UIActions.waitForDigit(doctorInstructionPage.btn_approvalDrug.findElements(By.tagName("span")).get(0));
      WebFlows.userSignConfirm();

    }

    @Step(" stop all active instruction to patient")
    public static void stopAllActiveInstructionToPatient(){

        int insToStop =  doctorInstructionPage.stopIconList.size();
        if(insToStop == 0 )
            return;
        for(int i=0; i< insToStop ; i++)
        {
            Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
            UIActions.click(doctorInstructionPage.stopIconList.get(0));
        }
        approvalInstruction();
    }




    @Step(" click edit on the top patient's instruction ")
    public static void editDrug() throws InterruptedException {


        UIActions.click(doctorInstructionPage.editIconList.get(0));
        Thread.sleep(5000);
        Verifications.isElementDisplay(drugForm.inp_selectDrug);

        // approvalInstruction();
    }

   // @Step(" click edit on drug by name param ")


    @Step(" click edit on the top patient's instruction ")
    public static void editDrugDosage(int drug_index ,int dosage) throws InterruptedException {

        UIActions.click(doctorInstructionPage.editIconList.get(drug_index));
        Thread.sleep(2000);
        UIActions.clearText(drugForm.input_drugDosage);
        UIActions.updateText(drugForm.input_drugDosage , String.valueOf(dosage));
        UIActions.click(drugForm.btn_add);
       // approvalInstruction();
   //     Verifications.

    }

    @Step(" click edit on the top patient's instruction ")
    public static void editDrugNumberOfTime(int drug_index, int numberPerDay)  {

        UIActions.click(doctorInstructionPage.editIconList.get(drug_index));
        UIActions.click(drugFormDailyPossibility.btn_numberOfTimesDaily);
        UIActions.selectFromList(drugFormDailyPossibility.numberOfTimesDaily , String.valueOf(numberPerDay));
        UIActions.click(drugForm.btn_add);
        approvalInstruction();
        // Verifications

    }

    @Step(" Fill Drug's Details")
    public static void fillDrugsDetails(String drugName ,@Nullable String dosage ,@Nullable  String routeAdministration ){
        wait.until(ExpectedConditions.textToBePresentInElement(drugForm.inp_selectDrug , ""));
        drugForm.inp_selectDrug.sendKeys(drugName);
        drugForm.inp_selectDrugTopList.click();

        try{ confirmModalSameInstruction();}catch (Exception ex){}

        if(dosage!=null)
        {
            drugForm.input_drugDosage.clear();
            drugForm.input_drugDosage.sendKeys(dosage);
        }
        if(routeAdministration!=null) {
             drugForm.btn_dropdownRouteAdministration.click();

         UIActions.selectFromListInsideAnotherTag(drugForm.routeAdministrationList, routeAdministration); }
    }

    @Step(" Fill Unknown Drug's Details")
    public static void fillUnknownDrugDetails(String drugName ,int isAddAfterAdd, String dosage ,String units , String routeAdministration ){

        if(isAddAfterAdd==0)
            Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        if(isAddAfterAdd==1)
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        UIActions.updateText(drugForm.inp_selectDrug, "UNKNOWN");
        UIActions.click(drugForm.inp_selectDrugTopList);
        UIActions.updateText(drugForm.inp_unknownDrug, drugName);
        UIActions.updateText(drugForm.input_drugDosage, dosage);
        UIActions.click(drugForm.btn_unitMeasure);
        UIActions.selectFromList(drugForm.unitMeasureList, units);
        UIActions.click(drugForm.btn_dropdownRouteAdministration);
        UIActions.selectFromListInsideAnotherTag(drugForm.routeAdministrationList, routeAdministration);
    }

    @Step(" Fill Details In Daily")
    public static void fillDrugNumberOfTimes( int numberOfTimeDaily ) {


        drugFormDailyPossibility.btn_numberOfTimesDaily.click();
        drugFormDailyPossibility.numberOfTimesDaily.get((numberOfTimeDaily)-1).click();

    }

    @Step(" choose a future day")
    public static void chooseFutureDay() {
        // choose the first day is enable in calender
        UIActions.click(drugFormDailyPossibility.btn_datePicker);
        UIActions.click(drugFormDailyPossibility.enableDays_datePicker.get(0));
    }

    @Step()
    public static void fillWithExecute() {

        UIActions.click(drugForm.checkbox_executed);
    }

    @Step(" Fill Hour In OnceOnly")
    public static void fillDrugOnceOnlyHour( String hour ) {

        if(hour!= "ללא") {
            drugFormOnceOnlyPossibility.btn_hour.click();
            UIActions.selectFromListInsideAnotherTag( drugFormOnceOnlyPossibility.hourList, hour);
        }
    }

    @Step(" Fill by hour details")
    public static void fillEveryXHour( String everyXHour ) {

        drugFormByHourPossibility.btn_everyXTime.click();
        UIActions.selectFromListByIndex( drugFormByHourPossibility.everyXTimeList, 1);
    }


    @Step(" Fill Hour In OnceOnly")
    public static void fillDrugSOSDetails(int max , int min ) {

       UIActions.click(drugFormSOSPossibility.btn_sosMaxTimesPerDay);
       UIActions.selectFromList( drugFormSOSPossibility.sosMaxTimesPerDayList, String.valueOf(max));
       UIActions.click(drugFormSOSPossibility.btn_sosMinTimesPerDay);
       UIActions.selectFromListInsideAnotherTag( drugFormSOSPossibility.sosMinTimesPerDayList, String.valueOf(min));
    }

    @Step(" Fill Hour In OnceOnly")
    public static void fillDrugWeeklyDetails(int timePerWeek) {

        UIActions.click(drugFormWeeklyPossibility.btn_WeekNumberOfTimes);
        UIActions.selectFromList( drugFormWeeklyPossibility.weekNumberOfTimesList , String.valueOf(timePerWeek));

    }

    @Step(" Fill duration")
    public static void fillDrugDilutedDetails(int duration) {

        UIActions.click(drugFormTimeLimitPossibility.btn_duration);
        UIActions.selectFromList(drugFormTimeLimitPossibility.durationList, String.valueOf(duration));
    }

    @Step(" Fill rate")
    public static void fillDrugContinuesDetails(int rate) {

        UIActions.updateText(drugFormContinuesPossibility.inp_rate, String.valueOf(rate));

    }

    @Step(" fill DrugForm for addNewDrugOnceOnly")
    public static void drugFormAddDrugOnceOnly(String drugName ,String dosage, String hour , String routeAdinistration , boolean withExecute , boolean addAndClose){

        fillDrugsDetails(drugName, dosage,routeAdinistration);
        drugForm.radio_onceOnlyPossbility.click();
        if(hour!=null)  fillDrugOnceOnlyHour(hour);
        UIActions.updateText(drugForm.input_drugComment, "בדיקות אוטו'");
        if(withExecute)  fillWithExecute();
        if(addAndClose)
           drugForm.btn_addAndClose.click();
       else
        drugForm.btn_add.click();
    }

    @Step(" fill DrugForm for addNewDrugDaily")
    public static void drugFormAddDrugDaily(String drugName , int dosage,  int numberOfTimeDaily, String routeAdinistration, boolean isAntibiotic , boolean isFuture , boolean addAndClose){

        fillDrugsDetails(drugName,String.valueOf(dosage),routeAdinistration);
        UIActions.updateText(drugForm.input_drugComment, "בדיקות אוטו'");
        if(isAntibiotic == true )
            fillDiagnosisToAntibiticDrug();
        fillDrugNumberOfTimes(numberOfTimeDaily);
        if(isFuture == true)
            chooseFutureDay();

        if(addAndClose)
            drugForm.btn_addAndClose.click();
        else
            drugForm.btn_add.click();
    }


    @Step(" fill DrugForm for addNewDrugSOS")
    public static void drugFormAddDrugSOS(String drugName ,String dosage, String routeAdinistration  , int max , int min,boolean addAndClose ){

        fillDrugsDetails(drugName,dosage,routeAdinistration);
        UIActions.updateText(drugForm.input_drugComment, "בדיקות אוטו'");
        drugForm.radio_sosPossbility.click();
        fillDrugSOSDetails(max, min);
        if(addAndClose)
            drugForm.btn_addAndClose.click();
        else
            drugForm.btn_add.click();
    }


    @Step(" Fill DrugForm for addNewDrugByHour")
    public static void drugFormAddDrugByHour(String drugName ,int everyXHour , String dosage, String routeAdinistration,boolean addAndClose ){

        fillDrugsDetails(drugName,dosage,routeAdinistration);
        drugForm.radio_byHourPossbility.click();
        UIActions.updateText(drugForm.input_drugComment, "בדיקות אוטו'");
        fillEveryXHour(String.valueOf(everyXHour));
        if(addAndClose)
            drugForm.btn_addAndClose.click();
        else
            drugForm.btn_add.click();
    }

    @Step(" Fill DrugForm for addNewDrugWeekly")
    public static void drugFormAddDrugWeekly(String drugName ,int timePerWeek , String dosage, String routeAdinistration ,boolean addAndClose){

        fillDrugsDetails(drugName,dosage,routeAdinistration);
        drugForm.radio_WeeklyPossbility.click();
        UIActions.updateText(drugForm.input_drugComment, "בדיקות אוטו'");
        fillDrugWeeklyDetails(timePerWeek);
        if(addAndClose)
            drugForm.btn_addAndClose.click();
        else
            drugForm.btn_add.click();
    }

    @Step(" Fill DrugForm for addNewDrugDiluted")
    public static void drugFormAddLiquidDrug(String drugName,@Nullable String dilutedName ,int possId ,boolean addAndClose  ){

        drugForm.inp_selectDrug.sendKeys(drugName);
        drugForm.inp_selectDrugTopList.click();
        try{ confirmModalSameInstruction();}catch (Exception ex){}
        if(dilutedName != null) {
            drugForm.btn_diluted.click();
            UIActions.selectFromListInsideAnotherTag(drugForm.dilutedList, dilutedName);
        }
        UIActions.updateText(drugForm.input_drugComment, "בדיקות אוטו'");


        switch (possId){

            case 1 :
                drugForm.radio_dailyPossbility.click();
                fillDrugNumberOfTimes(1);
                 break;
            case 2 :
                drugForm.radio_sosPossbility.click();
                fillDrugSOSDetails(3,5);
                break;
            case 3 :
                drugForm.radio_onceOnlyPossbility.click();
                break;
            case 11 :
                drugForm.radio_timeLimitPossbility.click();
                fillDrugDilutedDetails(24);
                break;
            case 12 :
                drugForm.radio_continuousPossbility.click();
                fillDrugContinuesDetails(20);
                break;

        }
        if(addAndClose)
            drugForm.btn_addAndClose.click();
        else
            drugForm.btn_add.click();
    }

//    @Step(" Fill DrugForm for addNewDrugContinues")
//    public static void drugFormAddDrugContinues(String drugName ,int rate , boolean addAndClose){
//
//        drugForm.inp_selectDrug.sendKeys(drugName);
//        drugForm.inp_selectDrugTopList.click();
//
//        try{ confirmModalSameInstruction();}catch (Exception ex){}
//        drugForm.radio_continuousPossbility.click();
//        UIActions.updateText(drugForm.input_drugComment, "בדיקות אוטו'");
//        fillDrugContinuesDetails(rate);
//        if(addAndClose)
//            drugForm.btn_addAndClose.click();
//        else
//            drugForm.btn_add.click();
//    }


    @Step(" fill diagnosis to antibiotic drug")
    public static void fillDiagnosisToAntibiticDrug(){

        UIActions.click(drugForm.firstCategoryDiagnosis);
        UIActions.selectFromListByIndex(drugForm.firstDiagnosisInFirstCategory,1);

    }


    @Step(" fill DrugForm for addUnknownDrug")
    public static void drugFormAddUnknownDrug(boolean addAndClose){

        fillUnknownDrugDetails("unknown drug", 0 , "50" , "ml" ,"PO" );
        fillDrugNumberOfTimes(1);
        if(addAndClose)
            drugForm.btn_addAndClose.click();
        else
            drugForm.btn_add.click();
    }

    @Step(" confirm same instruction")
    public static void confirmModalSameInstruction(){

        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        drugForm.btn_modalOK.click();

    }

    @Step("fill infection confirmation to drug by doctor")
    public static void confirmationInfectionToDrugFromInstructionPage(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddHHmm");
        LocalDateTime now = LocalDateTime.now();
        String confirmationKey="20"+dtf.format(now).toString();
        UIActions.click(doctorInstructionPage.requireConfirmationIcon);
        UIActions.updateText(drugConfirmation.confKey_inp , confirmationKey);
        UIActions.click(drugConfirmation.letter_buttonList);
        UIActions.click(drugConfirmation.letter_List.get(0));
        UIActions.click(drugConfirmation.save_button);

        approvalInstruction();
      //  Verifications.isElementDisplay(d);
    }




}
