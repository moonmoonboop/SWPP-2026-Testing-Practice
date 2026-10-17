# Spec: `DueDateUtils.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `dueDateLabel(dueDate: LocalDate?, today: LocalDate, isCompleted: Boolean): String`

**Intent**: Return the D-day label for a task based on its due date and completion status.

**Pre-conditions**
- `today` is the current date used for this calculation.

**Post-conditions**
- Success cases
  - A completed task returns `CLEAR`. `[Unit]`
    - Tip: Given a completed task with a due date, When its label is requested, Then the result is `CLEAR`.
  - An active task with no due date returns an empty string. `[Unit]`
    - Tip: Given an active task with no due date, When its label is requested, Then the result is `""`.
  - An active task due today returns `D-Day`; a future task returns a `D-N` label; an overdue task returns a `D+N` label. `[Unit]`
    - Tip: Given today is 2026-10-17, When due dates are 2026-10-17, 2026-10-20, and 2026-10-15, Then results are `D-Day`, `D-3`, and `D+2`.
- Error cases
  - None; nullable due dates are valid inputs.
- Edge cases
  - The day before and day after today return `D+1` and `D-1`. `[Unit]`
    - Tip: Given today is 2026-10-17, When due dates are 2026-10-16 and 2026-10-18, Then results are `D+1` and `D-1`.

**Undefined behavior** (optional)
- None.
