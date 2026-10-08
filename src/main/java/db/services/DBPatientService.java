package db.services;

import actionUtilies.DBExecuter;
import lombok.extern.slf4j.Slf4j;
import models.GynecologyPatient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class for Women Emergency Department patient database operations
 * Handles stored procedure calls and data transformation
 * 
 * Database Procedure: Returns list of patients with 60+ fields
 * Example fields from response:
 * - mispar_ishpuz: Patient number (primary ID)
 * - shem_prati: First name
 * - shem_mishp: Last name / Family name
 * - teudat_zeut: ID number
 * - gil: Age
 * - bed_shibuts: Bed/Room assignment
 * - tz_rofe_shem: Doctor name
 * - tz_achot_shem: Nurse name
 * - teluna_ikarit: Main reason for visit
 * - triage_num: Triage level
 * - sug_dam: Blood type
 */
@Slf4j
public class DBPatientService {

    /**
     * Stored procedure name - CONFIRMED from API response
     */
    private static final String PROCEDURE_NAME = "wbhEMR.s_miun_meushpazim_emr @k_yechida_shichrur = 55000";
    
    /**
     * Column mapping from stored procedure result to Patient object
     * These are the actual column names returned by the stored procedure
     */
    // Primary Identifiers
    private static final String COL_PATIENT_NUMBER = "mispar_ishpuz";      // Patient Number (Primary)
    private static final String COL_ID_NUMBER = "teudat_zeut";            // ID Number
    
    // Names
    private static final String COL_FIRST_NAME = "shem_prati";            // First Name
    private static final String COL_LAST_NAME = "shem_mishp";             // Last Name / Family Name
    
    // Physical Information
    private static final String COL_AGE = "gil";                          // Age
    private static final String COL_BLOOD_TYPE = "sug_dam";               // Blood Type (e.g., "O+")
    
    // Location/Bed
    private static final String COL_BED = "bed_shibuts";                  // Bed/Room Assignment
    private static final String COL_ROOM = "room";                        // Room Number
    
    // Clinical Information
    private static final String COL_REASON = "teluna_ikarit";             // Main Reason for Visit
    private static final String COL_TRIAGE = "triage_num";                // Triage Level (1-4)
    private static final String COL_STATUS = "matsav_siudi";              // Clinical Status
    
    // Staff Assignment
    private static final String COL_DOCTOR_NAME = "tz_rofe_shem";         // Doctor Name
    private static final String COL_DOCTOR_ID = "tz_rofe";                // Doctor ID
    private static final String COL_NURSE_NAME = "tz_achot_shem";         // Nurse Name
    private static final String COL_NURSE_ID = "tz_achot";                // Nurse ID
    
    // Admission Information
    private static final String COL_ADMISSION_DATE = "tarich_knisa";      // Admission Date
    private static final String COL_TIME_MINUTES = "zman_be_dakot";       // Time in minutes

