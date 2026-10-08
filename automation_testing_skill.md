# Automation Testing Guidelines & Framework Architecture Skill

## 1. Executive Summary & Purpose
This document defines the core architecture, design principles, conventions, and operational guidelines for writing automated test suites for the electronic medical record (EMR) system. 

The primary goals of these guidelines are to ensure:
* **Product Quality & Stability:** High coverage of critical workflows, edge cases, and cross-module synchronization without redundant or overlapping tests.
* **Maintainability:** Consistent use of Page Object Model (POM), unified UI action abstractions, and explicit waiting strategies.
* **Reliability:** Flake-free execution by banning hardcoded delays and enforcing proper wait mechanisms.
 
### Additional Test Organization Rules

- Each specific topic or feature tested will have a single test class whose name ends with the suffix "suite", and all tests verifying that topic must reside in that same class.
- Stick strictly to the test names specified in the Plan.
- You may create a helper test for taking screenshots, but you must not leave it running permanently as it is unnecessary; delete it after its initial run.

---

## 2. Project Directory Structure & Package Layout

The project follows a standard Maven/Java project layout structured by test scope and architectural responsibility:

```text
src/
├── main/
│   └── java/
│       └── pages/                # Page Object Model (POM) classes
└── test/
    └── java/
        ├── regression/           # Regression test suite classes
        └── sanity/               # Sanity/Smoke test suite classes
```

### Architectural Mapping
* **`src/main/java/pages/`**: Contains all Page Object classes representing UI screens, dialogs, popups, and components.
* **`src/test/java/regression/`**: Contains test suite classes for end-to-end (E2E) regression coverage.
* **`src/test/java/sanity/`**: Contains core sanity test suites evaluating critical application paths.

---

## 3. Test Suite & Class Inheritance Rules

### 3.1 Suite Hierarchy & Inheritance
* Every TestNG test class **must extend `BaseSuite`**.
* Base suite handling includes session setup, configuration loading, driver initialization, execution lifecycle management, and database connections.

### 3.2 Regression Suite Construction Rules
When creating or expanding a regression test suite class:
* **Test Count Limit:** Include up to **30 tests** per suite class file.
* **Uniqueness & Non-Redundancy:**
  * Tests must **not** be identical or near-identical.
  * Do **not** create tests that are completely sub-contained within other tests.
  * *Example Rule:* Do **NOT** write a dedicated "Login Test" if every other test in the suite inherently executes a login sequence as part of its setup or main flow.
* **Edge Case Inclusion:** Ensure that boundary values, exceptional conditions, and negative workflows are explicitly represented across the tests rather than testing only standard happy paths.

* **Short, Focused Tests:**
  * Prefer concise, single-purpose tests that validate one behavior or UI element per test.
  * Example: if a screen displays four distinct buttons, create a separate test for each button (one test per button) rather than one long test that exercises all buttons.
  * Keep test implementations minimal — avoid long, multi-step tests with many assertions. Break complex flows into smaller focused tests where practical.
  * Use clear, descriptive test names and aim for one primary assertion per test to improve readability and maintainability.

---

## 4. System Roles & User Authentication Contexts

### 4.1 Role-Based Access Control (RBAC)
The application dynamically alters menus, screens, and component states based on the logged-in user role (e.g., **Doctor**, **Nurse**, or other medical/administrative roles).

### 4.2 Handling Shared vs. Role-Specific Screens
* **Shared Screens:** Some screens are accessible by multiple roles (e.g., both Doctor and Nurse), but exhibit conditional behavior or restricted UI elements.
  * *Example:* Certain action buttons are enabled/interactive for a Doctor but disabled/hidden for a Nurse.
* **Explicit Role Definition:** Every test method or setup routine must explicitly specify the user role being authenticated to establish the correct permission context for the target scenario.

---

## 5. Testing Categorization & Methodology

Automated tests are categorized into three primary testing types:

### 5.1 Functional Tests
* Validate direct system functionality and data persistence.
* **Flow Example:** An action performed by a Nurse (e.g., adding clinical data/vitals) must be followed by immediate validation that the data displays correctly on the screen.
* **Interactive Element & Popup Validation:**
  * Verify that buttons function as expected and trigger proper UI states/dialogs.
  * When a popup containing secondary elements opens, thoroughly validate the popup with a focus on **one primary central scenario**.
  * **Action Consequence Verification:** Always verify the direct outcome of actions (e.g., button color state changes, creation of a new medical order/instruction).

### 5.2 Cross-Module Process & Workflow Tests
* Focus on end-to-end integration flows where an action in one screen dynamically updates or affects another screen/module in the application.
* **Cross-Screen Examples:**
  * **Doctor Orders $
