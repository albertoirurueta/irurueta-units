# Implementation Plan — Issue #13: Add support for Force, Pressure, Energy and Power measurements

## Task summary

Add four new measurement families to the library — `Force`, `Pressure`, `Energy`, `Power` — each shipping as
the same complete 4-class set every existing family uses: `<X>Unit` (enum), `<X>` (measurement class),
`<X>Converter` (conversion logic), `<X>Formatter` (locale-aware formatting), plus mirrored unit tests for all
four, and a row in README.md's "Supported measurement families" table. Per the issue's definition of done, a
family is not complete unless all four classes + tests + README row exist — no partial family (e.g. converter
without formatter) is acceptable.

**Language/framework:** java (Maven)

**Design decisions made (not specified by the issue, chosen from codebase convention):**

- **Base/reference unit per family** (the unit every conversion routes through internally, following
  `SpeedConverter`'s meters-per-second pattern): `Force` → `NEWTON`, `Pressure` → `PASCAL`, `Energy` → `JOULE`,
  `Power` → `WATT` — the SI unit in each family, matching how every existing converter (`DistanceConverter` →
  meter, `WeightConverter` → gram, `SpeedConverter` → m/s) uses the SI unit as its base.
- **Conversion factors** (standard, well-established physical constants; each becomes a named
  `static final double` field in its `*Converter` class per Checkstyle's `MagicNumber` rule, mirroring
  `SpeedConverter`'s `METERS_PER_KILOMETER`, `METERS_PER_FOOT`, etc.):
  - Force → base `NEWTON`: `KILONEWTON` = 1000 N, `POUND_FORCE` = 4.4482216152605 N, `DYNE` = 0.00001 N,
    `KILOGRAM_FORCE` = 9.80665 N.
  - Pressure → base `PASCAL`: `KILOPASCAL` = 1000 Pa, `BAR` = 100000 Pa, `ATMOSPHERE` = 101325 Pa,
    `PSI` = 6894.757293168 Pa.
  - Energy → base `JOULE`: `KILOJOULE` = 1000 J, `CALORIE` = 4.184 J (thermochemical calorie),
    `KILOCALORIE` = 4184 J, `KILOWATT_HOUR` = 3600000 J, `BTU` = 1055.05585262 J (International Table BTU).
  - Power → base `WATT`: `KILOWATT` = 1000 W, `MEGAWATT` = 1000000 W, `HORSEPOWER` = 745.699872 W (mechanical/imperial
    horsepower).
- **Metric vs. imperial classification** (for each `<X>Unit.getUnitSystem()`), following the same one-imperial-
  unit-per-family shape already used by `AccelerationUnit` (`FEET_PER_SQUARED_SECOND` imperial, rest metric):
  - Force: `POUND_FORCE` → `IMPERIAL`; `NEWTON`, `KILONEWTON`, `DYNE`, `KILOGRAM_FORCE` → `METRIC` (default).
  - Pressure: `PSI` → `IMPERIAL`; `PASCAL`, `KILOPASCAL`, `BAR`, `ATMOSPHERE` → `METRIC` (default).
  - Energy: `BTU` → `IMPERIAL`; `JOULE`, `KILOJOULE`, `CALORIE`, `KILOCALORIE`, `KILOWATT_HOUR` → `METRIC` (default).
  - Power: `HORSEPOWER` → `IMPERIAL`; `WATT`, `KILOWATT`, `MEGAWATT` → `METRIC` (default).
- **Formatter symbols**: `N`, `kN`, `lbf`, `dyn`, `kgf` (Force); `Pa`, `kPa`, `bar`, `atm`, `psi` (Pressure);
  `J`, `kJ`, `cal`, `kcal`, `kWh`, `BTU` (Energy); `W`, `kW`, `MW`, `hp` (Power) — standard scientific symbols,
  matching the mixed-case style already used (e.g. `SpeedFormatter`'s `"Km/h"`).
- **`formatAndConvertMetric`/`formatAndConvertImperial` strategy**: since none of these four families' suggested
  unit sets form a clean power-of-ten magnitude ladder the way `Weight`'s gram/kilogram/tonne chain does (e.g.
  `DYNE`, `KILOGRAM_FORCE`, `ATMOSPHERE`, `CALORIE`, `HORSEPOWER` are not decimal multiples of their base unit),
  each new formatter should follow `AccelerationFormatter`'s simpler approach instead: always format in one
  fixed canonical unit per system (e.g. `PressureFormatter.formatAndConvertMetric` always converts to and
  formats as `PASCAL`; `formatAndConvertImperial` always converts to and formats as `PSI`) rather than
  attempting magnitude-based tier selection.
- Every measurement class in this codebase (`Frequency`, `Temperature`, `Weight`, `Distance`, `Acceleration`,
  and `Speed` alike) carries the same `add`/`subtract` convenience API (~22 overloads: static double/Number,
  in-place `Speed`-style, `addAndReturnNew`, instance mutating/non-mutating variants) — confirmed this is the
  universal baseline, not something bespoke to `Speed`. The four new measurement classes must include the same
  API shape.

## Current code state

- All production code lives in `src/main/java/com/irurueta/units/`. 13 measurement families exist today
  (`Distance`, `Surface`, `Volume`, `Speed`, `Acceleration`, `Angle`, `AngularSpeed`, `AngularAcceleration`,
  `Time`, `Frequency`, `Temperature`, `Weight`, `MagneticFluxDensity`) — none of `Force`, `Pressure`, `Energy`,
  `Power` exist yet.
- Every family follows an identical 4-class shape (studied in depth via `Speed.java` / `SpeedUnit.java` /
  `SpeedConverter.java` / `SpeedFormatter.java`, cross-checked against `AccelerationFormatter.java` and
  `WeightFormatter.java` for formatting-strategy variants):
  - `<X>Unit` — enum with a Javadoc comment per constant; static `getUnitSystem(unit)` (switch expression,
    `IllegalArgumentException` on null), `getMetricUnits()`/`getImperialUnits()` (hard-coded arrays),
    `isMetric(unit)`/`isImperial(unit)` (delegate to `getUnitSystem`).
  - `<X>` — extends `Measurement<XUnit>` (`Measurement.java`); public `(Number value, XUnit unit)` constructor
    (throws `IllegalArgumentException` on null via the parent), package-private no-arg constructor; overrides
    `equals(Measurement<XUnit> other, double tolerance)` to add cross-unit tolerant comparison via
    `<X>Converter.convert(...)`; full `add`/`subtract`/`addAndReturnNew`/`subtractAndReturnNew` overload family.
  - `<X>Converter` — private no-arg constructor (`HideUtilityClassConstructor`); package-private
    (not `private`) `static final double` conversion-factor constants at the top with one-line Javadoc each
    (package-private so the matching `<X>Formatter` can read them directly, as `SpeedFormatter` does with
    `SpeedConverter.METERS_PER_KILOMETER`); overload set: `convert(double, XUnit, XUnit)` (the actual math, two
    chained switch expressions through the base unit), `convert(Number, XUnit, XUnit)`,
    `convert(X input, X output)`, `convert(X input, XUnit outputUnit)`, `convert(X input, XUnit outputUnit, X result)`,
    `convertAndReturnNew(X input, XUnit outputUnit)`; one named to-base/from-base method pair per non-base unit
    (e.g. `dyneToNewton`/`newtonToDyne`), each referencing only the named constants, never raw literals.
  - `<X>Formatter` — extends `MeasureFormatter<X, XUnit>` (`MeasureFormatter.java`); must implement exactly the
    5 abstract methods: `formatAndConvert(Number, XUnit, UnitSystem)`, `getUnitSystem(String)`,
    `parse(String)`, `findUnit(String)`, `getUnitSymbol(XUnit)`; unit-symbol `String` constants; 3 constructors
    (no-arg, `Locale`, copy); `equals`/`hashCode` overrides delegating to `super`; `findUnit` checks
    longest/most-specific symbols first to avoid substring false-matches.
- README.md line 171 has the "Supported measurement families" table (2 columns:
  `| Measurement | Examples of units |`), 11 rows, ending with `MagneticFluxDensity` right before the
  `## 🤝 Contributing` section at line 187.
- Checkstyle (`checkstyle.xml`) enables `MagicNumber`, `JavadocVariable`, `JavadocType`, `JavadocMethod`,
  `HideUtilityClassConstructor`, `ConstantName`, `DeclarationOrder` — all satisfied by mirroring the existing
  converter/formatter constant-declaration pattern exactly.
- Tests mirror 1:1 under `src/test/java/com/irurueta/units/`: `<X>UnitTest`, `<X>Test`, `<X>ConverterTest`,
  `<X>FormatterTest`. `SpeedConverterTest` re-declares conversion constants locally to test independently of the
  converter's own constants, and includes an exhaustive N×N matrix test (`testConvertDouble`) across every unit
  pairing. `SpeedFormatterTest`/`WeightFormatterTest` test formatting/parsing per-unit in both a metric locale
  (`es`,`ES`) and an imperial locale (`en`,`US`).
- License header convention (Apache 2.0, `Copyright (C) <year> Alberto Irurueta Carro (alberto@irurueta.com)`)
  must be present on every new file — use `2026` as the year for new files.

## Implementation steps

1. [x] **Add the `Force` measurement family** _(java)_ — `ForceUnit`, `Force`, `ForceConverter`, `ForceFormatter`
   plus mirrored tests (80 tests, 100% line/branch coverage), README row added. No new Checkstyle/PMD/SpotBugs
   issues beyond pre-existing repo-wide patterns already present in sibling classes.
   - [x] Create `src/main/java/com/irurueta/units/ForceUnit.java`: enum constants `NEWTON`, `KILONEWTON`,
     `POUND_FORCE`, `DYNE`, `KILOGRAM_FORCE`; `getUnitSystem`, `getMetricUnits`, `getImperialUnits`, `isMetric`,
     `isImperial` statics mirroring `AccelerationUnit`/`SpeedUnit`.
   - [x] Create `src/main/java/com/irurueta/units/Force.java`: extends `Measurement<ForceUnit>`; constructors;
     tolerance-based `equals`; full add/subtract API, mirroring `Speed.java`'s shape.
   - [x] Create `src/main/java/com/irurueta/units/ForceConverter.java`: base unit `NEWTON`; constants
     `KILONEWTONS_PER_NEWTON`/`NEWTONS_PER_KILONEWTON`-style naming consistent with existing converters (pick
     the direction that matches how `SpeedConverter` names things, e.g. `NEWTONS_PER_POUND_FORCE`,
     `NEWTONS_PER_DYNE`, `NEWTONS_PER_KILOGRAM_FORCE`, `NEWTONS_PER_KILONEWTON`) with the values from the Task
     summary; full convert overload set; to/from-base conversion method pairs (e.g. `poundForceToNewton`/
     `newtonToPoundForce`, `dyneToNewton`/`newtonToDyne`, `kilogramForceToNewton`/`newtonToKilogramForce`,
     `kilonewtonToNewton`/`newtonToKilonewton`).
   - [x] Create `src/main/java/com/irurueta/units/ForceFormatter.java`: extends
     `MeasureFormatter<Force, ForceUnit>`; symbols `N`, `kN`, `lbf`, `dyn`, `kgf`;
     `formatAndConvertMetric` always formats as `NEWTON`; `formatAndConvertImperial` always formats as
     `POUND_FORCE`, mirroring `AccelerationFormatter`.
   - [x] Create `src/test/java/com/irurueta/units/ForceUnitTest.java` mirroring `SpeedUnitTest`/
     `AccelerationUnitTest` (per-constant `getUnitSystem`, `getMetricUnits`/`getImperialUnits` completeness
     check, `isMetric`/`isImperial`, null → `IllegalArgumentException`).
   - [x] Create `src/test/java/com/irurueta/units/ForceTest.java` mirroring `SpeedTest` (constructor, equals/
     hashCode/tolerance, add/subtract family, serialization round-trip).
   - [x] Create `src/test/java/com/irurueta/units/ForceConverterTest.java` mirroring `SpeedConverterTest`
     (locally re-declared constants, per-unit-pair to/from-base tests, exhaustive N×N `testConvertDouble`
     matrix across all 5 units, Force-level overload tests).
   - [x] Create `src/test/java/com/irurueta/units/ForceFormatterTest.java` mirroring `AccelerationFormatterTest`
     (constructor/clone/equals/hashCode, format overloads per unit in a metric locale, `formatAndConvert`
     metric/imperial, `getUnitSymbol`, `findUnit`, `parse`, `isValidUnit`/`isValidMeasurement`/`isMetricUnit`/
     `isImperialUnit`/`getUnitSystem(String)`).
   - [x] Add a `Force` row to the README.md "Supported measurement families" table (after `MagneticFluxDensity`,
     before `## 🤝 Contributing`): `| \`Force\` | \`NEWTON\`, \`KILONEWTON\`, \`POUND_FORCE\`, \`DYNE\`,
     \`KILOGRAM_FORCE\` |`.
   - [x] Run the tests for this family via `gate-runner`:
     `Agent({description: "Run Force family tests", subagent_type: "gate-runner", prompt: "Invoke a Maven test
     run scoped to Force*Test classes (mvn test -Dtest=ForceUnitTest,ForceTest,ForceConverterTest,
     ForceFormatterTest) in /Users/albertoirurueta/repositories/common/irurueta-units and report pass/fail
     summary plus any failures."})`.

2. [x] **Add the `Pressure` measurement family** _(java)_ — `PressureUnit`, `Pressure`, `PressureConverter`,
   `PressureFormatter` plus mirrored tests (80 tests, 100% line/branch coverage), README row added. No new
   Checkstyle/PMD/SpotBugs issues beyond pre-existing repo-wide patterns already present in the Force family.
   - [x] Create `src/main/java/com/irurueta/units/PressureUnit.java`: enum constants `PASCAL`, `KILOPASCAL`,
     `BAR`, `ATMOSPHERE`, `PSI`; same statics shape as Task 1.
   - [x] Create `src/main/java/com/irurueta/units/Pressure.java`: extends `Measurement<PressureUnit>`; same
     shape as `Force.java`.
   - [x] Create `src/main/java/com/irurueta/units/PressureConverter.java`: base unit `PASCAL`; constants
     `PASCALS_PER_KILOPASCAL` = 1000, `PASCALS_PER_BAR` = 100000, `PASCALS_PER_ATMOSPHERE` = 101325,
     `PASCALS_PER_PSI` = 6894.757293168; to/from-base method pairs per unit.
   - [x] Create `src/main/java/com/irurueta/units/PressureFormatter.java`: extends
     `MeasureFormatter<Pressure, PressureUnit>`; symbols `Pa`, `kPa`, `bar`, `atm`, `psi`;
     `formatAndConvertMetric` always formats as `PASCAL`; `formatAndConvertImperial` always formats as `PSI`.
   - [x] Create `src/test/java/com/irurueta/units/PressureUnitTest.java` mirroring Task 1's unit test.
   - [x] Create `src/test/java/com/irurueta/units/PressureTest.java` mirroring Task 1's measurement test.
   - [x] Create `src/test/java/com/irurueta/units/PressureConverterTest.java` mirroring Task 1's converter test
     (N×N matrix across all 5 units).
   - [x] Create `src/test/java/com/irurueta/units/PressureFormatterTest.java` mirroring Task 1's formatter test.
   - [x] Add a `Pressure` row to the README.md table: `| \`Pressure\` | \`PASCAL\`, \`KILOPASCAL\`, \`BAR\`,
     \`ATMOSPHERE\`, \`PSI\` |`.
   - [x] Run the tests for this family via `gate-runner`:
     `Agent({description: "Run Pressure family tests", subagent_type: "gate-runner", prompt: "Invoke a Maven
     test run scoped to Pressure*Test classes (mvn test -Dtest=PressureUnitTest,PressureTest,
     PressureConverterTest,PressureFormatterTest) in /Users/albertoirurueta/repositories/common/irurueta-units
     and report pass/fail summary plus any failures."})`.

3. [x] **Add the `Energy` measurement family** _(java)_ — `EnergyUnit`, `Energy`, `EnergyConverter`,
   `EnergyFormatter` plus mirrored tests (81 tests, 100% line/branch coverage), README row added,
   `package-info.java` summary extended. No new Checkstyle/PMD/SpotBugs issues beyond pre-existing repo-wide
   patterns already present in the Force/Pressure families.
   - [x] Create `src/main/java/com/irurueta/units/EnergyUnit.java`: enum constants `JOULE`, `KILOJOULE`,
     `CALORIE`, `KILOCALORIE`, `KILOWATT_HOUR`, `BTU`; same statics shape as Task 1.
   - [x] Create `src/main/java/com/irurueta/units/Energy.java`: extends `Measurement<EnergyUnit>`; same shape
     as `Force.java`.
   - [x] Create `src/main/java/com/irurueta/units/EnergyConverter.java`: base unit `JOULE`; constants
     `JOULES_PER_KILOJOULE` = 1000, `JOULES_PER_CALORIE` = 4.184, `JOULES_PER_KILOCALORIE` = 4184,
     `JOULES_PER_KILOWATT_HOUR` = 3600000, `JOULES_PER_BTU` = 1055.05585262; to/from-base method pairs per unit.
   - [x] Create `src/main/java/com/irurueta/units/EnergyFormatter.java`: extends
     `MeasureFormatter<Energy, EnergyUnit>`; symbols `J`, `kJ`, `cal`, `kcal`, `kWh`, `BTU`;
     `formatAndConvertMetric` always formats as `JOULE`; `formatAndConvertImperial` always formats as `BTU`.
   - [x] Create `src/test/java/com/irurueta/units/EnergyUnitTest.java` mirroring Task 1's unit test.
   - [x] Create `src/test/java/com/irurueta/units/EnergyTest.java` mirroring Task 1's measurement test.
   - [x] Create `src/test/java/com/irurueta/units/EnergyConverterTest.java` mirroring Task 1's converter test
     (N×N matrix across all 6 units).
   - [x] Create `src/test/java/com/irurueta/units/EnergyFormatterTest.java` mirroring Task 1's formatter test.
   - [x] Add an `Energy` row to the README.md table: `| \`Energy\` | \`JOULE\`, \`KILOJOULE\`, \`CALORIE\`,
     \`KILOCALORIE\`, \`KILOWATT_HOUR\`, \`BTU\` |`.
   - [x] Run the tests for this family via `gate-runner`:
     `Agent({description: "Run Energy family tests", subagent_type: "gate-runner", prompt: "Invoke a Maven test
     run scoped to Energy*Test classes (mvn test -Dtest=EnergyUnitTest,EnergyTest,EnergyConverterTest,
     EnergyFormatterTest) in /Users/albertoirurueta/repositories/common/irurueta-units and report pass/fail
     summary plus any failures."})`.

4. [x] **Add the `Power` measurement family** _(java)_ — `PowerUnit`, `Power`, `PowerConverter`,
   `PowerFormatter` plus mirrored tests (79 tests, 100% line coverage on all 4 classes verified independently
   via jacoco), README row added. No new SpotBugs issues; +1 PMD `UselessOverridingMethod`
   (`PowerFormatter.hashCode`) and additional Checkstyle `LineLength`/`MagicNumber`-family findings, both being
   exact instances of the same pre-existing repo-wide pattern already present in the Force/Pressure/Energy
   formatters/converters mirrored by this task.
   - [x] Create `src/main/java/com/irurueta/units/PowerUnit.java`: enum constants `WATT`, `KILOWATT`,
     `MEGAWATT`, `HORSEPOWER`; same statics shape as Task 1.
   - [x] Create `src/main/java/com/irurueta/units/Power.java`: extends `Measurement<PowerUnit>`; same shape as
     `Force.java`.
   - [x] Create `src/main/java/com/irurueta/units/PowerConverter.java`: base unit `WATT`; constants
     `WATTS_PER_KILOWATT` = 1000, `WATTS_PER_MEGAWATT` = 1000000, `WATTS_PER_HORSEPOWER` = 745.699872; to/from-
     base method pairs per unit.
   - [x] Create `src/main/java/com/irurueta/units/PowerFormatter.java`: extends
     `MeasureFormatter<Power, PowerUnit>`; symbols `W`, `kW`, `MW`, `hp`; `formatAndConvertMetric` always
     formats as `WATT`; `formatAndConvertImperial` always formats as `HORSEPOWER`.
   - [x] Create `src/test/java/com/irurueta/units/PowerUnitTest.java` mirroring Task 1's unit test.
   - [x] Create `src/test/java/com/irurueta/units/PowerTest.java` mirroring Task 1's measurement test.
   - [x] Create `src/test/java/com/irurueta/units/PowerConverterTest.java` mirroring Task 1's converter test
     (N×N matrix across all 4 units).
   - [x] Create `src/test/java/com/irurueta/units/PowerFormatterTest.java` mirroring Task 1's formatter test.
   - [x] Add a `Power` row to the README.md table: `| \`Power\` | \`WATT\`, \`KILOWATT\`, \`MEGAWATT\`,
     \`HORSEPOWER\` |`.
   - [x] Run the tests for this family via `gate-runner`:
     `Agent({description: "Run Power family tests", subagent_type: "gate-runner", prompt: "Invoke a Maven test
     run scoped to Power*Test classes (mvn test -Dtest=PowerUnitTest,PowerTest,PowerConverterTest,
     PowerFormatterTest) in /Users/albertoirurueta/repositories/common/irurueta-units and report pass/fail
     summary plus any failures."})`.

5. [x] **Full-suite verification and quality gates** _(java)_ — full suite green (1373/1373 tests). Checkstyle
   1218 issues (up from a 1032 pre-existing baseline) and PMD 19 issues (up from 15) — the entire delta is
   attributable to the 4 new families mirroring the same repo-wide LineLength/MagicNumber/UselessOverridingMethod
   patterns already present in every sibling class, as each per-family task noted; SpotBugs unchanged at 3
   (identical pre-existing findings, none in new code). README confirmed with all 4 new rows, no formatting
   regressions. Per the `code` skill's Step 7 gate, this net increase is flagged to the user for a decision
   (fix now / file follow-up issues / accept as-is) rather than silently proceeding — see the final summary.
   - [x] Run the complete test suite via `gate-runner`: `Agent({description: "Run full test suite",
     subagent_type: "gate-runner", prompt: "Invoke `mvn test` in
     /Users/albertoirurueta/repositories/common/irurueta-units and report pass/fail summary, total test count,
     and any failures."})` — confirms no regressions in the other 13 families. Result: 1373 tests, 0
     failures/errors/skipped.
   - [x] Run Checkstyle via `gate-runner`: `Agent({description: "Run checkstyle", subagent_type: "gate-runner",
     prompt: "Invoke `mvn checkstyle:check` in /Users/albertoirurueta/repositories/common/irurueta-units and
     report violations, if any, with file/line."})`. Result: 1218 violations across 58 files (baseline: 1032).
   - [x] Run SpotBugs via `gate-runner`: `Agent({description: "Run spotbugs", subagent_type: "gate-runner",
     prompt: "Invoke `mvn com.github.spotbugs:spotbugs-maven-plugin:check` in
     /Users/albertoirurueta/repositories/common/irurueta-units and report violations, if any."})`. Result: 3
     violations (MeasureFormatter x2, Measurement x1) — identical to baseline, no new findings.
   - [x] Confirm the README.md table now has all four new rows (`Force`, `Pressure`, `Energy`, `Power`) in
     addition to the original 11, with no formatting regressions to the surrounding sections. Confirmed: lines
     186-189 of README.md.
