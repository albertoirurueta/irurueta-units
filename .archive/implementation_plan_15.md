# Implementation plan: Clean up Checkstyle/PMD style findings (Force/Pressure/Energy/Power and their templates)

## Task summary

Issue #13 added `Force`, `Pressure`, `Energy` and `Power` by copying the `Speed` family. The copy reproduced style
findings that already exist across the older families. This task removes them for **all** families (new and old), with
no behavior change. Four finding classes are in scope:

1. Checkstyle `Indentation`: array-initializer children in `get*Units()` are indented 16, expected 12.
2. Checkstyle `JavadocStyle`: first sentence of enum-constant Javadoc must end with a period (`Newton (N).` style).
3. Checkstyle `MultipleStringLiterals`: literal `" "` repeated in `*Formatter` classes.
4. PMD `UselessOverridingMethod`: `hashCode()` overrides that only call `super.hashCode()` in every `*Formatter`.

Source: GitHub issue #15
Base branch: develop

Choices made on the user's behalf (challenge during review):
- **Indentation**: reformat the code (12-space array-initializer children). `checkstyle.xml` is not relaxed.
- **JavadocStyle**: rewrite the flagged first sentences so they end in a period, e.g. `Newton, symbol N.` instead of
  `Newton (N).`. Exact flagged lines come from a fresh Checkstyle run (Task 1), not from the issue text.
- **`" "` literal**: add a `private static final String SPACE = " ";` constant per affected formatter and use it in
  place of each literal. No shared helper is introduced, to keep the per-family pattern self-contained.
- **`hashCode()`**: keep the overrides and suppress with `@SuppressWarnings("PMD.UselessOverridingMethod")` plus a
  justification in the existing Javadoc. Removing them would trade the PMD finding for an equals-without-hashCode
  warning from SpotBugs/Checkstyle, since every formatter overrides `equals()`. The issue explicitly allows
  "justify keeping".
- **Baseline**: `target/checkstyle-result.xml` currently on disk is stale (it lists no findings for the new families),
  and the issue's line numbers may have shifted. Task 1 re-measures before editing.
- Out of scope: other Checkstyle findings unrelated to the four classes (`MagicNumber`, `RedundantModifier`,
  `HiddenField`, `LineLength`, `VisibilityModifier`, `NewlineAtEndOfFile`).

Open clarifications: none

## Acceptance criteria coverage

| # | Criterion | Covered by tasks |
|---|---|---|
| 1 | "Fix (or deliberately suppress with justification) the `IndentationCheck` array-initializer findings in `EnergyUnit`/`ForceUnit`/`PowerUnit`/`PressureUnit.java` — and, for consistency, the equivalent findings already present in `SpeedUnit`/`DistanceUnit`/`WeightUnit`/`VolumeUnit`/`TimeUnit`/`SurfaceUnit`/`TemperatureUnit`/`AccelerationUnit.java`." | Task 2, Task 3 |
| 2 | "Fix the `JavadocStyleCheck` missing-period findings in `ForceUnit`/`PressureUnit.java` — and the equivalent findings in `SpeedUnit.java`/`WeightUnit.java`." | Task 2, Task 3 |
| 3 | "Fix (or suppress) the `MultipleStringLiteralsCheck` `\" \"` findings in the four new `*Formatter.java` files — and the equivalent findings already present in the other existing `*Formatter` classes." | Task 4, Task 5 |
| 4 | "Remove (or justify keeping) the `UselessOverridingMethod` `hashCode()` overrides in the four new `*Formatter.java` files — and the equivalent overrides already present in the other 12 existing `*Formatter` classes." | Task 4, Task 5 |
| 5 | "Full `mvn checkstyle:check` / PMD / SpotBugs pass afterward with no regression." | Task 1, Task 6 |

## Current code state

- Single package `com.irurueta.units`, Java 21, Maven. Checkstyle config at `checkstyle.xml` (enables `Indentation`,
  `JavadocStyle`, `MultipleStringLiterals` with defaults). PMD 3.27.0 and SpotBugs 4.9.6.0 run via `mvn site` reporting
  only; no gate fails the build.
- `*Unit` enums: `get{Metric,Imperial}Units()` return `new XUnit[]{ ... }` with children at 16 spaces. Affected:
  Energy, Force, Power, Pressure, Speed, Distance, Weight, Volume, Time, Surface, Temperature, Acceleration.
  `ForceUnit.java` / `PressureUnit.java` / `SpeedUnit.java` / `WeightUnit.java` constants carry Javadoc `Name (sym).`.
- `*Formatter` classes (Energy, Force, Power, Pressure plus Surface, MagneticFluxDensity, Speed, Volume, Weight,
  Frequency, Distance, AngularAcceleration, Acceleration, AngularSpeed, Angle, Temperature, Time): each has
  `@Override public int hashCode() { return super.hashCode(); }` with Javadoc "only calls parent implementation to
  avoid static analyzer warning". `SpeedFormatter.findUnit` repeats `X + " "` five times; the four new formatters use
  `containsSymbol(...)` and showed no `" "` in a local grep, so their reported literals must be re-checked.
- Tests mirror classes 1:1 in `src/test/java/com/irurueta/units/`; they must pass unchanged.
- Docs (`docs/`) describe public API only; no doc change expected.
- Precedent: `.archive/implementation_plan_13.md`.

## Implementation steps

### Group 1 — Baseline (Parallelizable: yes)

