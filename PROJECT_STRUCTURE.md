# 📱 AutomationProject_EMR - מבנה הקוד והמחלקות

## 🎯 תיאור הפרויקט
פרויקט automation לבדיקת אפליקציית EMR (Electronic Medical Record) של **בית חולים לניאדו**.
הפרויקט משתמש ב-Selenium + TestNG + Java עם Page Object Model.

### 🌍 סביבות בדיקה
- **QA** - סביבת בדיקה ראשונה (בשימוש עיקרי)
- **Dev** - סביבת פיתוח
- **Prod** - סביבת ייצור
- **Automation** - סביבת automation ייעודית

כל סביבה בעלת URL שונה המוגדרת ב-`DataConfig.xml`

---

## 📁 מבנה הפרויקט

```
src/
├── main/java/
│   ├── pages/
│   │   ├── LoginPage.java                    # עמוד התחברות
│   │   ├── BasePage.java                     # מחלקת בסיס לכל עמוד
│   │   ├── ChooseRolePage.java              # בחירת תפקיד (רופא/אחות/תזונאית)
│   │   ├── UserSignModalPage.java           # חלון חתימה דיגיטלית
│   │   ├── PatientBoxPage.java              # קופסת פרטי המטופל
│   │   ├── MedicinePrep.java                # הכנת תרופות
│   │   ├── menu/
│   │   │   ├── MainMenuPage.java            # התפריט הראשי
│   │   │   └── InnerMenuPage.java           # תפריט פנימי (בעמוד מטופל)
│   │   ├── mainPages/
│   │   │   ├── PatientsListPage.java        # רשימת מטופלים
│   │   │   ├── DischargedPatientListPage.java # משוחררים
│   │   │   ├── BloodOrders.java             # הזמנות דם
│   │   │   ├── OrderListPage.java           # הזמנות
│   │   │   ├── ProtocolListPage.java        # פרוטוקולים
│   │   │   ├── OperationsPage.java          # חדר ניתוח
│   │   │   └── DepReport.java               # דוחות
│   │   ├── nurse/
│   │   │   ├── Execute/
│   │   │   │   ├── CardexPage.java          # קרדקס (ביצוע הנחיות) - ישן
│   │   │   │   └── CardexPageNew.java       # קרדקס (ביצוע הנחיות) - חדש ✅
│   │   │   ├── approval/
│   │   │   │   └── ApprovalInstructionPage1.java # אישור הנחיות
│   │   │   ├── wound/
│   │   │   │   ├── WoundPage.java           # רשימת פצעים
│   │   │   │   ├── WondFormPage.java        # טופס הוספת פצע
│   │   │   │   └── ...
│   │   │   └── prepMedicine/
│   │   │       └── prepMedcinePage.java     # הכנת תרופות
│   │   ├── doctor/
│   │   │   └── FollowupPage.java            # עקביות (FollowUp)
│   │   ├── addForms/
│   │   │   └── DrugFormPage.java            # טופס הוספת תרופות/הוראות
│   │   └── popUp/
│   │       ├── ConfirmationAlert.java       # חלון אישור
│   │       └── CardexDailyReport.java       # דוח יומי קרדקס
│   ├── base/
│   │   └── BaseSuit.java                    # מחלקת בסיס לטסטים
│   ├── drivers/
│   │   └── DriverManager.java               # ניהול WebDriver
│   ├── helpers/
│   │   ├── Constants.java                   # קבועים (usernames, passwords)
│   │   ├── FilesHelper.java                 # קריאת DataConfig.xml
│   │   ├── QueriesUtils.java                # שאילתות מסד נתונים
│   │   ├── Listeners.java                   # TestNG Listeners (screenshots)
│   │   └── ...
│   ├── actionUtilies/
│   │   └── UIActions.java                   # פעולות UI (click, type, wait)
│   ├── enums/
│   │   └── HospitalDepartment.java          # enum של המחלקות בבית החולים
│   └── pages/
│       └── DoctorInstructionPage.java       # 🔴 פעולות הרופא
├── test/java/sanity/
│   └── SanitySuite1.java                    # טסטים ראשיים
└── resources/
    └── Configuration/
        └── DataConfig.xml                   # הגדרות סביבה
```

---

## 🏥 זרימת העבודה בבית החולים

