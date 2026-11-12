# Implementation plan: Clean up remaining pre-existing Checkstyle/PMD/SpotBugs findings

## Task summary

Issue #23 is the follow-up to #15. It asks for the static-analysis findings that existed before #15 to be fixed, or
deliberately suppressed with a written justification, with no behavior change (existing tests pass unchanged) and no
rule regressing.

Source: GitHub issue #23
Base branch: develop

Exploration found that the issue's Checkstyle numbers (1132 `LineLength`, `FinalClass`, `FinalParameters`,
`DesignForExtension`, ...) come from a **direct** `mvn checkstyle:checkstyle` run, which ignores `checkstyle.xml`:
the plugin is only configured under `<reporting>` in `pom.xml`, so direct runs fall back to Checkstyle's built-in Sun
rules (80 columns). A fresh run against the project's own `checkstyle.xml` (120 columns) gives 139 findings, not 1215.
Fresh baseline measured on this branch (JDK 21, `JAVA_HOME` must point to a 21 JDK):

- Direct run (Sun defaults): 1184 Checkstyle (LineLength 1101, MagicNumber 33, FinalClass 19, RedundantModifier 8,
  OperatorWrap 7, HiddenField 6, FinalParameters 2, NoWhitespaceBefore 2, MethodLength 1, InnerAssignment 1,
  RegexpSingleline 1, VisibilityModifier 1, DesignForExtension 1, NewlineAtEndOfFile 1), PMD 2, SpotBugs 1 (the
  issue says 3; Task 1 re-measures).
- Against `checkstyle.xml`: 139 (Indentation 88, MagicNumber 33, RedundantModifier 8, HiddenField 6,
  MultipleStringLiterals 1, LineLength 1, VisibilityModifier 1, NewlineAtEndOfFile 1).

Choices made on the user's behalf (challenge during review):
- **Baseline config**: wire `checkstyle.xml` into the plugin's `<build>` configuration so direct runs and `mvn site`
  agree (user choice).
- **Scope**: user chose "fix everything, no suppressions". Every finding under `checkstyle.xml` plus the PMD and
  SpotBugs findings is fixed in code. The Sun-only rules that are cheap and risk-free (`FinalClass`, `OperatorWrap`,
  `NoWhitespaceBefore`, `InnerAssignment`, `FinalParameters`, `RegexpSingleline`, `MethodLength`) are fixed too, so a
  Sun-defaults run is also clean on them. `DesignForExtension` is fixed with Javadoc or `final` only if no subclass or
  test overrides the method; otherwise it is the single justified suppression.
- **80-column LineLength is not rewrapped**: once `checkstyle.xml` is wired, the enforced limit is 120, and rewrapping
  1100 lines would add churn without being a rule of the project. Only the single >120 line is fixed.
- **SpotBugs `CT_CONSTRUCTOR_THROW`** (`MeasureFormatter`, `Measurement`): fix by validating arguments in a
  `private static` helper called as an argument to `this(...)`/`super(...)` or by making the class `final`/
  constructor-safe, only where behavior is unchanged; if a fix would change public behavior it is suppressed with
  `@SuppressFBWarnings`-style justification (the only exception to "fix everything"), and called out in the PR.
- **`MC_OVERRIDABLE_METHOD_CALL_IN_CLONE`**: fix by not calling overridable methods in `clone()`.

Open clarifications: none

## Acceptance criteria coverage

| # | Criterion | Covered by tasks |
|---|---|---|
| 1 | "Fix each rule above, or deliberately suppress it or change the configuration with a written justification." | Task 1, Task 2, Task 3, Task 4, Task 5, Task 6, Task 7, Task 8 |
| 2 | "No behavior change: the existing test suite passes unchanged." | Task 9 |
| 3 | "No regression of any rule against the counts above." | Task 1, Task 9 |

## Current code state

- Single package `com.irurueta.units`, Java 21, Maven (no wrapper). `checkstyle.xml` (120 cols, `Indentation`,
  `MagicNumber`, `MultipleStringLiterals`, `HiddenField`, `RedundantModifier`, `VisibilityModifier`, ...) is referenced
  only in `pom.xml` `<reporting>` (`maven-checkstyle-plugin` 3.6.0). PMD 3.27.0 and SpotBugs 4.9.6.0 are report-only.