ightarrow$ Nurse Worklist:** A Doctor issues a medical instruction/order $
ightarrow$ Verify that the Nurse screen displays an execution/pending indicator for that item.
  * **Patient Bed Transfer:** A patient is transferred to a new bed $
ightarrow$ Verify updated bed allocation across ward management and patient list screens.

### 5.3 Visual & High-Density Data Tests
* Validate UI rendering and data presentation when handling high data volume (e.g., complex data tables/grids).
* **Sampling Rule:** Implement a **single representative sample test** that checks core dynamic data against expected baseline sources rather than attempting comprehensive matrix iterations.

---

## 6. Assertion Guidelines & Standards

To prevent fragile assertions and slow executions, follow strict assertion rules:

| Category | Allowed / Recommended Rule | Strictly Prohibited |
| :--- | :--- | :--- |
| **Table Assertions** | Validate **a single representative row** per table test. | Do **NOT** iterate through and assert all table rows. |
| **Empty Field Checks** | Assert fields containing actual expected content or state changes. | Do **NOT** perform assertions on empty fields. |
| **Database Counts** | Assert specific record states or key dataset values. | Do **NOT** execute UI count vs. DB record count assertions (`UI.count == DB.count`). |

---

## 7. Execution, Synchronization & Exception Handling

### 7.1 Spinner / Loading Indicator Handling
* A loading spinner appears upon entering a new screen or submitting dialog popups.
* Spinners do **not** (and should not be expected to) appear after every simple micro-interaction.
* Synchronization logic must account for spinner lifecycle (wait for appearance if applicable, and complete disappearance) prior to performing assertions.

### 7.2 Explicit Wait Mandatory Rule
* **Strict Ban on Hard Delays:** NEVER use fixed sleep statements (e.g., `Thread.sleep()`).
* **Framework Abstraction:** All synchronization and waiting must be handled exclusively through standard methods provided in `UIActions` (e.g., `UIActions.waitForVisible(...)`, `UIActions.waitForElementToDisappear(...)`).

### 7.3 Exception Handling & Failure Visibility
* Do **NOT** wrap test logic in `try-catch` blocks to swallow or catch standard assertion/element exceptions within test methods.
* Unhandled exceptions must propagate naturally to allow TestNG and reporting tools (e.g., Allure) to catch, report, and mark failed test steps accurately.

---

## 8. Page Object Model (POM) & Element Locators

### 8.1 Element Locator Naming Conventions
* All elements defined as `By` objects must follow camelCase naming and strictly end with the `By` suffix.
* **Format:** `<elementDescription>By`
* **Examples:** `loginButtonBy`, `patientIdInputBy`, `departmentDropdownBy`.

### 8.2 Locator Strategy (XPath Preference)
* Prefer `XPath` as the primary locator strategy across page objects, as it provides the robust element capturing needed for dynamic EMR structures.

### 8.3 Handling Lists & Dropdown Collections
* Do **NOT** declare individual `By` locators for every item/option inside a dropdown menu or list.
* Define a **single parent list locator** that encompasses all items in the collection.
* Option selection at runtime must be performed by passing an index or parameter through standard wrapper functions.

### 8.4 Reusing UIActions Framework
* Do **NOT** write custom element-level action logic or helper methods inside Page Object classes if equivalent methods exist in `UIActions`.
* Use existing `UIActions` helper functions for all operations (e.g., option selection from dropdowns, clicks, element visibility checks).

### 8.5 Data Helper Objects (DTOs)
* Standard Page Objects do not require manual Getters/Setters.
* Implement Getters and Setters **only** when creating dedicated custom Data Transfer Objects (DTOs) or helper data models designed to support test scenarios.

---

## 9. Integration with Database (DB) and Authentication Modules

* When implementing database validation (JDBC connections, SQL queries) or establishing automated login routines, refer to and comply with the dedicated external project `.md` documentation files (`db-guidelines.md`, `login-flow.md`, etc.).

## 10. General Notes (Logging & Assertions)

- Do not add explanatory comments in the code regarding logging for any type of tests.
  1. Do not write a log entry after every step performed; avoid printing logs for every minor action during a test.
  2. Keep logs only inside the function itself — maintain necessary, focused logs only, because often there is nowhere else appropriate to store or display them.
  3. Use `assert` for test validations instead of `if/else` combined with logging. Tests must fail explicitly via assertions and should not rely on logs to indicate failures.

- Summary: Keep logs minimal, clear, and concentrated within test functions, and prefer `assert` for validations over conditional logic that uses only log messages.

---

## Test Class `setUp()` and Women Emergency patient-selection