### 1️⃣ **רופא (Doctor)**
```
Login as Doctor 
  ↓
Choose Department 
  ↓
Choose Patient 
  ↓
Add Instructions:
   - Medicine (תרופות)
   - Fluid (נוזלים)
   - General (הוראות כלליות)
   - Blood Product (מוצרי דם)
   - Nutrition (תזונה)
   ↓
Approve Instructions (חתימה דיגיטלית)
```

**מחלקה:** `DoctorInstructionPage.java`

**פונקציות עיקריות:**
- `addMedicineFullAndVerify(name, frequency, amount, units, user, pass)` - הוספת תרופה
- `addFluidAndClose(name, frequency, amount, total)` - הוספת נוזל
- `addGeneralInstructionAndClose()` - הוראה כללית
- `addBloodProductAndClose(type, amount)` - מוצר דם
- `addNutritionFull(name, frequency, amount, units, user, pass)` - תזונה
- `renewAllInstructions()` - חידוש הוראות
- `approveAndVerifyInstructions(user, pass)` - אישור וחתימה

---

### 2️⃣ **אחות (Nurse) - שלב 1: אישור**
```
Login as Nurse 
  ↓
Choose Department 
  ↓
Choose Patient 
  ↓
ApprovalInstructionPage1:
   - View new instructions from doctor
   - Approve each instruction
   - Set times for each instruction
   - Define shift hours
   - Confirm
```

**מחלקה:** `ApprovalInstructionPage1.java`

**פונקציות:**
- `approveAllInstructions(user, pass)` - אישור כל ההוראות

---

### 3️⃣ **אחות (Nurse) - שלב 2: ביצוע**
```
CardexPageNew (קרדקס):
   - View all instructions in table format
   - Each row = instruction
   - Each column = hour in shift
   - Mark as executed for specific hours
   - Execute and approve
```

**מחלקה:** `CardexPageNew.java`

**פונקציות:**
- `clickArrowForwardToInnerMenu()` - כניסה לתפריט פנימי
- `executeAndApproveAllToThisShiftAndApproval(user, pass)` - ביצוע אישור
- `printIVLabelForFirstFluidInCardex()` - הדפסת תווית IV
- `verifyDrugExecuted(name)` - אימות ביצוע תרופה

---

### 4️⃣ **תזונאית (Nutritionist)**
```
Login as Nutritionist 
  ↓
Choose Department 
  ↓
Choose Patient 
  ↓
Add Nutrition Instructions (דומה לרופא)
```

---

## 📋 דפי עיקריים

### **LoginPage.java** - התחברות
```java
// Locators
By txt_username = By.id("user_name");                          // שדה username
By txt_password = By.id("password");                           // שדה password
By btn_submitBtn = By.id("submitBtn");                         // כפתור "התחברות"

// Functions
navigateToEMR(String env)                    // ניווט ל-URL לפי סביבה (QA/Dev/Prod/Automation)
login(String user, String pass, String role) // התחברות מלאה + בחירת תפקיד
insertUserAndPass(String user, String pass)  // הזנת שם ומטופל בלי לחיצה
```

**📍 עמוד UI:** עמוד התחברות (Login Page)

**🔄 זרימה:**
1. ניווט ל-URL מ-DataConfig.xml לפי סביבה
2. הזנת שם משתמש (username)
3. הזנת סיסמה (password)
4. לחיצה על "התחברות"
5. אם התחברות הצליחה → ChooseRolePage מופיע (בחירת תפקיד)

**🎯 שימוש בטסטים:**
- `loginAsDoctor()` ב-BaseSuit → `login(DOCTOR_USERNAME, DOCTOR_PASSWORD, "רופא")`
- `loginAsNurse()` ב-BaseSuit → `login(NURSE_USERNAME, NURSE_PASSWORD, "אחות")`
- `loginAsNutritionist()` ב-BaseSuit

**💾 Credentials (Constants.java):**
- test / Te231121 (משתמש בדיקה עם הרשאות רבות)
- או משתמשים ספציפיים לכל תפקיד

---

### **ChooseRolePage.java** - בחירת תפקיד
```java
// Locators
By listRole = By.xpath("//ul[@class='list-group']/li");

// Functions
chooseRole(String roleDescription)          // בחירת תפקיד מרשימה ("רופא", "אחות", "תזונאית")
```

**📍 עמוד UI:** עמוד בחירת תפקיד (Role Selection)

