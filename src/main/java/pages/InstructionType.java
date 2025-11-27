package pages;

public enum InstructionType {
    MEDICINE("תרופה"),
    BLOOD("מוצר דם"),
    FLUID("תרופה"),
    GENERAL("General instruction"),
    NUTRITION("Nutrition instruction"),
    IMMEDIATE("Immediate instruction"),
    TREATMENT_PROTOCOL("Treatment protocol"),
    MEDICINE_PROTOCOL("Medicine protocol"),
    IMPORT_MEDICINE("Import medicine"),
    OTC("OTC instruction");
    private final String description;

    InstructionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // פונקציה לבדיקה אם ההוראה היא תרופתית
    public boolean isMedication() {
        return this == MEDICINE || this == MEDICINE_PROTOCOL || this == IMPORT_MEDICINE || this == OTC;
    }

    // פונקציה לבדיקה אם ההוראה היא טיפולית
    public boolean isTreatment() {
        return this == TREATMENT_PROTOCOL || this == FLUID || this == BLOOD || this == NUTRITION || this == IMMEDIATE;
    }
}
