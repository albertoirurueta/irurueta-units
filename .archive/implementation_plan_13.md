# Implementation plan: Force, Pressure, Energy and Power measurements

## Task summary

Add four new measurement families — `Force`, `Pressure`, `Energy`, `Power` — to `com.irurueta.units`. Each ships the
library's standard quadruplet (`<X>Unit`, `<X>`, `<X>Converter`, `<X>Formatter`) plus unit tests mirroring `Speed*`,
and a row in the README "Supported measurement families" table. Density and Torque are explicitly out of scope
("secondary, not prioritized" in the issue).

Source: GitHub issue #13
Base branch: develop

Choices made on the user's behalf (challenge during review):
- **Base units** (converters normalize to them): Force → newton, Pressure → pascal, Energy → joule, Power → watt.
- **Factors** (exact/standard values, as package-private `static final double` constants):
  - Force: `NEWTONS_PER_KILONEWTON = 1000.0`, `NEWTONS_PER_POUND_FORCE = 4.4482216152605`,
    `NEWTONS_PER_DYNE = 1.0e-5`, `NEWTONS_PER_KILOGRAM_FORCE = 9.80665`.
  - Pressure: `PASCALS_PER_KILOPASCAL = 1000.0`, `PASCALS_PER_BAR = 100000.0`, `PASCALS_PER_ATMOSPHERE = 101325.0`,
    `PASCALS_PER_PSI = 6894.757293168`.
  - Energy: `JOULES_PER_KILOJOULE = 1000.0`, `JOULES_PER_CALORIE = 4.184` (thermochemical calorie),
    `JOULES_PER_KILOCALORIE = 4184.0`, `JOULES_PER_KILOWATT_HOUR = 3.6e6`,
    `JOULES_PER_BTU = 1055.05585262` (International Table BTU).
  - Power: `WATTS_PER_KILOWATT = 1000.0`, `WATTS_PER_MEGAWATT = 1.0e6`,
    `WATTS_PER_HORSEPOWER = 745.69987158227022` (mechanical horsepower, 550 ft·lbf/s).
- **Unit system classification**: imperial = `POUND_FORCE`; `PSI`; `BTU`; `HORSEPOWER`. Everything else is metric
  (including `DYNE`, `KILOGRAM_FORCE`, `BAR`, `ATMOSPHERE`, `CALORIE`, `KILOCALORIE`, `KILOWATT_HOUR`).
- **Formatter unit selection**: `formatAndConvert(value, unit, system)` picks a human-friendly unit per system by
  magnitude, following the `SpeedFormatter` approach (metric default: base unit, imperial default: the imperial unit).
  Exact thresholds follow the closest existing formatter; document them in Javadoc.
- Symbols: `N`, `kN`, `lbf`, `dyn`, `kgf`; `Pa`, `kPa`, `bar`, `atm`, `psi`; `J`, `kJ`, `cal`, `kcal`, `kWh`, `BTU`;
  `W`, `kW`, `MW`, `hp`.

Open clarifications: none

## Acceptance criteria coverage

