# Spec: `TasksListBindings.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `setDueDateLabel(textView: TextView, task: Task?)`

**Intent**: Display the task's current due-date label using the intent from slide 53 and the feature PR. Each binding must replace any previous label, including removing `CLEAR` when a no-date task is unchecked.

**Pre-conditions**
- Call with a valid Android `TextView` on the main thread; `task` may be null.
- T denotes `LocalDate.now()` in the device's local time zone. The date must remain unchanged during each short binding assertion. Literal examples below use T = `2026-10-17`; runtime test inputs are computed from T because this function has no clock parameter.

**Post-conditions**
- Success cases
  - For an active task with `dueDate = T.plusDays(0)`, set text to `D-Day` and visibility to `VISIBLE`. For example, T = `2026-10-17` gives dueDate = `2026-10-17`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_dueToday_updatesExpectedText`.
  - For an active task with `dueDate = T.plusDays(3)`, set text to `D-3` and visibility to `VISIBLE`. For example, T = `2026-10-17` gives dueDate = `2026-10-20`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_dueInThreeDays_updatesExpectedText`.
  - For an active task with `dueDate = T.plusDays(-2)`, set text to `D+2` and visibility to `VISIBLE`. For example, T = `2026-10-17` gives dueDate = `2026-10-15`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_overdueByTwoDays_updatesExpectedText`.
  - For a completed task with dueDate = `null` when T = `2026-10-17` (runtime input: `null`), set text to `CLEAR` and visibility to `VISIBLE`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_completedWithoutDueDate_updatesExpectedText`.
  - For a completed task with dueDate = `2026-10-15` when T = `2026-10-17` (runtime input: `LocalDate.now().plusDays(-2L)`), set text to `CLEAR` and visibility to `VISIBLE`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_completedWithPastDueDate_updatesExpectedText`.
  - For a completed task with dueDate = `2026-10-17` when T = `2026-10-17` (runtime input: `LocalDate.now().plusDays(0L)`), set text to `CLEAR` and visibility to `VISIBLE`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_completedWithTodayDueDate_updatesExpectedText`.
  - For a completed task with dueDate = `2026-10-20` when T = `2026-10-17` (runtime input: `LocalDate.now().plusDays(3L)`), set text to `CLEAR` and visibility to `VISIBLE`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_completedWithFutureDueDate_updatesExpectedText`.
- Error cases
  - None; a null task is a supported empty binding.
- Edge cases
  - For an active task with `dueDate = T.plusDays(-1)`, set text to `D+1` and visibility to `VISIBLE`. For example, T = `2026-10-17` gives dueDate = `2026-10-16`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_dueYesterday_updatesExpectedText`.
  - For an active task with `dueDate = T.plusDays(1)`, set text to `D-1` and visibility to `VISIBLE`. For example, T = `2026-10-17` gives dueDate = `2026-10-18`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_dueTomorrow_updatesExpectedText`.
  - For an active task with `dueDate = null` and a view initially containing `""`, the text remains exactly `""`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_activeWithoutDueDate_updatesExpectedText`.
  - For an active task with `dueDate = null`, a view initially showing `CLEAR` must be cleared to exactly `""`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_activeWithoutDueDateClearsOldLabel_updatesExpectedText`.
  - For `task = null`, a view initially showing `D-3` must be cleared to exactly `""`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_nullTaskClearsOldLabel_updatesExpectedText`.
  - When the same task (`id = task-1`, `dueDate = null`) is first completed and then active, the same view must change from `CLEAR` to exactly `""`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_completedToActiveWithoutDueDate_updatesExpectedText`.
  - When an active task with no due date becomes completed, the same view must change from `""` to `CLEAR` and become `VISIBLE`. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_activeToCompletedWithoutDueDate_updatesExpectedText`.
  - When a completed task due `T.plusDays(3)` becomes active, the same view must change from `CLEAR` to `D-3` and remain `VISIBLE` (example: T = `2026-10-17`, dueDate = `2026-10-20`). `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_completedToActiveWithDueDate_updatesExpectedText`.
  - When a view first bound to an active task due `T.plusDays(3)` is rebound to another active task with `dueDate = null`, change `D-3` to exactly `""` (example: T = `2026-10-17`, first dueDate = `2026-10-20`). `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_reusedRowWithoutDueDate_updatesExpectedText`.
  - In the actual task list, a no-date task's checkbox sequence active -> completed -> active must produce label text `""` -> `CLEAR` -> `""` on the same row. `[Integration]`
    - Tested by `TasksDueDateScreenTest.setDueDateLabel_checkThenUncheckNoDateTask_removesClear`.

**Undefined behavior** (optional)
- The feature intent does not specify whether an empty label view must be `GONE`, `INVISIBLE`, or visible with empty text. Tests require empty text and no stale label, and do not impose a layout visibility policy for empty labels.