**🔄 זרימה:**
1. אחרי התחברות מוצלחת
2. עמוד מופיע עם רשימת תפקידים (אם המשתמש בעל הרשאות מרובות)
3. בחירה על תפקיד (רופא / אחות / תזונאית)
4. כניסה לתפריט הראשי (MainMenuPage)

**🎯 שימוש בטסטים:**
- `login(user, pass, role)` ב-LoginPage → שומר את התפקיד
- `loginAsDoctor()` ב-BaseSuit → בוחר "רופא"

---

### **MainMenuPage.java** - התפריט הראשי
```java
// Locators
private By menu_patients = By.xpath("//...patients menu item");
private By menu_discharged = By.xpath("//...discharged menu item");
private By menu_orders = By.xpath("//...orders menu item");
private By btn_logout = By.xpath("//...logout button");

// Functions
verifyRoleIsDisplayed(String role)                 // בדיקה שהתפקיד מוצג
verifyPatientTableIsDisplayed()                    // בדיקה שטבלת מטופלים קיימת
verificationNumVersionExisting()                   // בדיקה מספר גרסה
logout()                                           // התנתקות מהמערכת
```

**📍 עמוד UI:** התפריט הראשי (Main Menu)

**🔄 זרימה:**
1. אחרי בחירת תפקיד בהצלחה
2. התפריט הראשי מופיע עם אפשרויות:
   - **רופא:** הוראות רופא, מטופלים, דוחות
   - **אחות:** אישור הוראות, קרדקס, פצעים
   - **תזונאית:** תזונה, דוחות
3. בחירה על פריט מתפריט
4. מציג את העמוד המתאים

**🎯 שימוש בטסטים:**
- בדיקה ש-menu מופיע לפי תפקיד
- בדיקה ש-version number נכון
- `logout()` לסיום בדיקה

---

### **ChooseDepartmentListPage.java** - בחירת מחלקה
```java
// Locators
By departmentList = By.xpath("//ngb-typeahead-window[contains(@id,'ngb-typeahead')]/button");
By btn_li_department = By.id("dropdownBasic1");

// Functions
selectDepartment(String departmentName)              // פתיחת dropdown ובחירת מחלקה
```

**📍 עמוד UI:** תיבה דיאלוג / popup לבחירת מחלקה (הופיעה אחרי ה-login כשיש הרשאות למחלקות מרובות)

**🔄 זרימה:**
1. משתמש מתחבר
2. אם יש הרשאה למחלקות מרובות → popup בחירת מחלקה
3. בחירת מחלקה מ-dropdown (מחלקות זמינות)
4. המטופלים בטבלה מסתננים/עדכנים לפי מחלקה

**🎯 שימוש בטסטים:**
- `chooseDepartmentVerifyListPatients(dept)` ב-BaseSuit
- כמעט בכל טסט הראשי: `chooseDepartmentListPage.selectDepartment(ICU_DEPARTMENT_STRING)`

---

### **PatientsListPage.java** - רשימת מטופלים
```java
// Locators
By list_patients = By.xpath("//p-table[contains(@class,'depMeushpazim-table patients-table')]//tr//td[1]");

// Functions
getPatientRows()                 // קבלת כל שורות המטופלים
choosePatient(int index)         // בחירת מטופל לפי אינדקס (1-based)
verifyPatientsListVisible()      // בדיקה שרשימה נראית
```

**📍 עמוד UI:** טבלת מטופלים בתפריט הראשי (אחרי התחברות ובחירת מחלקה)

**🔄 זרימה:**
1. אחרי בחירת מחלקה → טבלת מטופלים מופיעה
2. טבלה מכילה עמודות: מטופל, מהדורה, פרטים נוספים
3. בחירה על שורה = כניסה לעמוד פרטי מטופל

**🎯 שימוש בטסטים:**
- `choosePatient(PATIENT_1)` ב-BaseSuit
- כל טסט בוחר מטופל מהרשימה

---

### **PatientBoxPage.java** - קופסת פרטי המטופל
```java
// Locators
By bar_deatails_patient = By.xpath("//app-patient-detail/div");

// Functions
verifyPatientDetailsExisting()   // בדיקה שפרטי מטופל מוצגים
```

**📍 עמוד UI:** פס מידע בחלק העליון של עמוד פרטי מטופל (header) המציג:
- שם המטופל
- מספר זהות (ID)
- גיל
- מיקום בבית החולים