| # | Criterion | Covered by tasks |
|---|---|---|
| 1 | "`ForceUnit.java` — unit enum (`NEWTON`, `KILONEWTON`, `POUND_FORCE`, `DYNE`, `KILOGRAM_FORCE`)" | Task 1.1 |
| 2 | "`Force.java`, `ForceConverter.java`, `ForceFormatter.java` following the Speed pattern" | Task 1.2, Task 1.3, Task 1.4 |
| 3 | "Force unit tests mirroring the coverage of an existing family" | Task 1.5 |
| 4 | "`PressureUnit.java` — unit enum (`PASCAL`, `KILOPASCAL`, `BAR`, `ATMOSPHERE`, `PSI`)" | Task 2.1 |
| 5 | "`Pressure.java`, `PressureConverter.java`, `PressureFormatter.java`" | Task 2.2, Task 2.3, Task 2.4 |
| 6 | "Pressure unit tests mirroring the coverage of an existing family" | Task 2.5 |
| 7 | "`EnergyUnit.java` — unit enum (`JOULE`, `KILOJOULE`, `CALORIE`, `KILOCALORIE`, `KILOWATT_HOUR`, `BTU`)" | Task 3.1 |
| 8 | "`Energy.java`, `EnergyConverter.java`, `EnergyFormatter.java`" | Task 3.2, Task 3.3, Task 3.4 |
| 9 | "Energy unit tests mirroring the coverage of an existing family" | Task 3.5 |
| 10 | "`PowerUnit.java` — unit enum (`WATT`, `KILOWATT`, `MEGAWATT`, `HORSEPOWER`)" | Task 4.1 |
| 11 | "`Power.java`, `PowerConverter.java`, `PowerFormatter.java`" | Task 4.2, Task 4.3, Task 4.4 |
| 12 | "Power unit tests mirroring the coverage of an existing family" | Task 4.5 |
| 13 | "Row added to the 'Supported measurement families' table in `README.md`" (x4) | Task 5.1 |
| 14 | derived: a family is only done when all four classes exist (no partial family) | Task 1–4 (each family complete), Task 6 |
| 15 | derived: build passes with Checkstyle clean and >= 80% coverage on new classes | Task 6 |

## Current code state

- Java 21 library, Maven (no wrapper), single module, package `com.irurueta.units` in `src/main/java`; tests in
  `src/test/java/com/irurueta/units`. Checkstyle (`checkstyle.xml`) enforces Javadoc on everything, `MagicNumber`
  (factors must be named `static final` constants), `HideUtilityClassConstructor`, import order, indentation and
  declaration order. Every source file carries the Apache 2.0 header (`Copyright (C) <year> Alberto Irurueta Carro`).
- Reference family is Speed:
  - `SpeedUnit.java` — enum + `getUnitSystem`, `getMetricUnits`, `getImperialUnits`, `isMetric`, `isImperial`
    (switch expression; `IllegalArgumentException` on null).
  - `Speed.java` (extends `Measurement<SpeedUnit>`) — constructors, getters/setters, `equals`/`hashCode`,
    tolerance `equals(other, tolerance)`, and unit-conversion helpers.
  - `SpeedConverter.java` — private constructor, `convert(Speed, Speed)`, `convertAndReturnNew`,
    `convert(Speed, SpeedUnit)`, `convert(Number|double, in, out)`, and per-pair helper methods.
  - `SpeedFormatter.java` (extends `MeasureFormatter<Speed, SpeedUnit>`) — `getUnitSymbol`, `findUnit`,
    `getUnitSystem`, `parse`, `formatAndConvert(value, unit, system)`.
  - Tests: `SpeedTest`, `SpeedConverterTest`, `SpeedFormatterTest`, `SpeedUnitTest` (roughly 1,600 lines total).
- Shared: `Measurement.java`, `MeasureFormatter.java`, `UnitSystem`, `UnitLocale`, `UnitsException`,
  `UnknownUnitException`. No change needed there.
- Docs (Antora, `docs/modules/ROOT/pages`): `index.adoc` (line 40 lists families), `supported-units.adoc`
  (per-family table), `api-conventions.adoc`, `measurements.adoc`, `formatting.adoc`, `reference.adoc`. `README.md`
  line ~178 holds the families table. `CHANGELOG.md` has an `[Unreleased]` section.
- Earlier attempts exist on remote branches `feature/13_v0` and `feature/13_v1`; they are not to be reused.

## Implementation steps

### Group 1 — The four families (Parallelizable: yes — each family touches only its own new files)

Mirror `Speed*` for every file; tag all tasks `java`.

