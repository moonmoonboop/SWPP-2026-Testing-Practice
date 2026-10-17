# Spec: `TasksListBindings.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `setDueDateLabel(textView: TextView, task: Task?)`

**Intent**: Display the due-date label for a task in its list item.

**Pre-conditions**
- The task may be null.

**Post-conditions**
- Success cases
  - A task with a label displays that label in the text view. `[Integration]`
    - Tip: Given a task due today, When the list label is set, Then the text view displays `D-Day`.
  - A completed task displays `CLEAR`. `[Integration]`
    - Tip: Given a completed task, When the list label is set, Then the text view displays `CLEAR`.
- Error cases
  - None.
- Edge cases
  - A task with no due date has no label displayed. `[Integration]`
    - Tip: Given an active task with no due date, When the list label is set, Then no due-date label is shown.

**Undefined behavior** (optional)
- None.
