# Gynecology Followup (פולואפ מיילדותי) — Test Plan

Status: Draft — awaiting approval

Scope
- Screen: Gynecology Followup / פולואפ מיילדותי (POM: `pages.midwife.GynecologyFollowupPage`)
- Environment: `qa` (URL: https://lanwebapptest.laniado.org.il/emr2/)
- Roles: Doctor (`Constants.DOCTOR_*`) and Midwife/Nurse where noted

Prerequisites
- Test account: `test` / `Te231121` (role selected by login flow)
- Patient data: use first patient in `מיון נשים` department (method: `WomenEmergencyPatientsPage.choosePatient(1)`)
- Tests run with `-Denv=qa`
- All interactions must use `UIActions` wait helpers and POM methods; avoid raw `Thread.sleep` unless noted and wrapped.

General verification rules
- After every action verify an immediate, concrete UI state change (element visible, input value equals expected, modal opens/closes, history updated).
- Use `GynecologyFollowupPage.getPageStatus()` as baseline verification on screen load.
- One primary assertion per test; supporting assertions only when strictly necessary.

Test Scenarios (design IDs, priority, brief steps, expected result)

1) GF-01 — Page Load and Baseline Elements (P0)
- Steps: Login as Doctor → choose `מיון נשים` → select first patient → ensure `GynecologyFollowupPage` loaded
- Verify: `getPageStatus()` returns true or log warnings; essential elements (monitor/water/epidural/pitocin/zeruz) present or reported

2) GF-02 — Save Basic SOAP Follow-up (P0)
- Steps: Open page → fill `subjective/objective/assessment/plan` with short text → click Save → sign modal (if appears) → verify follow-up appears in history
- Verify: New history entry exists; trash button for new entry visible; `verifyFollowupSaved()` assertion

3) GF-03 — PV Section Round-Trip (P1)
- Steps: Open PV tab → select first option in each dropdown (location, fetal position, head height, texture, presentation) → set numeric fields (effacement=5, dilation=8, weight clinical=3.1, weight US=3.8) → save → re-open and assert values persisted
- Verify: Inputs show the values (via `getAttribute('value')` or relevant UI text) and first-option texts match selection

4) GF-04 — Monitoring Section Values (P1)
- Steps: Open Monitoring tab → set decelerations/category/variability (first choices) → set BL=140 & frequency=10 → select rhythm=regular & accelerations=yes → save → re-open Monitoring and assert values
- Verify: Numeric inputs and selected radio/option states equal expected

5) GF-05 — Water Drop Modal Flow (P1)
- Steps: Click Water Circle → wait for Water Drop modal → assert modal title visible → fill required fields (if any) → Save (use sign modal if required) → assert modal closed and followup history updated if applicable
- Verify: `isWaterDropModalTitleVisible()` true before save; false after save; submission success indicator or updated history

6) GF-06 — Epidural Modal Flow (P1)
- Steps: Click Epidural Circle → wait for modal → fill fields → Save with sign → verify modal closed and UI updated
- Verify: `EpiduralModalPage.isEpiduralModalVisible()` toggles correctly and modal fields accepted

7) GF-07 — Obstetric Indicators Quick-Toggle (P2)
- Steps: Click monitor/water/epidural/pitocin/zeruz buttons in sequence → observe immediate UI changes (marker set, modal open for those that open) → cancel/close when modal opens
- Verify: Each click produces expected immediate UI state (class toggles, modal open). No uncaught exceptions.

8) GF-08 — Negative: Missing Required SOAP Field (P2)
- Steps: Clear `subjective` and other SOAP fields → click Save → assert validation message displayed and followup not saved
- Verify: Validation UI element present and `getFollowupHistoryCount()` unchanged

9) GF-09 — Large Input Persistence (P2)
- Steps: Enter very large text (~5000 chars) into `subjective` → Save → reload followup → assert text persisted (or truncated per UI policy) and UI remains responsive
- Verify: Text value equals original or matches allowed truncation; no UI breakage

10) GF-10 — Concurrency / DB-Injection Resilience (P3)
- Steps: (Optional, requires DB helper) Prepare concurrent update via SQL for this patient's followups → attempt to save via UI → assert application displays reconciliation/error message or gracefully overwrites as per product spec
- Verify: UI shows expected conflict handling behavior

11) GF-11 — Accessibility / Focus & Keyboard (P3)
- Steps: Tab through main inputs, ensure focus order logical, Enter triggers Save where applicable; test keyboard-only modal open/close
- Verify: Focusable elements reachable; keyboard actions perform as expected

Data & Test Isolation
- Prefer idempotent actions: create entries under test user, and clean by deleting created followups when possible. Use DB cleanup tasks if available.

Execution notes
- Group tests so that navigation (department + patient selection) runs in `@BeforeMethod` for each test to ensure independence.
- For modal flows that require signing, reuse `Constants.DOCTOR_*` credentials. If signature modal fails intermittently, fallback to test that validates modal presence and skip sign submission (mark as manual step).

Approval requested
- Please review the scenario list and mark which scenarios to implement first (recommended: GF-01..GF-06). After your approval I'll implement tests and run them.
