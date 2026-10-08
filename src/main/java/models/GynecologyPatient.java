package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a Gynecology Patient (מטופלת אגף נשים) in Women Emergency Department
 * Maps to stored procedure result set columns
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GynecologyPatient {
    
    // Core Patient Identifiers
    private String mispar_ishpuz;           // Patient Number (Primary Key)
    private String teudat_zeut;            // ID Number
    private String shem_prati;             // First Name
    private String shem_mishp;             // Last Name
    
    // Physical Information
    private String gil;                    // Age
    private String sug_dam;                // Blood Type
    
    // Location/Bed Information
    private String bed_shibuts;            // Bed/Room Assignment
    private String room;                   // Room Number (if different)
    
    // Clinical Information
    private String teluna_ikarit;          // Main Reason for Visit
    private String triage_num;             // Triage Level (1-4)
    private String matsav_siudi;           // Clinical Status
    
    // Staff Assignment
    private String tz_rofe_shem;           // Doctor Name
    private String tz_rofe;                // Doctor ID
    private String tz_achot_shem;          // Nurse Name
    private String tz_achot;               // Nurse ID
    
    // Admission/Time Information
    private String tarich_knisa;           // Admission Date
    private String zman_be_dakot;          // Time in minutes
    
    // Additional Fields
    private String connectMonitor;         // Monitor Connection Status
    private String haveInstructions;       // Has Instructions Flag
    private String remark;                 // Remarks
    
    /**
     * Get full patient name
     */
    public String getFullName() {
        String first = shem_prati != null ? shem_prati : "";
        String last = shem_mishp != null ? shem_mishp : "";
        return String.format("%s %s", first, last).trim();
    }
    
    /**
     * Get patient display ID (either patient number or ID number)
     */
    public String getDisplayId() {
        return mispar_ishpuz != null ? mispar_ishpuz : teudat_zeut;
    }
    
    /**
     * Override equals for comparison (useful for testing)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GynecologyPatient patient = (GynecologyPatient) o;
        return mispar_ishpuz != null && mispar_ishpuz.equals(patient.mispar_ishpuz);
    }
    
    @Override
    public int hashCode() {
        return mispar_ishpuz != null ? mispar_ishpuz.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return String.format(
            "GynecologyPatient{id='%s', name='%s %s', age='%s', bed='%s', doctor='%s', reason='%s', triage=%s}",
            getDisplayId(),
            shem_prati,
            shem_mishp,
            gil,
            bed_shibuts,
            tz_rofe_shem,
            teluna_ikarit,
            triage_num
        );
    }
}
