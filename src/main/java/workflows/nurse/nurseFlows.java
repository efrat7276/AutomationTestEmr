package workflows.nurse;
import com.google.common.util.concurrent.Uninterruptibles;
import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.CommonOps;
import workflows.WebFlows;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class nurseFlows extends CommonOps {

    @Step("approval all instruction")
    public static void approvalAllPossibilitiesIns(boolean chooseHourCurrentDay, boolean existWeeklyDrug) throws InterruptedException, IOException {

        Date d = new Date();
        int today = d.getDay() + 1;
        int drugs_count = 0, gen_count = 0, liquid_count = 0, blood_count = 0;

        //     Thread.sleep(10000);

        // האם צריך לבחור שעות ליום הנוכחי תרופות והוראות כלליות
        if (chooseHourCurrentDay) {

            //תרופות
            //  drugs_count = 5;
            if (approvalInstructionPage.btn_drug.size() > 0) {
                for (int i = 0; i < approvalInstructionPage.btns_approveDrug.size(); i++) {
                   try {
                       UIActions.click(approvalInstructionPage.btn_drug.get(i));
                       UIActions.click(approvalInstructionPage.ul_drug.get(4));
                   }
                   catch (Exception error){ }
                    // הוראה שבועית של שבועיים
                    if (approvalInstructionPage.possibilityDescDrug.get(i).getText().contains("Week")) {
                        int arrDaysNew[] = fillArrToDaysInWeek(today);
                        for (int j = 0; j < 2; j++) {
                            UIActions.click(approvalInstructionPage.days_checkBoxList.get(arrDaysNew[j]));
                        }
                    }
                    UIActions.click(approvalInstructionPage.btns_approveDrug.get(i));
                }
            }

            //הוראות כלליות

            //  gen_count = 2;
            // if (gen_count > 0) {
            for (int i = 0; i < approvalInstructionPage.btns_approveGeneral.size(); i++) {
                UIActions.click(approvalInstructionPage.btn_gen.get(i));
                UIActions.click(approvalInstructionPage.ul_gen.get(4));
                UIActions.click(approvalInstructionPage.btns_approveGeneral.get(i));
            }
            // }


            // נוזלים

            for (int i = 0; i < approvalInstructionPage.btns_approveSolution.size(); i++) {
                if(i==1)
                    Thread.sleep(500);
              //  FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("C:\\Automation\\AutomationProject_emr\\temp\\"+getFileName("nurseApprovalSolution")+".png"));
                UIActions.click(approvalInstructionPage.solution_scale_currentHourList.get(i));
                // not relevant to continues
                try {

                    // מדובר רק בהוראות לנוזלים time limit
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='solutionBagSizeDynamicPopover']/button")));
                    UIActions.click(approvalInstructionPage.btn_V_liquid);

                } catch (Exception e) {
                }
                UIActions.click(approvalInstructionPage.btns_approveSolution.get(i));

            }

            Thread.sleep(1000);
            // מוצרי דם

            for (int i = 0; i < approvalInstructionPage.btns_approveBloodProduct.size(); i++) {
                UIActions.click(approvalInstructionPage.bloodP_scale_currentHour.get(i));
                Thread.sleep(1000);
                UIActions.click(approvalInstructionPage.btn_V_blood);
                UIActions.click(approvalInstructionPage.btns_approveBloodProduct.get(i));

            }

        }
    }

    @Step("approval drug daily")
    public static void approvalDrugsDaily(int numberOfTime , boolean isFuture) throws InterruptedException {

        if (isFuture == false) {
            for (int i = 0; i < numberOfTime; i++) {
                approvalInstructionPage.btn_drug.get(i).click();
                approvalInstructionPage.ul_drug.get(i+4).click();
            }}
        approvalInstructionPage.btns_approveDrug.get(0).click();
        approvalNurseSign();
    }


    @Step("approval daily drugs , choosing hours : currentDay- close hours, routine - default hours ")
    public static void approvalDrugDailyList(int countDrug, int numberInDay)  {

        for (int i = 0 , r = 0; i < countDrug * numberInDay ; i+= numberInDay , r++) {
            // פירוק לשעות : לשעת מתן הקורבה ולאחריה השעות מתן הקורובת ברצף לדוגמא תרופה ל3 פעמים ביום שעת הפירוק 9 בבוקר יפורק ל-10 , 12 , 14
            for (int j = 0, k=4;  j< numberInDay ; j++, k+=1) {
                approvalInstructionPage.btn_drug.get(i+j).click();
                approvalInstructionPage.ul_drug.get(i+j).findElements(By.tagName("li")).get(k).click();
            }
            approvalInstructionPage.btns_approveDrug.get(r).click();
        }
        approvalInstructionPage.btn_approvalDrug.click();
        WebFlows.userSignConfirm();

    }

    @Step("approval one drug daily , choosing all timeId to non-giving")
    public static void approvalOneDrugDailyToNonGiving( int numberInDay)  {

        for (int i = 0 ; i <  numberInDay ; i++) {
                approvalInstructionPage.btn_drug.get(i).click();
                // פירוק ל'ללא מתן'
                approvalInstructionPage.ul_drug.get(i).findElements(By.tagName("li")).get(2).click();
        }
        approvalInstructionPage.btns_approveDrug.get(0).click();
        approvalInstructionPage.btn_approvalDrug.click();
        WebFlows.userSignConfirm();
    }

    @Step("approval OnceOnly Drugs")
    public static void approvalDrugOnceOnlyList(int count) {
        for (int i = 0; i < count; i++) {

            // פירוק כל התרופות לשעת מתן הקרובה
            approvalInstructionPage.btn_drug.get(i).click();
            approvalInstructionPage.ul_drug.get(i).findElements(By.tagName("li")).get(4).click();
            approvalInstructionPage.btns_approveDrug.get(i).click();
        }
        approvalInstructionPage.btn_approvalDrug.click();
        WebFlows.userSignConfirm();
    }

    @Step("approval SOS Drugs")
    public static void approvalSOSDrugList()  {
        for (int i = 0; i < approvalInstructionPage.btns_approveDrug.size() ; i++) {
            UIActions.click(approvalInstructionPage.btns_approveDrug.get(i));
        }
        approvalNurseSign();
    }

    @Step("approval Weekly Drugs , choosing days : today and the close next days")
    public static void approvalWeeklyDrugList(int today, int countDay)  {
        int arrDaysNew []= fillArrToDaysInWeek(today);
        for (int i = 0; i < approvalInstructionPage.btns_approveDrug.size() ; i++) {
            approvalInstructionPage.btn_drug.get(i).click();
            approvalInstructionPage.ul_drug.get(i).findElements(By.tagName("li")).get(4).click();


            // בחירת ימים לפי :  היום הנוכחי והימים הבאים אחריו
            for (int j = 0; j < countDay ; j++) {
                UIActions.click(approvalInstructionPage.days_checkBoxList.get(arrDaysNew[j]));}
            UIActions.click(approvalInstructionPage.btns_approveDrug.get(i));
        }
        approvalNurseSign();

    }

    public static int [] fillArrToDaysInWeek(int today){
        int arr[] = new int[10];
        int m , n ;
        // סידור המערך של שבעה ימים שיהיה היום הנוכחי במקום הראשון ולאחריו הימים הבאים ברצף כגון היום הנוכחי יום רביעי אז המערך שיתקבל הוא : 4,5,6,0,1,2,3
        for (m=0 ,n=7-today+1;n<7; m++ ,n++)
            arr[n] = m;
        if(m<7){
            for (n=0; m<7; m++ ,n++)
                arr[n] = m;
        }
        return  arr;
    }

    @Step("approval solution to current hour ")
    public static void approvalSolution(boolean isSolution ,boolean isContinues)  {
        for (int i = 0; i < approvalInstructionPage.btns_approveSolution.size() ; i++) {
           if(isSolution)
               UIActions.click(approvalInstructionPage.solution_scale_currentHourList.get(i));
           else
               UIActions.click(approvalInstructionPage.bloodP_scale_currentHour.get(i));
            if(!isContinues)  {
              //  Thread.sleep();
                UIActions.click(approvalInstructionPage.btn_V_liquid);}
            UIActions.click(approvalInstructionPage.btns_approveSolution.get(i));
        }
        approvalNurseSign();


    }

    @Step("approval general-ins daily")
    public static void approvalGeneralInsDaily(int numberOfTime , boolean isFuture)  {
        if(isFuture== false) {
            // int indexToStart = 0;
            for (int i = 0; i < numberOfTime; i++) {
                //    for (int j = indexToStart; j < numberOfTime + indexToStart; j++) {
                //   approvalInstructionPage.btn.get(j).click();
                //    approvalInstructionPage.ul.get(j).findElements(By.tagName("li")).get(4 + j).click();
                approvalInstructionPage.btn_gen.get(i).click();
                approvalInstructionPage.ul_gen.get(i).findElements(By.tagName("li")).get(4 + i).click();
            }
        }
        approvalInstructionPage.btns_approveGeneral.get(0).click();

        approvalNurseSign();
    }

    @Step("approval general-ins once only")
    public static void approvalGeneralInsOnceOnly()  {

            for (int i = 0; i < approvalInstructionPage.btn_gen.size(); i++) {
                approvalInstructionPage.btn_gen.get(i).click();
                approvalInstructionPage.ul_gen.get(i).findElements(By.tagName("li")).get(4).click();
                approvalInstructionPage.btns_approveGeneral.get(i).click();
            }
            approvalNurseSign();
    }

    @Step("cardex execute supervision to drug")
    public static void executeSupervisionToDrug() {
        for (int i = 0; i < cardexPage.checkBoxListDrug.size(); i++) {
            if (cardexPage.checkBoxListDrug.get(i).getCssValue("border-color").contains("194, 49, 52")) {
                UIActions.click(cardexPage.drug_popover_execArrowList.get(i));
                UIActions.click(cardexPage.inputSupervision);
                UIActions.click(cardexPage.btn_ok);
                executionNurseSign();
            }
        }
    }

    @Step("cardex execute supervision to solution")
    public static void executeSupervisionToSolution() throws InterruptedException {
        for (int i = 0; i < cardexPage.checkBoxListSol.size(); i++) {
            System.out.println(cardexPage.checkBoxListSol.get(i).getCssValue("border-color"));
            if (cardexPage.checkBoxListSol.get(i).getCssValue("border-color").contains("194, 49, 52") ) {
                UIActions.click(cardexPage.sol_popover_execArrowList.get(i));
                UIActions.click(cardexPage.inputSupervision);
                UIActions.click(cardexPage.btn_ok);
                Thread.sleep(500);
                executionNurseSign();
            }
        }
    }

    @Step("execute all daily_onceOnly_sos_weekly_byHour ins")
    public static void executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse() throws InterruptedException {
                for (int i = 0; i < cardexPage.checkBoxListDrug.size(); i++)
                    UIActions.click(cardexPage.checkBoxListDrug.get(i));
                Thread.sleep(100);
    }

    @Step("execute liquid drugs and blood-products")
    public static void executeAllLiquidAfterApprovalNurse() throws InterruptedException {
                 //continues and timeLimit
        if( cardexPage.checkBoxListSol.size()>0) {
            for (int i = 0; i < cardexPage.checkBoxListSol.size(); i++) {
                UIActions.click(cardexPage.checkBoxListSol.get(i)); }
            Thread.sleep(3000);

        }
                //continues and timeLimit - blood
        if(cardexPage.checkBoxListBlood.size()>0)
            for (int i = 0; i < cardexPage.checkBoxListBlood.size(); i++) {
                UIActions.click(cardexPage.checkBoxListBlood.get(i)); }
        Thread.sleep(3000);

    }

    @Step("execute general-ins")
    public static void executeAllGeneralInsAfterApprovalNurse() throws InterruptedException {

        Thread.sleep(1000);
        for (int i = 0; i < cardexPage.checkBoxListGeneralIns.size(); i++) {
            UIActions.click(cardexPage.checkBoxListGeneralIns.get(i)); }
        Thread.sleep(3000);

    }

    @Step("execution nurse sign")
    public static void executionNurseSign(){
        if(!cardexPage.btn_approval.getText().contains("0")) {
            cardexPage.btn_approval.click();
            WebFlows.userSignConfirm();
        }
        wait.until(ExpectedConditions.elementToBeClickable(cardexPage.btn_approval));
        Verifications.textIsContains(cardexPage.btn_approval , "0");
    }

    @Step("approval nurse sign")
    public static void approvalNurseSign(){
        approvalInstructionPage.btn_approvalDrug.click();
        WebFlows.userSignConfirm();
    }



    @Step("execute and supervision all CardexIns")
    public static void executeAndSupervisionAllCardexIns() throws InterruptedException {
        Thread.sleep(500);
        executeSupervisionToDrug();
        executeSupervisionToSolution();
      //  Thread.sleep(500);
        executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
        executeAllLiquidAfterApprovalNurse();
        executionNurseSign();
    }

}