- Purpose: Reduce code duplication and ensure all Page Objects used by tests are initialized before each test run.
- Guideline: Every TestNG test class should include a `setUp()` method that initializes all Page Objects the class's tests rely on. When multiple test classes share the same Page Objects, prefer placing common initialization in `BaseSuite` or a shared superclass.

Recommended implementation example (copy into test classes or move into `BaseSuite` when appropriate):

```java
@BeforeMethod
public void setUp() {
  super.setUp(); // ensures driver and global context are initialized

  // Initialize Page Objects commonly used in Women Emergency / Gynecology tests
  womenEmergencyPatientsPage = new WomenEmergencyPatientsPage();
  gynecologyFollowupPage = new GynecologyFollowupPage();
  waterDropModalPage = new WaterDropModalPage();
  epiduralModalPage = new EpiduralModalPage();
  pitocinModalPage = new PitocineModalPage();
  zeruzModalPage = new ZeruzModalPage();
  followupPage = new FollowupPage();
}
```

- Important: Keep initialization in a single place where possible to avoid duplicated setup logic across tests.

- Women Emergency patient-selection note: The patients table in the Women Emergency department differs from other departments (PrimeNG virtualization, column layout, or custom wrappers). After calling `chooseDepartment("מיון נשים")` use the specialized selection method on `WomenEmergencyPatientsPage` rather than a generic helper.

Example usage:

```java
chooseDepartment("מיון נשים");
womenEmergencyPatientsPage.choosePatient(1); // select the first patient using the department-specific POM
```

- Rationale: Using the department-specific chooser avoids Timeouts and locator mismatches caused by different table structures (virtual scroll, custom row templates). If you encounter Timeouts locating patient rows, run the DOM capture test (`exploratory.UIElementCaptureTest`) and update the locator inside `WomenEmergencyPatientsPage` accordingly.


## 11. Selenium MCP Chrome initialization (example)

The snippet below shows a minimal, standards-compliant construction to initialize a Chrome session via a Selenium MCP (remote) server, read the QA URL from `Configuration/DataConfig.xml`, and open the application while using framework wait abstractions.

```java
// Example: src/test/java/framework/DriverFactory.java
public final class DriverFactory {
  private DriverFactory() {}

  public static RemoteWebDriver createRemoteChrome(String mcpUrl) throws MalformedURLException {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--start-maximized");
    options.addArguments("--disable-infobars");

    URL remote = new URL(mcpUrl); // e.g. http://localhost:4444/wd/hub
    return new RemoteWebDriver(remote, options);
  }

  public static String readQaUrl() throws Exception {
    Path cfg = Paths.get("Configuration", "DataConfig.xml");
    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc = db.parse(cfg.toFile());
    XPath xp = XPathFactory.newInstance().newXPath();
    return xp.evaluate("/Environment/qa", doc).trim();
  }

  public static void openApp(RemoteWebDriver driver, String appUrl) {
    driver.get(appUrl);
    // Use framework wait abstraction to ensure end-to-state readiness
    UIActions.waitForVisible(driver, By.xpath("//body"));
  }
}
```

And usage inside `BaseSuite` (TestNG `@BeforeClass`):

```java
@BeforeClass(alwaysRun = true)
public void baseSetUp() throws Exception {
  String mcp = System.getProperty("mcp.url", "http://localhost:4444/wd/hub");
  RemoteWebDriver driver = DriverFactory.createRemoteChrome(mcp);

  String qaUrl = DriverFactory.readQaUrl();
  DriverFactory.openApp(driver, qaUrl);

  // Pause for manual navigation if needed: wait for a known screen sentinel for up to 5 minutes
  UIActions.waitForVisible(driver, By.xpath("//div[contains(@class,'main-screen') or //header]"), Duration.ofMinutes(5));
  // attach driver to test context / BaseSuite for downstream tests
  TestContext.setDriver(driver);
}
```

Notes:
- Replace the sentinel XPath in the `waitForVisible` call with the actual screen element used by your app.
- If your MCP server uses a different endpoint path, override `mcp.url` system property when running tests:

```text
mvn test -Dmcp.url=http://selenium-mcp-host:4444/wd/hub
```

This construction follows the project's POM, dynamic wait, and UIActions patterns.

- Log Message Style Rules:
  - Write log messages using lowercase English letters only, except for standard sentence capitalization (capitalize the first letter of a sentence or proper nouns). Preserve punctuation and sentence casing.
  - Do not print decorative or divider line patterns in logs.
    ```
    or other long line-of-characters dividers.
  - Keep log content concise and meaningful — use logs to provide necessary context, not to trace every low-level action.
