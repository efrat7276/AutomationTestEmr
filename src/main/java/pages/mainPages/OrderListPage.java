package pages.mainPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class OrderListPage {

    @FindBy(how = How.XPATH ,using="//order-department")
    public WebElement order_body;


}
