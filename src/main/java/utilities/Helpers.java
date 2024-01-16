package utilities;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Helpers {

    public static String getFileName(String desc){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        return desc+"_"+dtf.format(now);
    }

    public static String getData(String nodeName) {

        DocumentBuilder dBuilder;
        Document doc = null;
        File fxmlFile = new File("./Configuration/DataConfig.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fxmlFile);
        }
        catch (Exception e) {
            System.out.println("Exception in reading XMLfile: "+ e);
        }
        doc.getDocumentElement().normalize();
        return  doc.getElementsByTagName(nodeName).item(0).getTextContent();
    }




//    public static String getDataFromData(String nodeName1, String nodeName2) {
//
//        DocumentBuilder dBuilder;
//        Document doc = null;
//        File fxmlFile = new File("./Configuration/DataConfig.xml");
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        try {
//            dBuilder = dbFactory.newDocumentBuilder();
//            doc = dBuilder.parse(fxmlFile);
//        }
//        catch (Exception e) {
//            System.out.println("Exception in reading XMLfile: "+ e);
//        }
//        doc.getDocumentElement().normalize();
//        return  ((Element)doc.getElementsByTagName(nodeName1).item(1)).getTextContent();
//    }
}