- `MeasureFormatter.java` (630 lines): `RedundantModifier` (7), `HiddenField` (2), `OperatorWrap` (2),
  `NoWhitespaceBefore` (2), `VisibilityModifier` (1), `DesignForExtension` (1), SpotBugs `CT_CONSTRUCTOR_THROW` (line
  ~80) and `MC_OVERRIDABLE_METHOD_CALL_IN_CLONE` (line ~628).
- `Measurement.java` (158 lines): `MagicNumber` (3), `OperatorWrap` (5), `HiddenField` (4), `CT_CONSTRUCTOR_THROW`
  (line ~47).
- `BuildInfo.java`: `RedundantModifier`, `InnerAssignment`, PMD `UnnecessaryModifier` (line ~130), `FinalClass`.
  `package-info.java`: `NewlineAtEndOfFile`.
- `TimeFormatter.java` (1547 lines): `MagicNumber` (24), `MultipleStringLiterals` (1), `MethodLength` (1), PMD
  `UselessParentheses` (line ~1031).
- `*Converter` classes: `FinalClass` (19, together with `BuildInfo`, `UnitLocale`); `TemperatureConverter`:
  `MagicNumber` (6). `Temperature.java`: one line over 120 columns.
- `*Unit` enums: `Indentation` (88) in the `get*Units()` array initializers (see #15 and
  `.archive/implementation_plan_15.md`; fresh run shows them again, so re-check the expected indentation).
- `MagneticFluxDensityFormatter.java` (`FinalParameters`, `RegexpSingleline`), `AngularSpeed.java`
  (`FinalParameters`).
- Tests mirror classes 1:1 in `src/test/java/com/irurueta/units/` and must pass unchanged.
- Docs (`docs/`) describe public API only; no doc change expected.
- Precedent: `.archive/implementation_plan_15.md` (re-measure first, per-rule tasks, final gate).

## Implementation steps

### Group 1 (Parallelizable: yes)

- [x] Task 1. Wire `checkstyle.xml` and record the baseline _(java)_
  - [x] Task 1.1. In `pom.xml`, add `maven-checkstyle-plugin` 3.6.0 to `<build><plugins>` with
    `<configLocation>checkstyle.xml</configLocation>` so `mvn checkstyle:check` and `mvn checkstyle:checkstyle` use the
    project config like `mvn site` already does.
  - [x] Task 1.2. With `JAVA_HOME` on JDK 21, run `mvn clean compile checkstyle:checkstyle pmd:pmd
    spotbugs:spotbugs` and record per-rule/per-file counts (Checkstyle with `checkstyle.xml`, and once with the Sun
    defaults via `-Dcheckstyle.config.location=sun_checks.xml`), PMD and SpotBugs, in the working notes for the final
    comparison in Task 9. Delegate to `iru-gate-runner` (`Skill iru-java-code-quality`).
    - Working note (baseline, confirmed): checkstyle.xml 139 = Indentation 88, MagicNumber 33, RedundantModifier 8,
      HiddenField 6, MultipleStringLiterals 1, LineLength 1, VisibilityModifier 1, NewlineAtEndOfFile 1. Sun defaults
      1184 = LineLength 1101, MagicNumber 33, FinalClass 19, RedundantModifier 8, OperatorWrap 7, HiddenField 6,
      FinalParameters 2, NoWhitespaceBefore 2, MethodLength 1, InnerAssignment 1, RegexpSingleline 1,
      VisibilityModifier 1, DesignForExtension 1, NewlineAtEndOfFile 1 (reused from pre-measured figures; the pom's
      explicit configLocation overrides `-Dcheckstyle.config.location`, so re-measure Sun by temporarily editing it).
      PMD 2 (BuildInfo.java:130 UnnecessaryModifier, TimeFormatter.java:1030 UselessParentheses). SpotBugs 3
      (MeasureFormatter.java:78 CT_CONSTRUCTOR_THROW, :628 MC_OVERRIDABLE_METHOD_CALL_IN_CLONE, Measurement.java:45
      CT_CONSTRUCTOR_THROW). Task 1.1 only touched pom.xml; no tests/coverage affected.

### Group 2 (Parallelizable: yes — each task edits a disjoint set of source files and no tests)

