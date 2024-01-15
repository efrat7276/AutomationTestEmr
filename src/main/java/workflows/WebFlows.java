package workflows;

import com.google.common.util.concurrent.Uninterruptibles;
import extensions.DBAction;
import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Step;
import utilities.CommonOps;
//import workflows.doctor.doctorInstructionFlows;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebFlows extends CommonOps {

    @Step("sign")
    public static void userSignConfirm(){

        userSignModalPage.input_userName.clear();
        userSignModalPage.input_userName.sendKeys(CommonOps.getData("userTest"));
        userSignModalPage.input_password.clear();
        userSignModalPage.input_password.sendKeys(CommonOps.getData("passwordTest"));
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        userSignModalPage.btn_confirm.click();
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);

    }
    @Step(" Login With Choose Role")
    public static void login(char r)  {

        UIActions.updateText(emrLogin.txt_username, CommonOps.getData("userTest"));
        UIActions.updateText( emrLogin.txt_password, CommonOps.getData("passwordTest"));
        UIActions.click(emrLogin.btn_submitBtn);
        switch (r){
            case 'd' : UIActions.selectFromList(chooseRole.list , "רופא"); break;
            case 'n' : UIActions.selectFromList(chooseRole.list , "אחות"); break;

        }
        try {
            Thread.sleep(4000);
        }
        catch (InterruptedException e){

        }

    }
    @Step(" Login")
    public static void login(String user , String pass){

        UIActions.updateText( emrLogin.txt_username , user);
        UIActions.updateText( emrLogin.txt_password, pass);
        UIActions.click(emrLogin.btn_submitBtn);

    }



    @Step(" ")
    public static void chooseRole(String roleDescription){

        UIActions.selectFromList(chooseRole.list , roleDescription);
    }

//    @Step(" patientBoxEntry")
//    public static void patientBoxEntry(String mispar_sherut){
//
//
//        String mispar_sherut_katzar = mispar_sherut.substring(2);
//        UIActions.searchInList(patientsList.list_patients, mispar_sherut_katzar);
//
//    }

    @Step(" patientBoxEntry")
    public static void patientBoxEntry(int index){

        UIActions.click(patientsList.list_patients.get(index));
        Verifications.textIsVisible(demogeDataBar.sherut_label , "מס' שרות:");
        try {
            Thread.sleep(4000);
        }
        catch (InterruptedException e){

        }
    }
}
