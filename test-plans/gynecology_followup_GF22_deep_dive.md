# GF-22 Deep Dive: Concurrent save / DB-injection resilience

Status: Saved for deep investigation — do not run in standard regression until reviewed.

Purpose
- Test concurrency/conflict handling when an external actor (DB/script/another UI session) updates the patient's followup records concurrently with UI save.

Requirements
- DB access with a test account capable of inserting/updating followup records for the target patient.
- A controllable timing mechanism or script to perform the concurrent update at the precise moment of UI save.
- Test data isolation and rollback scripts.

Steps (recommended approach)
1. Prepare patient context: ensure patient has no pending followup entries for the test window.
2. Open UI and begin adding a followup but do NOT click final Save yet.
3. From a separate DB session/script, insert/update the followup record for the same patient (simulate another user). Record the exact timestamp and content used.
4. Immediately after DB action completes, click Save in UI.
5. Observe application behavior:
   - Does app show conflict dialog? Does it overwrite? Does it merge? Does it silently accept?
6. Validate final state in UI and DB: ensure behavior matches product spec.

Verification
- If product spec defines conflict handling, assert that behavior precisely.
- If undefined, assert the application surfaces a clear, actionable message rather than failing silently.

Notes
- This test is complex and fragile; keep in a separate deep-dive suite and run only in controlled environments.
- Coordination with DB/DevOps may be required.
