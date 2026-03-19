package enums; 
import java.util.HashMap;
import java.util.Map;

public enum HospitalDepartment {
    INTERNAL_A(10011, "פנימית א"),
    INTERNAL_B(10012, "פנימית ב"),
    INTERNAL_C(10013, "פנימית ג"),
    CARDIOLOGY(10040, "קרדיולוגיה"),
    HEMATO_ONCOLOGY(10090, "המטואונקול"),
    GERIATRIC_INTERNAL(10230, "פנימית גרי"),
    INTENSIVE_CARE_GENERAL(10700, "ט'נ כללי"),
    INTENSIVE_CARE_HEART(10800, "ט'נ לב"),
    CORONA(11301, "קורונה"),
    PEDIATRICS_A(12000, "ילדים"),
    INTENSIVE_CARE_PEDIATRICS(12300, "ט'נ ילדים"),
    PREMATURE_BABIES(12400, "פגים"),
    GENERAL_SURGERY_A(13000, "כיר' כללית"),
    GENERAL_SURGERY_B(13002, "כיר' כלל' ב"),
    ORTHOPEDICS(13100, "אורטופדיה"),
    UROLOGY(13200, "אורולוגיה"),
    GYNECOLOGY(15001, "גניקולוגיה"),
    MATERNITY_A(15100, "יולדות"),
    MATERNITY_B(15102, "יולדות ב"),
    RECOVERY(16000, "השהייה"),
    GERIATRIC_REHABILITATION(18200, "גרי' שיקום"),
    NURSING_CARE_A(18401, "גרי' סע' א"),
    NURSING_CARE_C(18403, "גרי' סע' ג"),
    PEDIATRICS_B(19000, "ילדים"),
    PEDIATRICS_C(19002, "ילדים ב"),
    DAY_HOSPITALIZATION(20000, "אשפוז יום"),
    HEMATOLOGY_DAY(20080, "המטולוגי י"),
    ONCOLOGY_DAY(20400, "אונקולוג י"),
    EMERGENCY_ROOM(50000, "חדר מיון"),
    PEDIATRIC_EMERGENCY(52000, "מ' ילדים"),
    WOMEN_EMERGENCY(55000, "מיון נשים"),
    DELIVERY_ROOM(59000, "חדר לידה");

    private final int code;
    private final String displayName;

    private static final Map<String, HospitalDepartment> BY_NAME = new HashMap<>();

    static {
        for (HospitalDepartment dept : values()) {
            BY_NAME.put(dept.displayName.trim(), dept);
        }
    }

    HospitalDepartment(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public int getCode() { return code; }
    public String getDisplayName() { return displayName; }

    public static HospitalDepartment getByHebrewName(String name) {
        if (name == null) return null;
        return BY_NAME.get(name.trim());
    }
}