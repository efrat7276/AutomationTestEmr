package regression;

import extensions.UIActions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;

@Listeners(utilities.Listeners.class)
public class mainMenuPagesRegression extends CommonOps {

    @Test(description = "goToAllMainMenuDoctor")
    public  void goToAllMainMenuDoctor() {

        WebFlows.login('d');
        softAssert.assertTrue(UIActions.isExist(patientsList.patients_body));
        UIActions.click(mainMenuPage.category_dischargedList);
        softAssert.assertTrue(UIActions.isExist(dischargedListPage.discharged_body));
        UIActions.click(mainMenuPage.category_ordersList);
        softAssert.assertTrue(UIActions.isExist(orderListPage.order_body));
        UIActions.click(mainMenuPage.category_reportsList);
        softAssert.assertTrue(UIActions.isExist(depReport.depReport_body));
        UIActions.click(mainMenuPage.category_protocolList);
        softAssert.assertTrue(UIActions.isExist(protocolListPage.protocolDep_body));
        UIActions.click(mainMenuPage.category_operations);
        softAssert.assertTrue(UIActions.isExist(orderListPage.order_body));
        softAssert.assertAll();
    }

}
