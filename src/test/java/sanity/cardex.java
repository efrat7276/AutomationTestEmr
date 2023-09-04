package sanity;

import extensions.DBAction;
import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Description;
import models.DrugObj;
import org.checkerframework.checker.guieffect.qual.UI;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.emr.nurse.Execute.CardexPage;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.bloodProductInstructionFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;
import workflows.doctor.nutritionFlows;
import workflows.general.general;
import workflows.nurse.nurseFlows;

import java.sql.SQLException;
import java.util.List;

import static org.testng.Assert.assertTrue;

@Listeners(utilities.Listeners.class)
public class cardex extends CommonOps {

    @Test(description = "verify patient cardex")
    @Description("verify patient cardex")
    public void checkDrugListPatient() throws InterruptedException {

        //עובר על כל המטופלים ממחלקה מסוימת ובודק על כל מטופל (במידה ויש תרופות קרדקס) האם התרופה הראשונה שמופיע היא אכן שייכת למטופל
        WebFlows.login('n');
        departmentFlows.chooseDepartment("ילדים");
        String currentMisparIshpuz, ishpuzToCpoe;
        String query = "SELECT mispar_ishpuz FROM cpoe.cpoeInstructions  WHERE cpoeInstructionID = ";
        for (int i = 1; i < patientsList.list_patients.size(); i++) {
            WebFlows.patientBoxEntry(i);
            System.out.println("patient " + i);

            // בדיקה באיזה מסך נמצא
            int urlDefault_countChars = 0;
            switch (CommonOps.getEnv()) {
                case "dev":
                    urlDefault_countChars = "https://landev16.laniado.org.il/emr2/#/main/patients/patientDetail/".length();
                    break;
                case "qa":
                    urlDefault_countChars = "https://lanwebapptest.laniado.org.il/emr2/#/main/patients/patientDetail/".length();
                    break;
                case "prod":
                    urlDefault_countChars = "https://lanwebapp.laniado.org.il/emr2/#/main/patients/patientDetail/".length();
                    break;
                case "production":
                    urlDefault_countChars = "https://emr.laniado.org.il/emr2/#/main/patients/patientDetail/".length();
                    break;
            }

            // ניווט למסך קרקס במקרה שלא היה במסך
            String subUrl = driver.getCurrentUrl().substring(urlDefault_countChars);
            if (!subUrl.equals("cardex")) {
                NavigateFlows.goToCategory("cardex");
            }

            // חילוץ מספר ההוראה הראשונה (במקרה שישי הוראות)
            if (UIActions.isExist(cardexPage.topCpoeInCardex)) {

                currentMisparIshpuz = demogeDataBar.sherut_number.getText();
                String topCpoeDisplay = cardexPage.topCpoeInCardex.get(0).getText();

                // שליפה מהDB את המספר אשפוז להוראה הראשונה שמופיעה בקרדקס
                ishpuzToCpoe = DBAction.getResultForQuery(query + topCpoeDisplay);
                //קיצוץ קידומת 20 ממספר אשפוז
                ishpuzToCpoe = ishpuzToCpoe.substring(2);

                System.out.println("current mispar ishpuz " + currentMisparIshpuz);
                System.out.println("topCpoeDisplay " + topCpoeDisplay);
                System.out.println("ishpuzToCpoe " + ishpuzToCpoe);

                // בדיקה האם המספר אשפוז הנוכחי שווה למספר אשפוז של ההוראה בקרדקס
                if (!currentMisparIshpuz.equals(ishpuzToCpoe)) {
                    Assert.fail();
                    System.out.println("cpoe: " + topCpoeDisplay + "not belongs to this mispar_ishpuz: " + currentMisparIshpuz);
                } else {

                    // חזרה לרשימת מטופלים והמשך הלולאה
                    UIActions.click(cardexPage.i_arrow);
                    UIActions.click(mainMenuPage.category_patientList);
                }
            }
            // הודעה מתאימה כאשר אין למטופל הוראות בקרדקס
            // חזרה לרשימת מטופלים והמשך הלולאה
            else {
                currentMisparIshpuz = demogeDataBar.sherut_number.getText();
                System.out.println("current mispar ishpuz " + currentMisparIshpuz);
                System.out.println("no drugs to patient topCpoeDisplay");

                UIActions.click(cardexPage.i_arrow);
                UIActions.click(mainMenuPage.category_patientList);
            }
        }


    }
}

