# Gynecology Followup (פולואפ מיילדותי) — Test Plan v2

Status: Draft — awaiting approval

הערה חשובה:
- לתיקון התוכנית נדרש "לפתוח את המערכת" בדפדפן ולתפוס לוקאטורים/אלמנטים שלא קיימים עדיין ב-POM. בכל מקרה שאציין "(דרוש UI capture / new POM)" — אסור להתחיל בכתיבת הבדיקות אוטומטיות עד שאאסוף את הלוקאטורים המדויקים.

מטרה
- כיסוי רגרסיה מלא עבור עמוד Gynecology Followup (פולואפ מיילדותי), כולל אינדיקטורים יולדותיים, מודאלים (ירידת מים, אפידורל, פיטוצין, זרוז), PV, Monitoring, ושמירת פסקי זמן.

סמלים ומהירויות
- Priority: P0 (קריטי), P1 (חשוב), P2 (רצוי)

סביבת בדיקה
- `qa` — https://lanwebapptest.laniado.org.il/emr2/
- חשבון בדיקה: `test` / `Te231121` (תפקיד ייבחר דרך ה-flow)

רשימת בדיקות (30) — כל פריט כולל תקציר צעדיו ותוצאת אימות

GF-01 (P0) — Page load baseline
 - Login doctor; choose מיון נשים; select patient #1; open gynecology followup
 - Verify: `getPageStatus()` true; main indicators visible (monitor, water, epidural, pitocin, zeruz)

GF-02 (P0) — SOAP save minimal
 - Fill Subjective/Object/Assessment/Plan short texts; Save; sign if prompted
 - Verify: new history line exists; `verifyFollowupSaved()` passes. Additionally, assert the latest history entry contains a short snippet of the saved SOAP `subjective` text (persistence check).

GF-03 (P0) — PV: dropdowns basic
 - Preconditions: `loginAsDoctor()` and `chooseDepartment("מיון נשים")` then `womenEmergencyPatientsPage.choosePatient(1)`.
 - Actions: Open PV tab; select the first option for every PV dropdown; fill numeric/text PV inputs (effacement, dilation, weight fields); click Save and perform signature if required.
 - Verify: After save, wait for end-of-state and assert the newest history row contains markers for PV data (e.g., presence of a PV tag/icon or PV summary text). 


GF-04 (P0) — Monitoring: numeric & radio
 - Preconditions: `loginAsDoctor()` and `chooseDepartment("מיון נשים")` then `womenEmergencyPatientsPage.choosePatient(1)`.
 - Actions: Open Monitoring tab; set BL=140, Frequency=10; choose the rhythm radio = Regular; set Accelerations = Yes; click Save and complete signing flow if required.
 - Verify: After save, assert the newest history row contains monitoring markers (BL, Frequency or summary). 

GF-05 (P0) — Water Drop modal open and fill and save and TODAY's date on button
 - Actions: Click the Water indicator; wait for the Water Drop modal to open; fill all required modal fields with representative positive data; click Save/Submit; complete sign flow if required.
 - Verify:
    1. Modal opens and fields are editable.
    2. After saving, the Water indicator button displays TODAY's date (not the date entered in form, but the current system date) in a span inside the button (e.g., inside `<button class="water_circle">...<span>18/09/2026</span></button>`). Assert the date text matches today's date in format dd/MM/yyyy and is not empty.
    3. The Water indicator button CSS class reflects the saved state (assert via class or style attribute).
 - Note: create `WaterDropModal.fillAndSave(...)` POM helper and ensure UI capture is done before implementing. The displayed date should always be TODAY, regardless of any dates entered in form fields.

GF-06 (P0) — Epidural modal fill and save and visual state
 - Actions: Click the Epidural indicator; wait for the Epidural modal; fill required fields (including any drop-downs or numeric inputs); click Save and sign if required.
 - Verify:
    1. Modal opens and data can be entered.
    2. After saving, the Epidural indicator button changes its visual state (CSS class or color) consistent with saved data.
 - Note: implement `EpiduralModal.fillAndSave(...)` in the POM before automating.

GF-07 (P0) — Pitocin (פיטocin) flow fill and save and visual verification
 - Actions: Click the Pitocin indicator; open the Pitocin modal or perform the quick action flow; fill required fields; save and sign if required.
 - Verify:
    1. Modal accepts input and allows saving.
    2. Pitocin indicator visual state updates according to entered data (assert CSS class or attribute change).
 - Note: add `PitocinModal.fillAndSave(...)` helper in the POM.

