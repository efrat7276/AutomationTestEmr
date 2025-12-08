import org.testng.annotations.Test;

import actionUtilies.DBExecuter;
import pages.*;
import pages.addForms.DrugFormPage;
import pages.mainPages.PatientsListPage;

public class DoctorInstructionsSanitySuite extends BaseTest {
    LoginPage loginPage = new LoginPage();
    PatientsListPage patientsListPage = new PatientsListPage();
    PatientBoxPage patientBoxPage = new PatientBoxPage();
    DoctorInstructionPage doctorInstructionPage = new DoctorInstructionPage();
    DrugFormPage drugForm = new DrugFormPage();


    private final String doctorUsername = "test";
    private final String doctorPassword = "Te231121";
    
    public void openInstructionForm(InstructionType type) {
        switch(type) {
            case MEDICINE:
               doctorInstructionPage.clickButtonAddInstruction(type);
                break;
            case BLOOD:
                //     clickButtonAddBloodProduct();
                break;
            case FLUID:
              //       clickButtonAddFluid();
                break;
            case GENERAL:
              //  clickButtonAddGeneralInstruction();
                break;
            case NUTRITION:
              //  clickButtonAddNutrition();
                break;
            case IMMEDIATE:
               // clickButtonAddImmediateInstruction();
                break;
            case TREATMENT_PROTOCOL:
               /////// clickButtonAddTreatmentProtocol();
                break;
            case MEDICINE_PROTOCOL:
            //////    clickButtonAddMedicineProtocol();
                break;
            case IMPORT_MEDICINE:
            //    clickButtonImportMedicine();
                break;
        }
    }
    private void loginAsDoctor() {
        loginPage.login(doctorUsername, doctorPassword, "רופא");
    }

    private void selectFirstPatient() {
        patientsListPage.choosePatient(1);
        patientBoxPage.verifyPatientDetailsExisting();
    }

    @Test(description = "Sanity: Adding a medicine")
    public void testAddMedicine() {
      String result = DBExecuter.executeQuery("SELECT * FROM cpoe.cpoeIinstructionTypeCode AS citc").toString();
       System.out.println(result);
      loginAsDoctor();
        selectFirstPatient();
        System.out.println("Starting test: Adding a medicine");
        doctorInstructionPage.addMedicineFull("dep", "daily", "20", "1", doctorUsername, doctorPassword);
        System.out.println("Finished test: Adding a medicine");
    }

    @Test(description = "Sanity: Adding a fluid")
    public void testAddFluid() {
        loginAsDoctor();
        selectFirstPatient();
        System.out.println("Starting test: Adding a fluid");
        doctorInstructionPage.addFluidFull("INF", "continuous", "50", "1L", doctorUsername, doctorPassword);
        System.out.println("Finished test: Adding a fluid");
    }

    @Test(description = "Sanity: Adding a blood product")
    public void testAddBloodProduct() {
        loginAsDoctor();
        selectFirstPatient();
        System.out.println("Starting test: Adding a blood product");
        doctorInstructionPage.addBloodProductFull("דם דחוס", "1", doctorUsername, doctorPassword);
        System.out.println("Finished test: Adding a blood product");
    }

    // @Test(description = "Sanity: Adding a general instruction")
    // public void testAddGeneralInstruction() {
    //     loginAsDoctor();
    //     selectFirstPatient();
    //     System.out.println("Starting test: Adding a general instruction");
    //     doctorInstructionPage.addGeneralInstructionFull("Patient to rest for 30 minutes", doctorUsername, doctorPassword);
    //     System.out.println("Finished test: Adding a general instruction");
    // }

    // @Test(description = "Sanity: Adding OTC")
    // public void testAddOTC() {
    //     loginAsDoctor();
    //     selectFirstPatient();
    //     System.out.println("Starting test: Adding OTC");
    //  //   doctorInstructionPage.addOTCFull("Vitamin C", "1 tablet", doctorUsername, doctorPassword);
    //     System.out.println("Finished test: Adding OTC");
    // }


    @Test(description = "Adding continuous fluid")
    public void testAddContinuousFluid() {
        loginAsDoctor();
        selectFirstPatient();
        doctorInstructionPage.addFluidFull("INF", "Continuous", "500", "1000", doctorUsername, doctorPassword);
        doctorInstructionPage.approveAndVerifyInstructions(doctorUsername, doctorPassword);
    }

    // @Test(description = "Adding nutrition")
    // public void testAddNutrition() {
    //     loginAsDoctor();
    //     selectFirstPatient();
    //  //   doctorInstructionPage.clickButtonAddNutrition();
    //  //   doctorInstructionPage.addNutrition("Enteral", "1500kcal");
    //     doctorInstructionPage.approveAndVerifyInstructions(doctorUsername, doctorPassword);
    // }


    // @Test(description = "Adding immediate instruction in ER")
    // public void testAddImmediateInstructionER() {
    //     loginAsDoctor();
    //     selectFirstPatient();
    //  //   doctorInstructionPage.clickButtonAddImmediateInstruction();
    //  //   doctorInstructionPage.addImmediateInstruction("Immediate Example");
    //     doctorInstructionPage.approveAndVerifyInstructions(doctorUsername, doctorPassword);
    // }


    // @Test(description = "Adding treatment protocol")
    // public void testAddTreatmentProtocol() {
    //     loginAsDoctor();
    // }

}