**🔄 זרימה:**
1. בחירת מטופל מטבלה
2. כניסה לעמוד פרטי מטופל
3. Header מופיע עם פרטי המטופל

**🎯 שימוש בטסטים:**
- בדיקה שהטסט בחר במטופל הנכון
- `test_06_enteringPatientBox()` - בדיקה שהפס קיים

---

### **UserSignModalPage.java** - חלון חתימה דיגיטלית
```java
// Locators
By inputUserName = By.id("user");                              // שדה שם משתמש
By inputPassword = By.id("password");                          // שדה סיסמה
By btnConfirm = By.xpath("//app-user-sign-modal//button[contains(@class,'btn-ok')]");
By btnCancel = By.xpath("//app-user-sign-modal//button[contains(@class,'btn-cancel')]");

// Functions
signModal(String username, String password)          // הזנת נתונים ואישור
```

**📍 עמוד UI:** חלון modal שקיבול שם משתמש וסיסמה (חתימה דיגיטלית)

**🔄 זרימה:**
1. בעת אישור הוראות/ביצועים
2. Popup חתימה דיגיטלית מופיעה
3. הזנת username/password של המשתמש
4. לחיצה על "אישור" / "Cancel"
5. בדיקה על השרת (או בפרונטאנד) של הנתונים

**⚠️ חשוב:** שימוש כללי לכל אישור בחוק - רופא מאשר הוראות, אחות מאשרת ביצוע, תזונאית מאשרת תזונה

**🎯 שימוש בטסטים:**
- `approveAndVerifyInstructions(user, pass)` - אישור הוראות רופא
- `approveAllInstructions(user, pass)` - אישור הוראות ע"י אחות
- `executeAndApproveAllToThisShiftAndApproval(user, pass)` - ביצוע הוראות אחות

---

### **ApprovalInstructionPage1.java** - אישור הוראות (אחות)
```java
// ⭐ עמוד אישור הוראות חדשות מהרופא
// הנחיות: בחזרה מהרופא → אחות מאשרת ודוקדוקת זמנים

// Functions
approveAllInstructions(String user, String pass)
  └─ אישור כל ההוראות החדשות + חתימה דיגיטלית (UserSignModalPage)
```

**📍 עמוד UI:** עמוד אישור הוראות (זעזוע של רופא → אחות)

**🔄 זרימה:**
1. רופא הוסיף הוראות ואישר (חתימה דיגיטלית)
2. אחות נכנסת לעמוד הראשי (MainMenuPage)
3. בוחרת מחלקה (ChooseDepartmentListPage)
4. בוחרת מטופל (PatientsListPage)
5. 🟢 **כל הוראה חדשה מופיעה עם "בדיקה" - אחות מאשרת**
6. אחות מגדירה זמנים לביצוע
7. אחות לוחצת על "אישור הוראות" (הופיע Modal חתימה דיגיטלית)

**🎯 שימוש בטסטים:**
- `test_10_approvalAllInstructionByNurse()` - אישור כל ההוראות

---

### **CardexPageNew.java** - קרדקס (ביצוע הוראות - אחות)
```java
// Locators
private By chk_executemedication = By.xpath("//...checkbox specific");
private By lblAmountExecuted = By.xpath("//...amount label");

// Functions
clickArrowForwardToInnerMenu()                                    // כניסה לתפריט פנימי
executeAndApproveAllToThisShiftAndApproval(user, pass)          // ביצוע אישור
printIVLabelForFirstFluidInCardex()                              // הדפסת תווית IV
verifyDrugExecuted(String drugName)                              // בדיקה ש-drug בוצע
```

**📍 עמוד UI:** טבלה שקיבול הוראות (Cardex)

**📊 מבנה הטבלה:**
```
┌─────────────────────────────────────────────────────────┐
│  הוראה          │  06:00  │  09:00  │  12:00  │  18:00 │
├─────────────────────────────────────────────────────────┤
│ תרופה A (3 פעמים)│  ☑    │  ☐    │  ☑    │  ☐    │
│ נוזל B         │  ☑    │  ☑    │  ☑    │  ☑    │
│ הוראה כללית  │  ☑    │  -    │  ☑    │  -    │
└─────────────────────────────────────────────────────────┘
```