- [x] Task 1. Capture the quality baseline _(java)_ — no source changed; baseline recorded below.
  - [x] Task 1.1. Run Checkstyle (`mvn checkstyle:checkstyle`), PMD and SpotBugs through `iru-gate-runner`
    (`Skill({skill: "iru-java-code-quality"})`), and record per-file counts of the four in-scope rules plus totals of
    all other rules (to prove no regression later).
    - Baseline (unscoped iru-java-code-quality, BUILD SUCCESS, JDK 21): totals Checkstyle 1221, PMD 19, SpotBugs 3
      (overall 1243).
    - Checkstyle per rule: LineLength 1132, MagicNumber 33, FinalClass 19, RedundantModifier 8, OperatorWrap 7,
      JavadocStyle 6, HiddenField 6, FinalParameters 2, NoWhitespaceBefore 2, MethodLength 1, InnerAssignment 1,
      RegexpSingleline 1, VisibilityModifier 1, DesignForExtension 1, NewlineAtEndOfFile 1, Indentation 0,
      MultipleStringLiterals 0.
    - PMD: UselessOverridingMethod 17, UnnecessaryModifier 1, UselessParentheses 1. SpotBugs: CT_CONSTRUCTOR_THROW 2,
      MC_OVERRIDABLE_METHOD_CALL_IN_CLONE 1.
    - JavadocStyle: TimeFormatter.java:298, SpeedUnit.java:27,32,37,42, WeightUnit.java:132.
    - UselessOverridingMethod: hashCode() in all 17 *Formatter classes (Acceleration, Angle, AngularAcceleration,
      AngularSpeed, Distance, Energy, Force, Frequency, MagneticFluxDensity, Power, Pressure, Speed, Surface,
      Temperature, Time, Volume, Weight).

### Group 2 — Unit enums (Parallelizable: yes — one file per task, no shared edits)

- [x] Task 2. Fix the new unit enums _(java)_
  - [x] Task 2.1. `EnergyUnit.java`, `ForceUnit.java`, `PowerUnit.java`, `PressureUnit.java`: re-indent array
    initializer children to 12 spaces.
  - [x] Task 2.2. `ForceUnit.java`, `PressureUnit.java`: rewrite flagged constant Javadoc so the first sentence ends
    with a period (e.g. `Newton, symbol N.`).
- [x] Task 3. Fix the older unit enums _(java)_
  - [x] Task 3.1. `SpeedUnit`, `DistanceUnit`, `WeightUnit`, `VolumeUnit`, `TimeUnit`, `SurfaceUnit`,
    `TemperatureUnit`, `AccelerationUnit`: re-indent array initializer children to 12 spaces, plus any other unit enum
    the fresh baseline flags.
  - [x] Task 3.2. `SpeedUnit.java`, `WeightUnit.java`: fix `JavadocStyle` first-sentence findings (same wording rule).

### Group 3 — Formatters (Parallelizable: yes — one file per task)

- [x] Task 4. Fix the new formatters _(java)_
  - Group 3: 17 *Formatter files; hashCode() suppressed+justified, SPACE constant where " " literals existed (not in Energy/Force/Power/Pressure); 1346 tests pass; PMD UselessOverridingMethod 0; no new issues.
  - [x] Task 4.1. `EnergyFormatter`, `ForceFormatter`, `PowerFormatter`, `PressureFormatter`: annotate `hashCode()` with
    `@SuppressWarnings("PMD.UselessOverridingMethod")` and extend its Javadoc with the justification (kept because
    `equals()` is overridden).
  - [x] Task 4.2. Same four files: eliminate the repeated `" "` literals flagged by the baseline with a
    `private static final String SPACE = " ";` constant (respect `DeclarationOrder` and Javadoc rules).
- [x] Task 5. Fix the older formatters _(java)_
  - Group 3: 17 *Formatter files; hashCode() suppressed+justified, SPACE constant where " " literals existed (not in Energy/Force/Power/Pressure); 1346 tests pass; PMD UselessOverridingMethod 0; no new issues.
  - [x] Task 5.1. `Surface`, `MagneticFluxDensity`, `Speed`, `Volume`, `Weight`, `Frequency`, `Distance`,
    `AngularAcceleration`, `Acceleration`, `AngularSpeed`, `Angle`, `Temperature`, `Time` formatters: same
    `hashCode()` suppression and justification as Task 4.1.
  - [x] Task 5.2. Same files: replace repeated `" "` literals with a `SPACE` constant as in Task 4.2.

### Group 4 — Verification (Parallelizable: yes)

- [x] Task 6. Verify no regression _(java)_
  - [x] Task 6.1. Run the full test suite via `iru-gate-runner` (`mvn test`); all tests pass unchanged and coverage
    stays at or above its previous level.
  - [x] Task 6.2. Re-run Checkstyle, PMD and SpotBugs via `iru-gate-runner` and diff against Task 1: the four in-scope
    rules drop to zero, every other rule's count is unchanged, SpotBugs stays at 0 findings.
  - [x] Task 6.3. Confirm no `src/main/resources/.../build-info.properties` churn is staged (it is regenerated every
    build and must not be committed).

  - Result: 1346 tests pass (JaCoCo lines 3457/3466 covered, branches 1461/1499). Checkstyle 1215 (baseline 1221; JavadocStyle 6->0, LineLength 1132 = baseline after wrapping 10 lines lengthened by `+ SPACE` in Distance/MagneticFluxDensity/Temperature/Time/Volume formatters, all others unchanged); PMD 2 (UselessOverridingMethod 17->0, UnnecessaryModifier 1, UselessParentheses 1 unchanged); SpotBugs 3 = baseline (no new). build-info.properties modified in working tree only, nothing staged.