GF-08 (P0) — Zeruz (Labor Induction) directive — add directive and cross-screen verification
 - Actions: Click the Zeruz indicator; open the Zeruz/Induction modal; create a new Labor Induction directive with representative data; save and sign if required.
 - Verify (in-modal and cross-screen):
    1. The newly added directive appears under the modal heading "Directives Given".
    2. On the same page, the patient appears in the "Pending Induction" list (a local UI list). Assert that the new entry contains key directive details.
   3. Navigate back to the patient list → open the Main Inductions Screen → locate the patient entry and assert all directive details are present and correct (cross-screen persistence).
   4. Main Zeruz-screen category check: From the system main navigation, open the primary Zeruz/Inductions dashboard that lists all patients waiting for induction (the screen is partitioned into categories). Assert the patient appears under the category 'ממתינות לזירוז' and that the entry displays the directive summary (date/time, agent, short note).
   5. Additional verification: If a Zeruz/Induction directive is created from this followup, capture the chosen induction type and assert that the same induction name appears in the patient's row on the Main Zeruz/Inductions dashboard (cross-screen verification).
 - Note: This flow is multi-screen; reuse the existing navigation helpers to reach the Main Inductions/Zeruz dashboard. Implement only the directive-add helper (`ZeruzModal.addDirective(...)`) if it does not already exist in the POMs.

GF-08b (P0) — Zeruz transfer: add directive 'זירוז בחדר לידה' and verify category 'ממתינות להעברה לחדר לידה'
 - Actions: From the patient's Gynecology Followup, open the Zeruz modal and add a new directive. Set the induction type to `זירוז בחדר לידה` (transfer to delivery room), include a short note, and save/sign as required.
 - Verify:
    1. The directive appears in the patient's local `Directives Given` list in the modal.
   2. Capture the patient's display name from the patient followup header (use existing POM helper that returns patient name after opening the patient). Navigate to the Main Inductions/Zeruz dashboard via existing navigation helpers and find an entry under `ממתינות להעברה לחדר לידה` that exactly matches the captured patient name.
   3. The entry shows the transfer directive summary (type=`זירוז בחדר לידה`, snippet of the note, timestamp and user/agent).
 - Note: Reuse existing navigation helpers; implement `ZeruzModal.addDirective(type, note)` helper only if missing.

GF-08c (P0) — Zeruz previous-directive cancelation and popup view
 - Actions: Open patient #1 from `מיון נשים`; add a new Zeruz directive (any type) — expected behavior: the previous directive (created earlier in GF-08b) becomes marked as canceled in the same Zeruz modal under the heading `הנחיות שניתנו` (Previous Directives).
 - Verify in-modal:
    1. The prior directive shows a canceled state/label next to it (or CSS class indicating canceled) in the `הנחיות שניתנו` section.
 - Verify cross-screen (Main Zeruz dashboard):
    1. Navigate back to the patient list and open the Main Inductions/Zeruz dashboard via existing navigation helpers.
    2. Locate the patient row (use captured patient name). Click the `זירוזים קודמים` (Previous Inductions) trigger/button on that patient's row.
    3. Assert a popup appears (see attached screenshot) and that this popup contains the previous directive entry with the expected details (type, timestamp, note).
 - Note: This test requires `ZeruzModal.addDirective(...)` and a POM method to open and read the `זירוזים קודמים` popup from the Main Zeruz screen (e.g., `InductionsPage.openPreviousInductionsPopup(patientName)`). Reuse navigation helpers; capture precise popup locators during UI capture step.

GF-09 (P1) — Monitor indicator + Monitor Note with persistence
 - Actions: Click Monitor indicator; open monitor notes modal; enter a brief monitoring note; save.
 - Verify:
   1. The note appears in the monitor notes list/modal.
   2. After saving and closing, a history entry or monitor-notes area contains the note (persistence), and the Monitor indicator has any visual change expected.

GF-10 (P1) — Copy last follow-up and save as new
 - Actions: Use `copy_lastButton` to copy the last followup into the SOAP fields; modify one field (e.g., Subjective) and Save; sign if required.
 - Verify:
   1. After copying, SOAP fields are pre-populated with previous values.
   2. After modifying and saving, a new history entry is added with the updated field value; confirm history count increased.
   3. Confirm the original history entry remains unchanged (no accidental overwrite).


GF-12 (P1) — Followup history trash/delete
 - Add a followup, then delete via trash icon in history
 - Verify: history count decreased and entry removed
GF-14 (P1) — Copy PV last / clear PV
 - Use `copyLastPVButtonBy` and `clearPVButtonBy` flows; verify copied values and clearing
 - Note: (requires UI capture for copy-last PV confirmation)

GF-15 (P1) — Execution Time inputs (HH/MM)
 - Fill HH and MM inputs; save; verify time persisted and formatted
 - Include both positive and negative cases: valid times accepted and persisted; invalid times rejected with validation messages and no history entry added

GF-16 (P1) — Labor monitoring array inputs (multiple indexed inputs)
 - Fill laborMonitoring-0..n inputs and accelerationMonitoring inputs; save
 - Verify: values persisted and mapped correctly

GF-17 (P1) — Dropdown parent list selection by index
 - For each dropdown control, select option by index=1 and assert visible label matches
 - Note: (requires capturing list locators for dropdowns)

GF-18 (P1) — Save with large inputs (5000 chars)
 - Enter large text in `subjective`; save; re-open; verify persistence or truncation policy

GF-19 (P2) — Missing required field validation
 - Clear required SOAP field(s); click Save; assert validation message shown and no history entry added

GF-21 (P2) — Role-based UI differences (Doctor vs Nurse)
 - Login as Nurse; open same followup; assert certain buttons disabled/hidden (e.g., sign/approve)
 - Note: list exact role-specific elements after UI capture

