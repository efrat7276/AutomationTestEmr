package models;

public class DrugObj{

     public String drug_desc;
     public  int dosage;
     public  String routeAdmin;
     public  String comment;
     public  int possId;
     public  int numberOfTime;
     public  boolean isAntibiotic;
     public  boolean isFutureDate;

     public DrugObj(String drug_desc, int dosage, String routeAdmin , int  possId, int numberOfTime
             , boolean isAntibiotic, boolean isFutureDate){
          this.drug_desc = drug_desc;
          //super(desc);

          this.dosage = dosage;
          this.routeAdmin = routeAdmin;
          this.comment = "בדיקות אוטו'";
          this.possId = possId;
          this.numberOfTime = numberOfTime;
          this.isAntibiotic = isAntibiotic;
          this.isFutureDate = isFutureDate;
     }



}
