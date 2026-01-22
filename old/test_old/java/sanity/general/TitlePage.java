package sanity.general;

import actionUtilies.Verifications;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;

@Listeners(utilities.Listeners.class)
public class TitlePage extends CommonOps {


    private static final String doctor_role = "רופא";
    private static final String nurse_role = "אחות מוסמכת";

    private static final String user = "test";
    private static final String password = "Te081219";


    @Test(description = "Verify Patients List")
    @Description("verify page patientList by title 'רשימת מטופלים'")
    public void test02_verifyPatientsList(){

        WebFlows.login('d');

        Verifications.textIsVisible(patientsList.menu_patientList , "רשימת מטופלים");

    }

}