GF-22 (P2) — Concurrent save conflict handling (DB injection)  (MOVE TO API/DEEP-DIVE BACKLOG)
 - Moved out of the main regression list and saved to the deep-dive backlog for API/DB-assisted testing. See `test-plans/gynecology_followup_GF22_deep_dive.md`.

GF-13 (P1) — Copy PV last / clear PV
 - Use `copyLastPVButtonBy` and `clearPVButtonBy` flows; verify copied values and clearing
 - Note: (requires UI capture for copy-last PV confirmation)

GF-14 (P1) — Execution Time inputs (HH/MM)
 - Fill HH and MM inputs; save; verify time persisted and formatted
 - Include both positive and negative cases: valid times accepted and persisted; invalid times rejected with validation messages and no history entry added

GF-15 (P1) — Labor monitoring array inputs (multiple indexed inputs)
 - Fill laborMonitoring-0..n inputs and accelerationMonitoring inputs; save
 - Verify: values persisted and mapped correctly

GF-16 (P1) — Dropdown parent list selection by index
 - For each dropdown control, select option by index=1 and assert visible label matches
 - Note: (requires capturing list locators for dropdowns)

GF-17 (P1) — Save with large inputs (5000 chars)
 - Enter large text in `subjective`; save; re-open; verify persistence or truncation policy

GF-18 (P2) — Missing required field validation
 - Clear required SOAP field(s); click Save; assert validation message shown and no history entry added

GF-19 (P2) — Role-based UI differences (Doctor vs Nurse)
 - Login as Nurse; open same followup; assert certain buttons disabled/hidden (e.g., sign/approve)
 - Note: list exact role-specific elements after UI capture

GF-20 (P2) — Concurrent save conflict handling (DB injection)  (MOVE TO API/DEEP-DIVE BACKLOG)
 - Moved out of the main regression list and saved to the deep-dive backlog for API/DB-assisted testing. See `test-plans/gynecology_followup_GF22_deep_dive.md`.

GF-21 (P2) — Attachment upload & attachment list
 - Upload an attachment via followup attachment control; verify list shows the attachment and download works

GF-22 (P2) — History filtering / show last 3 days  (MOVE TO API BACKLOG)
 - Moved to API testing backlog as requested; remove from UI regression for now.

GF-24 (P2) — History filtering / show last 3 days  (MOVE TO API BACKLOG)
 - Moved to API testing backlog as requested; remove from UI regression for now.

GF-25 (P2) — Copy last followup then modify and save new
 - Copy last, change one field, save as new; verify history added with changed content


GF-26 (P2) — Followup History table representative row check
 - Target table: the Followup History list (`followup-history` / locator: `//followup-history//div[@class='history-line']` / POM: `FollowupPage.followupHistoryList`).
 - Steps: perform an action that affects history (e.g., add followup, submit Water Drop) → wait for end-of-state → fetch first history row → assert it contains expected summary (SOAP snippet, PV marker, timestamps, user)
 - Verify: first history row text contains the saved content or expected marker; history count increased by one when adding a new entry.

GF-28 (P2) — Visual indicator states (icons/classes) — expanded
 - Scope: monitor, water, epidural, pitocin, zeruz indicator buttons.
 - Variations to test for each indicator:
    1. Click to toggle on → assert CSS class/`aria-pressed` or an `active` attribute is set.
    2. Click to toggle off → assert class/attribute removed.
    3. Click then Save followup → reload page and assert indicator persistent if expected.
    4. If indicator opens a modal (water/epidural/pitocin/zeruz), assert indicator state during modal open, after save, and after cancel.
    5. Rapid toggle stress: toggle quickly 5x and assert no JS exceptions and final state stable.
 - Verify: class or attribute changes detected and persist according to app spec; no console errors.

GF-29 (P2) — Unhandled exceptions & error overlay handling
 - Force an invalid action (e.g., invalid numeric char in number field) and assert app shows graceful error with no console crash

GF-30 (P2) — End-to-end regression sample (SOAP + PV + Monitoring + Water Drop)
 - Fill SOAP, PV, Monitoring, open Water Drop modal and submit, save full followup, verify single-history entry that contains expected markers (e.g., PV data, monitoring values)

אופן פעולה להמשך (לאחר אישורך לתוכנית זו):
1. אני אפתח את ה-UI בדפדפן ואַתפוס את כל הלוקאטורים החסרים (אלמנטים המסומנים כ"דרוש UI capture").
2. אעדכן / אצור POMs חסרים ב-`src/main/java/pages/midwife/` ו-`pages/` אחרים לפי הצורך.
3. אממש את הבדיקות המובחרות (ממליץ להתחיל ב-GF-01..GF-06) בתוך `src/test/java/regression/` תוך שימוש ב-`UIActions` ו-`BaseSuit`.

בקשה לאישור
- אישור דרוש לפני שאני מתחיל בשלב של תפיסת לוקאטורים ובכתיבת בדיקות אוטומטיות. אנא אשר/י את רשימת ה-30 הבדיקות או ציין/י שינויים נדרשים.
