package RegressionTest;

import extensions.UIActions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;

@Listeners(utilities.Listeners.class)
public class mainMenuPagesRegression extends CommonOps {

    @Test(description = "goToAllMainMenuDoctor")
    public  void goToAllMainMenuDoctor() throws InterruptedException {

        WebFlows.login('d');
        softAssert.assertTrue(UIActions.isExist(patientsList.patients_body));
        UIActions.click(mainMenuPage.category_dischargedList);
        Thread.sleep(2000);
        softAssert.assertTrue(UIActions.isExist(dischargedListPage.discharged_body));
        UIActions.click(mainMenuPage.category_ordersList);
        Thread.sleep(2000);
        softAssert.assertTrue(UIActions.isExist(orderListPage.order_body));
        UIActions.click(mainMenuPage.category_reportsList);
        Thread.sleep(2000);
        softAssert.assertTrue(UIActions.isExist(depReport.depReport_body));
        UIActions.click(mainMenuPage.category_protocolList);
        Thread.sleep(2000);
        softAssert.assertTrue(UIActions.isExist(protocolListPage.protocolDep_body));
        UIActions.click(mainMenuPage.category_operations);
        Thread.sleep(2000);
        softAssert.assertTrue(UIActions.isExist(operationsPage.operations_body));
        softAssert.assertAll();
    }

}
