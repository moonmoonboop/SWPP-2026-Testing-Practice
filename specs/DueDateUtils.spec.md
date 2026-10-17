# Spec: `DueDateUtils.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `dueDateLabel(dueDate: LocalDate?, today: LocalDate, isCompleted: Boolean): String`

**Intent**: Return `CLEAR` for completed tasks, an empty string for active tasks without a due date, and `D-Day`, `D-N`, or `D+N` for active dated tasks. N is the total number of calendar days, including across months, years, and leap day; the oracle comes from slide 53 and the feature PR description.

**Pre-conditions**
- `today` is the caller-supplied reference date; `dueDate` may be null. Either completion value is valid.

**Post-conditions**
- Success cases
  - Given `today = 2026-10-17`, `dueDate = null`, and `isCompleted = true`, return exactly `CLEAR`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_completedWithoutDueDate_returnsExpectedLabel`.
  - Given `today = 2026-10-17`, `dueDate = 2026-10-15`, and `isCompleted = true`, return exactly `CLEAR`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_completedWithPastDueDate_returnsExpectedLabel`.
  - Given `today = 2026-10-17`, `dueDate = 2026-10-17`, and `isCompleted = true`, return exactly `CLEAR`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_completedWithTodayDueDate_returnsExpectedLabel`.
  - Given `today = 2026-10-17`, `dueDate = 2026-10-20`, and `isCompleted = true`, return exactly `CLEAR`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_completedWithFutureDueDate_returnsExpectedLabel`.
  - Given `today = 2026-10-17`, `dueDate = null`, and `isCompleted = false`, return exactly `""`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_activeWithoutDueDate_returnsExpectedLabel`.
  - Given `today = 2026-10-17`, `dueDate = 2026-10-17`, and `isCompleted = false`, return exactly `D-Day`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_dueToday_returnsExpectedLabel`.
  - Given `today = 2026-10-17`, `dueDate = 2026-10-20`, and `isCompleted = false`, return exactly `D-3`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_dueInThreeDays_returnsExpectedLabel`.
  - Given `today = 2026-10-17`, `dueDate = 2026-10-15`, and `isCompleted = false`, return exactly `D+2`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_overdueByTwoDays_returnsExpectedLabel`.
- Error cases
  - None; the typed input domain has no rejected values.
- Edge cases
  - Given `today = 2026-10-17`, `dueDate = 2026-10-16`, and `isCompleted = false`, return exactly `D+1`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_dueYesterday_returnsExpectedLabel`.
  - Given `today = 2026-10-17`, `dueDate = 2026-10-18`, and `isCompleted = false`, return exactly `D-1`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_dueTomorrow_returnsExpectedLabel`.
  - Given `today = 2026-10-17`, `dueDate = 2026-11-16`, and `isCompleted = false`, return exactly `D-30`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_futureBeforeMonthAnniversary_returnsExpectedLabel`.
  - Given `today = 2026-10-17`, `dueDate = 2026-11-17`, and `isCompleted = false`, return exactly `D-31`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_futureOnMonthAnniversary_returnsExpectedLabel`.
  - Given `today = 2026-10-17`, `dueDate = 2026-11-18`, and `isCompleted = false`, return exactly `D-32`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_futureAfterMonthAnniversary_returnsExpectedLabel`.
  - Given `today = 2026-11-17`, `dueDate = 2026-10-16`, and `isCompleted = false`, return exactly `D+32`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_pastBeforeMonthAnniversary_returnsExpectedLabel`.
  - Given `today = 2026-11-17`, `dueDate = 2026-10-17`, and `isCompleted = false`, return exactly `D+31`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_pastOnMonthAnniversary_returnsExpectedLabel`.
  - Given `today = 2026-11-17`, `dueDate = 2026-10-18`, and `isCompleted = false`, return exactly `D+30`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_pastAfterMonthAnniversary_returnsExpectedLabel`.
  - Given `today = 2026-01-30`, `dueDate = 2026-01-30`, and `isCompleted = false`, return exactly `D-Day`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_beforeMonthEnd_returnsExpectedLabel`.
  - Given `today = 2026-01-30`, `dueDate = 2026-01-31`, and `isCompleted = false`, return exactly `D-1`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_onMonthEnd_returnsExpectedLabel`.
  - Given `today = 2026-01-30`, `dueDate = 2026-02-01`, and `isCompleted = false`, return exactly `D-2`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_afterMonthEnd_returnsExpectedLabel`.
  - Given `today = 2026-12-30`, `dueDate = 2026-12-30`, and `isCompleted = false`, return exactly `D-Day`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_beforeYearEnd_returnsExpectedLabel`.
  - Given `today = 2026-12-30`, `dueDate = 2026-12-31`, and `isCompleted = false`, return exactly `D-1`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_onYearEnd_returnsExpectedLabel`.
  - Given `today = 2026-12-30`, `dueDate = 2027-01-01`, and `isCompleted = false`, return exactly `D-2`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_afterYearEnd_returnsExpectedLabel`.
  - Given `today = 2024-02-28`, `dueDate = 2024-02-28`, and `isCompleted = false`, return exactly `D-Day`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_beforeLeapDay_returnsExpectedLabel`.
  - Given `today = 2024-02-28`, `dueDate = 2024-02-29`, and `isCompleted = false`, return exactly `D-1`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_onLeapDay_returnsExpectedLabel`.
  - Given `today = 2024-02-28`, `dueDate = 2024-03-01`, and `isCompleted = false`, return exactly `D-2`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_afterLeapDay_returnsExpectedLabel`.
  - Given `today = 2024-03-01`, `dueDate = 2024-02-28`, and `isCompleted = false`, return exactly `D+2`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_overdueAcrossLeapDay_returnsExpectedLabel`.
  - Given `today = 2024-03-01`, `dueDate = 2024-02-29`, and `isCompleted = false`, return exactly `D+1`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_overdueOnLeapDay_returnsExpectedLabel`.
  - Given `today = 2025-02-28`, `dueDate = 2025-03-01`, and `isCompleted = false`, return exactly `D-1`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_nonLeapFebruary_returnsExpectedLabel`.
  - Given `today = 2026-10-17`, `dueDate = 2027-10-17`, and `isCompleted = false`, return exactly `D-365`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_futureWholeYear_returnsExpectedLabel`.
  - Given `today = 2027-10-17`, `dueDate = 2026-10-17`, and `isCompleted = false`, return exactly `D+365`. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_pastWholeYear_returnsExpectedLabel`.

**Undefined behavior** (optional)
- None within the typed input domain.