- [x] Task 1. Force family _(java)_ — implemented, tests added — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 1.1. Create `ForceUnit.java` with `NEWTON, KILONEWTON, POUND_FORCE, DYNE, KILOGRAM_FORCE` and the standard helpers (imperial: `POUND_FORCE`) — done (ForceUnit.java) — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 1.2. Create `Force.java` extending `Measurement<ForceUnit>` — done (Force.java) — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 1.3. Create `ForceConverter.java` (newton base, factors above, all `convert` overloads and per-pair helpers) — done (ForceConverter.java) — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 1.4. Create `ForceFormatter.java` (symbols above, `findUnit`, `getUnitSystem`, `parse`, `formatAndConvert`) — done (ForceFormatter.java) — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 1.5. Create `ForceUnitTest`, `ForceTest`, `ForceConverterTest`, `ForceFormatterTest` mirroring the Speed tests (known values, round trips, null/invalid args, locales, parse failures) — done (ForceUnitTest, ForceTest, ForceConverterTest, ForceFormatterTest) — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
- [x] Task 2. Pressure family _(java)_ — implemented, tests added — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 2.1. Create `PressureUnit.java` with `PASCAL, KILOPASCAL, BAR, ATMOSPHERE, PSI` (imperial: `PSI`) — implemented, tests added — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 2.2. Create `Pressure.java` — implemented, tests added — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 2.3. Create `PressureConverter.java` (pascal base) — implemented, tests added — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 2.4. Create `PressureFormatter.java` — implemented, tests added — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 2.5. Create `PressureUnitTest`, `PressureTest`, `PressureConverterTest`, `PressureFormatterTest` — implemented, tests added — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
- [x] Task 3. Energy family _(java)_
  - [x] Task 3.1. Create `EnergyUnit.java` with `JOULE, KILOJOULE, CALORIE, KILOCALORIE, KILOWATT_HOUR, BTU` (imperial: `BTU`)
  - [x] Task 3.2. Create `Energy.java`
  - [x] Task 3.3. Create `EnergyConverter.java` (joule base)
  - [x] Task 3.4. Create `EnergyFormatter.java`
  - [x] Task 3.5. Create `EnergyUnitTest`, `EnergyTest`, `EnergyConverterTest`, `EnergyFormatterTest` — implemented in Energy*.java and Energy*Test.java — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
- [x] Task 4. Power family _(java)_ — implemented, tests added — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 4.1. Create `PowerUnit.java` with `WATT, KILOWATT, MEGAWATT, HORSEPOWER` (imperial: `HORSEPOWER`) — implemented — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 4.2. Create `Power.java` — implemented — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 4.3. Create `PowerConverter.java` (watt base) — implemented — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 4.4. Create `PowerFormatter.java` — implemented — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.
  - [x] Task 4.5. Create `PowerUnitTest`, `PowerTest`, `PowerConverterTest`, `PowerFormatterTest` — implemented — validated: 100% line coverage, no new issues beyond Speed-pattern-inherent Checkstyle LineLength/FinalClass and PMD UselessOverridingMethod.

### Group 2 — Docs and validation (Parallelizable: no — depends on Group 1's classes and edits shared files)

- [x] Task 5. Documentation _(untagged)_
  - [x] Task 5.1. Add four rows to the "Supported measurement families" table in `README.md` — done (README.md)
  - [x] Task 5.2. Run `/iru-update-docs` to update `docs/modules/ROOT/pages` (`index.adoc` family list, `supported-units.adoc` tables, others as needed) — done manually (index.adoc, supported-units.adoc); /iru-update-docs skill not invoked, docs build not run
  - [x] Task 5.3. Add an `Added` entry under `[Unreleased]` in `CHANGELOG.md` — done (CHANGELOG.md)
- [x] Task 6. Final validation _(java)_
  - [x] Task 6.1. Delegate `mvn test` to the `iru-gate-runner` agent (full suite, new tests included) and confirm all pass — 1340 tests, 0 failures
  - [x] Task 6.2. Run `mvn checkstyle:check`, and confirm coverage of new classes is at least 80% — 100% line coverage (PowerFormatter branch 93.8%); Checkstyle: only LineLength (181) and FinalClass (4 Converters), both Speed-pattern-inherent
  - [x] Task 6.3. Confirm all four families have all four classes plus tests (no partial family) — verified 8 files per family
