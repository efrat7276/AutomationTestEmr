package pages;

public enum InstructionType {
    MEDICINE("תרופה"),
    BLOOD("מוצר דם"),
    FLUID("תרופה"),
    GENERAL("כלליות"),
    NUTRITION("תזונה"),
    IMMEDIATE("מיידית"),
    TREATMENT_PROTOCOL("פרוטוקול טיפול"),
    MEDICINE_PROTOCOL("פרוטוקול תרופתי"),
    IMPORT_MEDICINE("ייבוא תרופה"),
    OTC("הוראת OTC");
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
