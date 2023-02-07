package prepertion;

import utilities.CommonOps;

public class BeforeTest extends CommonOps {

    public String clearTables(String query){

        try {
            rs = stmt.executeQuery(query);
        }
        catch (Exception ex){

        }
        return "success";



    }

}