    /**
     * Execute stored procedure to get Women Emergency Department patients
     * @return List of Patient objects from database
     */
    public static List<GynecologyPatient> getWomenEmergencyPatientsFromDB() {
        log.info("======== Fetching Women Emergency Gynecology Patients from DB ========");
        log.info("Executing procedure: {}", PROCEDURE_NAME);
        
        List<GynecologyPatient> patients = new ArrayList<>();
        
        try {
            // Build the procedure call
            String procedureCall = String.format("EXEC %s", PROCEDURE_NAME);
            // Note: If procedure requires parameters (e.g., department ID), add them:
            // String procedureCall = String.format("EXEC %s @departmentId='55000'", PROCEDURE_NAME);
            
            log.info("Procedure call: {}", procedureCall);
            
            // Execute the procedure
            Object result = DBExecuter.handleAnyQuery(procedureCall);
            
            // Parse the results
            patients = parsePatientResults(result);
            
            log.info("✓ Successfully retrieved {} patients from database", patients.size());
            return patients;
            
        } catch (Exception e) {
            log.error("✗ Error fetching patients from database: {}", e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Parse stored procedure results into Patient objects
     * @param result Raw result from DBExecuter
     * @return List of Patient objects
     */
    private static List<GynecologyPatient> parsePatientResults(Object result) {
        log.info("Parsing gynecology patient results from database...");
        List<GynecologyPatient> patients = new ArrayList<>();
        
        try {
            if (result instanceof List) {
                List<?> resultList = (List<?>) result;
                
                for (Object item : resultList) {
                    if (item instanceof Map) {
                        Map<String, Object> row = (Map<String, Object>) item;
                        GynecologyPatient patient = mapRowToPatient(row);
                        if (patient != null) {
                            patients.add(patient);
                            log.debug("Parsed patient: {} - {}", patient.getDisplayId(), patient.getFullName());
                        }
                    }
                }
            }
            
            log.info("✓ Parsed {} patient rows from database result", patients.size());
            logPatientsSummary(patients);
            return patients;
            
        } catch (Exception e) {
            log.error("✗ Error parsing patient results: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Map database row to Patient object
     * Maps all columns from stored procedure response to Patient fields
     * 
     * @param row Database row as Map<String, Object>
     * @return Patient object or null if mapping fails
     */
    private static GynecologyPatient mapRowToPatient(Map<String, Object> row) {
        try {
            // Core Identifiers
            String patientNumber = getMapValue(row, COL_PATIENT_NUMBER);
            String idNumber = getMapValue(row, COL_ID_NUMBER);
            
            // Names
            String firstName = getMapValue(row, COL_FIRST_NAME);
            String lastName = getMapValue(row, COL_LAST_NAME);
            
            // Physical Information
            String age = getMapValue(row, COL_AGE);
            String bloodType = getMapValue(row, COL_BLOOD_TYPE);
            
            // Location
            String bed = getMapValue(row, COL_BED);
            String room = getMapValue(row, COL_ROOM);
            
            // Clinical
            String reason = getMapValue(row, COL_REASON);
            String triage = getMapValue(row, COL_TRIAGE);
            String status = getMapValue(row, COL_STATUS);
            
            // Staff
            String doctorName = getMapValue(row, COL_DOCTOR_NAME);
            String doctorId = getMapValue(row, COL_DOCTOR_ID);
            String nurseName = getMapValue(row, COL_NURSE_NAME);
            String nurseId = getMapValue(row, COL_NURSE_ID);
            
            // Admission Info
            String admissionDate = getMapValue(row, COL_ADMISSION_DATE);
            String timeMinutes = getMapValue(row, COL_TIME_MINUTES);
            
            // Build GynecologyPatient object using builder pattern
            GynecologyPatient patient = GynecologyPatient.builder()
                    .mispar_ishpuz(patientNumber)
                    .teudat_zeut(idNumber)
                    .shem_prati(firstName)
                    .shem_mishp(lastName)
                    .gil(age)
                    .sug_dam(bloodType)
                    .bed_shibuts(bed)
                    .room(room)
                    .teluna_ikarit(reason)
                    .triage_num(triage)
                    .matsav_siudi(status)
                    .tz_rofe_shem(doctorName)
                    .tz_rofe(doctorId)
                    .tz_achot_shem(nurseName)
                    .tz_achot(nurseId)
                    .tarich_knisa(admissionDate)
                    .zman_be_dakot(timeMinutes)
                    .build();
            
            return patient;
            
        } catch (Exception e) {
            log.error("Error mapping row to patient: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Get value from Map with null safety
     */
    private static String getMapValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : "";
    }

    /**
     * Log summary of fetched gynecology patients
     */
    private static void logPatientsSummary(List<GynecologyPatient> patients) {
        log.info("========== Women Emergency Department Gynecology Patients Summary ==========");
        log.info("Total patients fetched: {}", patients.size());
        
        if (!patients.isEmpty()) {
            log.info("First patient: {}", patients.get(0));
            if (patients.size() > 1) {
                log.info("Last patient: {}", patients.get(patients.size() - 1));
            }
        }
        
        // Group by triage level
        java.util.Map<String, Long> triageCount = patients.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        p -> p.getTriage_num() != null ? p.getTriage_num() : "UNKNOWN",
                        java.util.stream.Collectors.counting()
                ));
        
        log.info("Patients by triage level:");
        triageCount.forEach((triage, count) -> log.info("  Level {} : {}", triage, count));
        
        log.info("=====================================================================");
    }

    /**
     * Get single gynecology patient by patient number
     * @param patientNumber Patient number to search for
     * @return GynecologyPatient object or null if not found
     */
    public static GynecologyPatient getPatientByNumberFromDB(String patientNumber) {
        log.info("Fetching patient {} from database", patientNumber);
        
        List<GynecologyPatient> allPatients = getWomenEmergencyPatientsFromDB();
        return allPatients.stream()
                .filter(p -> p.getMispar_ishpuz().equals(patientNumber))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get all gynecology patients with specific triage level
     * @param triageLevel Triage level to filter by (1-4)
     * @return List of GynecologyPatient objects with that triage level
     */
    public static List<GynecologyPatient> getPatientsByTriageLevelFromDB(String triageLevel) {
        log.info("Fetching patients with triage level '{}' from database", triageLevel);
        
        List<GynecologyPatient> allPatients = getWomenEmergencyPatientsFromDB();
        return allPatients.stream()
                .filter(p -> triageLevel.equalsIgnoreCase(p.getTriage_num()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Verify that patient exists in database
     * @param patientNumber Patient number to verify
     * @return true if patient exists, false otherwise
     */
    public static boolean patientExistsInDB(String patientNumber) {
        log.info("Checking if patient {} exists in database", patientNumber);
        GynecologyPatient patient = getPatientByNumberFromDB(patientNumber);
        boolean exists = patient != null;
        log.info("✓ Patient {} {} in database", patientNumber, exists ? "EXISTS" : "DOES NOT EXIST");
        return exists;
    }

    /**
     * Count total patients in Women Emergency Department
     * @return Total patient count
     */
    public static int getTotalPatientCountFromDB() {
        List<GynecologyPatient> patients = getWomenEmergencyPatientsFromDB();
        return patients.size();
    }
}
