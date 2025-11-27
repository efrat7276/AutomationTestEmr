package pages.mainPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ProtocolListPage {

    @FindBy(how = How.XPATH ,using="//protocol-list-table")
    public WebElement protocolDep_body;


}