**🔄 זרימה:**
1. אחות מתחברת, בוחרת מחלקה ומטופל
2. בוחרת "קרדקס" מהתפריט (ביצוע הוראות)
3. טבלה מופיעה עם:
   - שורות = הוראות (תרופות, נוזלים, הוראות כלליות)
   - עמודות = שעות בשיפור (06:00, 09:00, 12:00, 18:00 וכו')
   - תאים = checkboxes לביצוע בשעה מסוימת
4. אחות מסמנת ביצוע (check) לשעות המתאימות
5. לחיצה על "בצע ואישור"
6. Modal חתימה דיגיטלית (אחות מאשרת)

**🎯 שימוש בטסטים:**
- `test_13_executeAllInstructionByNurse()` - ביצוע כל ההוראות
- `test_14_printIVLabel()` - הדפסת תווית IV (fluid)

---

### **DoctorInstructionPage.java** - 🔴 פעולות הרופא
```java
// Locators
By btnAddMedicine = By.id("btnAddMedicine");                    // כפתור תרופה
By btnAddGeneralInstruction = By.xpath("//section[@id='generalIns']//button");
By btnAddNutrition = By.xpath("//section[@id='nutrition']//button[@id='btnAddMedicine']");
By btnAddBloodProduct = By.xpath("//section[@id='blood-product']//button");
By btnAddFluid = By.id("btnAddMedicine");                       // כפתור נוזל
By btn_approvalDrug = By.id("approvalDrug");                    // כפתור אישור

// ⭐ דינמי: כותרת משתנה בהתאם לסוג ההוראה המנוהל
By doctorInstructionsTitle = By.xpath("//span[contains(@class,'lan-title-ng-view-Hierarchy-a') and text()='הוראות רופא']");

// Main Functions - Add instructions (multiple variants per type)
clickButtonAddInstruction(InstructionType type)               // לחיצה על כפתור הוספה
addMedicineFullAndVerify(name, frequency, amount, units, user, pass)
addMedicineToList(name, frequency, amount, units)            // בלי אישור
addMedicineAndClose(name, frequency, amount, units)
addFluidFull(name, frequency, amount, units, user, pass)
addFluidToList(name, frequency, amount, units)
addFluidAndClose(name, frequency, amount, units)
addBloodProductFull(name, amount, user, pass)
addBloodProductToList(name, amount)
addBloodProductAndClose(name, amount)
addNutritionFull(name, frequency, amount, units, user, pass)
addGeneralInstructionFull(text, user, pass)
addGeneralInstructionAndClose()

// Approval and Verification
clickButtonSign()                                              // לחיצה על כפתור אישור
approveAndVerifyInstructions(user, pass)                      // אישור עם חתימה דיגיטלית
verifyDoctorApproval()                                         // בדיקה שהאישור עבר
verifySecondTitle(InstructionType type)                        // בדיקה ש-title עדכן

// Management
renewAllInstructions()                                         // חידוש כל ההוראות
```

**📍 עמוד UI:** עמוד הוראות הרופא (תת-עמוד בקטגוריה "הוראות רופא" בתפריט ראשי)

**🔄 זרימת שימוש:**
1. רופא בוחר סוג הוראה (תרופה, נוזל, דם, כללית, תזונה)
2. טופס ייעודי נפתח (DrugFormPage, BloodProductsPage, GeneralInstructionPage)
3. רופא מילא את הטופס
4. רופא לוחץ על כפתור ההוספה או אישור
5. אם אישור - מופיע חלון חתימה דיגיטלית (UserSignModalPage)

**🎯 שימוש בטסטים:**
- `test_07_addingMedicine()` - הוספת תרופה עם אישור
- `test_09_addFluidGeneralBloodProduct()` - הוספת נוזל, הוראה כללית, מוצר דם
- `test_15_addingNutritionByNutritionist()` - הוספת תזונה (דומה לתרופה)

---

## 🏗️ BaseSuit.java - מחלקת בסיס לטסטים

מחלקה הורסת שתהליכים חוזרים על עצמם:

```java
// Login methods
loginAsDoctor()         // התחברות כרופא
loginAsNurse()          // התחברות כאחות
loginAsNutritionist()   // התחברות כתזונאית

// Patient selection
chooseDepartmentVerifyListPatients(dept)
choosePatient(int index)

// Database operations
getDetailsFirstPatient(query)
removePatientDataBeforeTest(query, patientId)

// Doctor instruction page access
doctorInstructionPage.*

// Choose department
chooseDepartmentListPage.*
```

---

## 📚 Constants.java - קבועים

```java
// Credentials
DOCTOR_USERNAME = "doctor"
DOCTOR_PASSWORD = "password"
NURSE_USERNAME = "nurse"
NURSE_PASSWORD = "password"
NUTRITIONIST_USERNAME = "nutritionist"
NUTRITIONIST_PASSWORD = "password"

// Environment
CURRENT_ENV = "qa"  // או "dev", "prod"

// Departments
ICU_DEPARTMENT_STRING = "..."
```

---

---

## 🎬 זרימת טסטים מפורטת (עם שמות עמודים בקוד)

### **test_01 - בדיקת התחברות רופא**
```
LoginPage
  ├─ navigateToEMR("qa")
  ├─ login("doctor", "password", "רופא")
  └─ לאחר התחברות: ChooseRolePage → בחירת "רופא" → MainMenuPage
```

### **test_07 - הוספת תרופה (רופא)**
```
LoginPage
  └─ login("doctor", "password", "רופא")
ChooseDepartmentListPage
  └─ selectDepartment("ICU")
PatientsListPage
  └─ choosePatient(1)
PatientBoxPage
  └─ verifyPatientDetailsExisting() ✓
DoctorInstructionPage
  ├─ clickButtonAddInstruction(MEDICINE)
  ├─ DrugFormPage - הזנת פרטי התרופה
  └─ approveAndVerifyInstructions("doctor", "password")
     └─ UserSignModalPage - חתימה דיגיטלית
```

### **test_10 - אישור הוראות ע"י אחות**
```
LoginPage
  └─ login("nurse", "password", "אחות")
ChooseDepartmentListPage
  └─ selectDepartment("ICU")
PatientsListPage
  └─ choosePatient(1)
ApprovalInstructionPage1
  └─ approveAllInstructions("nurse", "password")
     └─ UserSignModalPage - חתימה דיגיטלית
```

### **test_13 - ביצוע הוראות קרדקס (אחות)**
```
LoginPage
  └─ login("nurse", "password", "אחות")
ChooseDepartmentListPage
  └─ selectDepartment("ICU")
PatientsListPage
  └─ choosePatient(1)
CardexPageNew
  ├─ clickArrowForwardToInnerMenu()
  ├─ [טבלה של הוראות + שעות → checkboxes]
  └─ executeAndApproveAllToThisShiftAndApproval("nurse", "password")
     └─ UserSignModalPage - חתימה דיגיטלית
```

### **test_15 - הוספת תזונה (תזונאית)**
```
LoginPage
  └─ login("nutritionist", "password", "תזונאית")
ChooseDepartmentListPage
  └─ selectDepartment("ICU")
PatientsListPage
  └─ choosePatient(1)
DoctorInstructionPage
  ├─ clickButtonAddInstruction(NUTRITION)
  ├─ DrugFormPage - הזנת פרטי התזונה
  └─ approveAndVerifyInstructions("nutritionist", "password")
     └─ UserSignModalPage - חתימה דיגיטלית
```

---

## 📍 מפת עמודים וקלאסים מלאה

| קלאס Java | עמוד UI | תפקיד |
|---|---|---|
| **LoginPage.java** | Login Page | כניסה למערכת |
| **ChooseRolePage.java** | Role Selection | בחירת תפקיד |
| **MainMenuPage.java** | Main Menu | תפריט ראשי לאחר כניסה |
| **ChooseDepartmentListPage.java** | Department Dropdown | בחירת מחלקה (ICU, Pediatrics וכו') |
| **PatientsListPage.java** | Patients Table | טבלה של מטופלים בחוק |
| **PatientBoxPage.java** | Patient Header | Header עם פרטי מטופל |
| **DoctorInstructionPage.java** | Doctor Instructions | הוספה/אישור הוראות רופא |
| **DrugFormPage.java** | Drug Form Modal | טופס הוספת תרופה/נוזל/תזונה |
| **BloodProductsPage.java** | Blood Form Modal | טופס הוספת מוצר דם |
| **GeneralInstructionPage.java** | General Form Modal | טופס הוראה כללית |
| **ApprovalInstructionPage1.java** | Approval Instructions | אישור הוראות רופא ע"י אחות |
| **CardexPageNew.java** | Cardex Table | טבלה ביצוע הוראות בשעות |
| **UserSignModalPage.java** | Digital Sign Modal | חלון חתימה דיגיטלית (אישור) |
| **FollowupPage.java** | Followup Notes | הערות עקביות (רופא) |
| **WoundPage.java** | Wound List | רשימת פצעים |
| **WondFormPage.java** | Wound Form | טופס הוספת פצע |

---

## 🎯 טבלת השוואה: מה קורה בכל רשות

| שלב | רופא | אחות | תזונאית |
|---|---|---|---|
| **כניסה** | LoginPage → "רופא" | LoginPage → "אחות" | LoginPage → "תזונאית" |
| **בחירת מטופל** | ChooseDepartmentListPage + PatientsListPage | ChooseDepartmentListPage + PatientsListPage | ChooseDepartmentListPage + PatientsListPage |
| **פעולה עיקרית** | DoctorInstructionPage (הוספה + אישור) | ApprovalInstructionPage1 (אישור) + CardexPageNew (ביצוע) | DoctorInstructionPage (הוספה תזונה) |
| **אישור** | UserSignModalPage (רופא חותם) | UserSignModalPage (אחות חותמת) | UserSignModalPage (תזונאית חותמת) |

---

## 🔴 **LaniadoProd - היסוד החסר בקוד**

### **איפה זה מופיע?**
```
בעמוד Cardex (CardexPageNew):
┌─────────────────────────────────────────────┐
│ Laniado Hospital EMR | [LaniadoProd]  ← HERE │
├─────────────────────────────────────────────┤
│ קרדקס - ביצוע הוראות                       │
│ [טבלה של הוראות בשעות...]                  │
└─────────────────────────────────────────────┘
```

### **מה זה LaniadoProd?**
- Element בטופ-לפט של Header (סרגל עליון)
- מציג סימון "Laniado Production" או דומה
- **מטרת הטסט:** בדיקה שמתחברים ל-Production environment בהצלחה

### **📝 HeaderPage.java - קלאס חדש שיוצר**

```java
package pages;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderPage extends BasePage {
    
    // Locators
    private By laniadoProdElement = By.xpath("//..."); // צריך למצוא את ה-XPath ה-נכון
    
    // Functions
    public boolean isLaniadoProdDisplayed() {
        return UIActions.isElementPresent(laniadoProdElement);
    }
    
    public void verifyLaniadoProdElement() {
        assertTrue(isLaniadoProdDisplayed(), "LaniadoProd element not found");
    }
    
    public String getLaniadoProdText() {
        return UIActions.getText(laniadoProdElement);
    }
}
```

### **📍 אם לא מצאנו את ה-XPath, צריך:**
1. ✅ להריץ את `find_laniado_prod.py` 
2. ✅ להוסיף את ה-XPath ל-HeaderPage
3. ✅ ליצור test_19_loginToProdAsDoctor() עם בדיקת LaniadoProd

---

## ❓ שאלות המשך וחידושים

### **1. DoctorInstructionPage - מצא!**
- ✅ נמצא ב-`src/main/java/pages/DoctorInstructionPage.java` (לא בתיקייה קטנה)
- ✅ מכיל 237 שורות עם הרבה methods

### **2. CardexPage vs CardexPageNew**
- `CardexPage.java` - ישן (לא בשימוש יותר)
- `CardexPageNew.java` - חדש ✅ (בשימוש בטסטים)

### **3. LaniadoProd**
- 🔴 **צריך למצוא את ה-XPath - עדיין לא יודעים**
- צריך להריץ את `find_laniado_prod.py` לחפש בקוד HTML של Production

### **4. ChooseDepartmentListPage**
- ✅ היא popup/modal בחירת מחלקה
- בעלת dropdown עם רשימת מחלקות

### **5. InstructionType enum**
- סוגי הוראות: MEDICINE, FLUID, BLOOD, GENERAL, NUTRITION, IMMEDIATE, וכו'
- משמש ב-DoctorInstructionPage.clickButtonAddInstruction()

---

## 🚀 צעדים הבאים

1. **קביעת LaniadoProd XPath** - הריץ `find_laniado_prod.py`
2. **יצירת HeaderPage.java** - עם בדיקת LaniadoProd
3. **הוספת test_19** - בדיקת התחברות Prod עם LaniadoProd verification
4. **עדכון BaseSuit** - הוספת HeaderPage בעצמי שם צריך