- [x] Task 2. Clean up `MeasureFormatter.java` _(java)_ — done; 1346 existing tests pass unchanged, checkstyle/PMD/SpotBugs clean for touched files.
  - [x] Task 2.1. Remove the 7 redundant modifiers; rename the 2 hiding parameters; fix the 2 `OperatorWrap` and 2
    `NoWhitespaceBefore` spots; make the `VisibilityModifier` field private with accessor, or `protected final` only if
    subclasses need it (check all `*Formatter` subclasses compile).
  - [x] Task 2.2. `DesignForExtension`: add Javadoc or `final` where no subclass/test overrides the method.
  - [x] Task 2.3. SpotBugs: remove `CT_CONSTRUCTOR_THROW` (move argument validation into a static helper used in the
    constructor call chain) and `MC_OVERRIDABLE_METHOD_CALL_IN_CLONE` (do not call overridable methods in `clone()`).
    No behavior change: same exceptions, same messages.
- [x] Task 3. Clean up `Measurement.java` _(java)_ — done; 1346 existing tests pass unchanged, checkstyle/PMD/SpotBugs clean for touched files.
  - [x] Task 3.1. Replace the 3 magic numbers by named `private static final` constants (with Javadoc).
  - [x] Task 3.2. Fix 5 `OperatorWrap`, rename the 4 hiding parameters, and fix `CT_CONSTRUCTOR_THROW` as in Task 2.3.
- [x] Task 4. Clean up `BuildInfo.java`, `package-info.java`, `UnitLocale.java` _(java)_ — done; 1346 existing tests pass unchanged, checkstyle/PMD/SpotBugs clean for touched files.
  - [x] Task 4.1. `BuildInfo`: remove the redundant modifier and the PMD `UnnecessaryModifier` (line ~130), split the
    `InnerAssignment`, make the class `final`.
  - [x] Task 4.2. `package-info.java`: add the trailing newline. `UnitLocale`: make the class `final`.
- [x] Task 5. Clean up `TimeFormatter.java` _(java)_ — done; 1346 existing tests pass unchanged, checkstyle/PMD/SpotBugs clean for touched files.
  - [x] Task 5.1. Replace the 24 magic numbers with named constants.
  - [x] Task 5.2. Fix the `MultipleStringLiterals` literal with a constant, split the over-long method
    (`MethodLength`), remove the useless parentheses (PMD, line ~1031).
- [x] Task 6. Converters and `Temperature` _(java)_ — done; 1346 existing tests pass unchanged, checkstyle/PMD/SpotBugs clean for touched files.
  - [x] Task 6.1. Make every `*Converter` class `final` (19 `FinalClass` findings; private constructors already exist).
  - [x] Task 6.2. `TemperatureConverter`: name the 6 magic numbers as constants. `Temperature.java`: wrap the single
    line over 120 columns.
- [x] Task 7. `*Unit` enum indentation _(java)_ — done; 1346 existing tests pass unchanged, checkstyle/PMD/SpotBugs clean for touched files.
  - [x] Task 7.1. Fix the 88 `Indentation` findings in the `get*Units()` array initializers of all `*Unit` enums,
    re-deriving the expected indentation from a fresh run (see Task 1) and from `.archive/implementation_plan_15.md`.
- [x] Task 8. Remaining formatter/measurement items _(java)_ — done; 1346 existing tests pass unchanged, checkstyle/PMD/SpotBugs clean for touched files.
  - [x] Task 8.1. `MagneticFluxDensityFormatter.java`: fix `FinalParameters` and the `RegexpSingleline` match.
  - [x] Task 8.2. `AngularSpeed.java`: fix the `FinalParameters` finding.

### Group 3 (Parallelizable: yes — single task, validates everything once)

- [x] Task 9. Full validation _(java)_
  - [x] Task 9.1. Delegate to `iru-gate-runner`: run the full test suite (`mvn test`, JDK 21) and confirm all tests
    pass with no test edits (`git diff --stat src/test` is empty), plus 80% coverage.
  - [x] Task 9.2. Delegate to `iru-gate-runner`: re-run Checkstyle (`checkstyle.xml`, and a Sun-defaults run),
    PMD and SpotBugs; compare to the Task 1 baseline: every rule must be at 0, or the single remaining
    justified suppression is documented; no rule may increase. License headers and Javadoc audit on touched files.
  - [x] Task 9.3. Add a `CHANGELOG.md` entry under Unreleased (Changed: static analysis cleanup, Checkstyle config
    wired into the build) if the project keeps one for such changes.
    - Validation note: 1346 tests pass, no test edits; overall coverage 99.7% line / 98.1% branch (Measurement.java
      branch 73.8%, pre-existing); checkstyle.xml 0, PMD 0, SpotBugs 0; Sun defaults only 80-col LineLength (1128,
      out of scope per plan); no suppressions; CHANGELOG.md updated.
