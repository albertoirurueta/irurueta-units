# Implementation Plan — Add Force, Pressure, Energy and Power measurement families (Issue #13)

## Task summary

Issue #13 asks for four new measurement families — `Force`, `Pressure`, `Energy`, `Power` — each shipping the
same 4-class pattern every existing family in `com.irurueta.units` follows (`<X>Unit`, `<X>`, `<X>Converter`,
`<X>Formatter`), with unit tests mirroring an existing family's coverage, plus a row in README.md's "Supported
measurement families" table per family. A family is not done until all four classes + tests exist — no partial
implementations.

The issue explicitly leaves "exact conversion factors/base units" to be finalized during implementation. Since
nothing here is architecturally ambiguous (the 4-class pattern, base-unit-mediated conversion, and metric/imperial
split are all established conventions), the following choices are made now rather than deferred, so downstream
tasks have no guesswork left:

- **Base (reference) unit per family** — the plain, non-prefixed SI unit, same convention as `METERS_PER_SECOND`
  for `Speed` / `TESLA` for `MagneticFluxDensity`:
  - Force → `NEWTON`
  - Pressure → `PASCAL`
  - Energy → `JOULE`
  - Power → `WATT`
- **Conversion factors** (all `<BASE_PLURAL>_PER_<OTHER>` constants, `static final double`, package-visible, no
  `private`, defined in each `<X>Converter`):
  - Force (base `NEWTON`): `NEWTONS_PER_KILONEWTON = 1000.0`, `NEWTONS_PER_DYNE = 1e-5`,
    `NEWTONS_PER_KILOGRAM_FORCE = 9.80665`, `NEWTONS_PER_POUND_FORCE = 4.4482216152605`
  - Pressure (base `PASCAL`): `PASCALS_PER_KILOPASCAL = 1000.0`, `PASCALS_PER_BAR = 100000.0`,
    `PASCALS_PER_ATMOSPHERE = 101325.0`, `PASCALS_PER_PSI = 6894.757293168361`
  - Energy (base `JOULE`): `JOULES_PER_KILOJOULE = 1000.0`, `JOULES_PER_CALORIE = 4.184`,
    `JOULES_PER_KILOCALORIE = 4184.0`, `JOULES_PER_KILOWATT_HOUR = 3600000.0`, `JOULES_PER_BTU = 1055.05585262`
    (BTU = International Table BTU)
  - Power (base `WATT`): `WATTS_PER_KILOWATT = 1000.0`, `WATTS_PER_MEGAWATT = 1000000.0`,
    `WATTS_PER_HORSEPOWER = 745.6998715822702` (mechanical horsepower)
- **Metric/imperial split**, following `SpeedUnit.getUnitSystem`'s switch-expression pattern (imperial units listed
  explicitly, `default -> METRIC`):
  - Force: imperial = `POUND_FORCE`; metric = `NEWTON`, `KILONEWTON`, `DYNE`, `KILOGRAM_FORCE`
  - Pressure: imperial = `PSI`; metric = `PASCAL`, `KILOPASCAL`, `BAR`, `ATMOSPHERE`
  - Energy: imperial = `BTU`; metric = `JOULE`, `KILOJOULE`, `CALORIE`, `KILOCALORIE`, `KILOWATT_HOUR`
  - Power: imperial = `HORSEPOWER`; metric = `WATT`, `KILOWATT`, `MEGAWATT`
- **Formatter symbol constants** (`public static final String`, also used as `findUnit` match tokens):
  - Force: `NEWTON="N"`, `KILONEWTON="kN"`, `POUND_FORCE="lbf"`, `DYNE="dyn"`, `KILOGRAM_FORCE="kgf"`
  - Pressure: `PASCAL="Pa"`, `KILOPASCAL="kPa"`, `BAR="bar"`, `ATMOSPHERE="atm"`, `PSI="psi"`
  - Energy: `JOULE="J"`, `KILOJOULE="kJ"`, `CALORIE="cal"`, `KILOCALORIE="kcal"`, `KILOWATT_HOUR="kWh"`, `BTU="BTU"`
  - Power: `WATT="W"`, `KILOWATT="kW"`, `MEGAWATT="MW"`, `HORSEPOWER="hp"`
- **`findUnit` check ordering** (longer/suffix-conflicting symbols before the shorter symbols they contain, same
  rationale as `SpeedFormatter`/`MagneticFluxDensityFormatter`):
  - Force: check `KILONEWTON` ("kN") before `NEWTON` ("N"); `POUND_FORCE`, `DYNE`, `KILOGRAM_FORCE` have no
    conflicts.
  - Pressure: check `KILOPASCAL` ("kPa") before `PASCAL` ("Pa"); `BAR`, `ATMOSPHERE`, `PSI` have no conflicts.
  - Energy: check `KILOJOULE` ("kJ") before `JOULE` ("J"); check `KILOCALORIE` ("kcal") before `CALORIE` ("cal");
    `KILOWATT_HOUR`, `BTU` have no conflicts.
  - Power: check `KILOWATT` ("kW") and `MEGAWATT` ("MW") before `WATT` ("W"); `HORSEPOWER` has no conflict.
- **`formatAndConvertMetric`/`formatAndConvertImperial` design**: mirror `SpeedFormatter`'s ascending-magnitude
  pattern — order candidate units from smallest to largest base-unit-equivalent, and for each non-final candidate
  test whether the value converted to that unit has an absolute magnitude smaller than the conversion factor to
  the next larger unit in the sequence; the final (largest) candidate is always used unconditionally as the
  fallthrough. Concretely:
  - Force metric: order `DYNE → NEWTON → KILOGRAM_FORCE → KILONEWTON`. Force imperial: only `POUND_FORCE` exists,
    so `formatAndConvertImperial` just converts directly to it (no threshold chain needed — same as if a family
    had a single imperial unit).
  - Pressure metric: order `PASCAL → BAR → ATMOSPHERE → KILOPASCAL`... instead use ascending numeric factor order
    `PASCAL (1) → KILOPASCAL (1e3) → BAR (1e5) → ATMOSPHERE (1.01325e5)`. Pressure imperial: only `PSI` — direct
    conversion, no chain.
  - Energy metric: order `JOULE (1) → CALORIE (4.184) → KILOJOULE (1e3) → KILOCALORIE (4184) → KILOWATT_HOUR
    (3.6e6)`. Energy imperial: only `BTU` — direct conversion, no chain.
  - Power metric: order `WATT (1) → KILOWATT (1e3) → MEGAWATT (1e6)`. Power imperial: only `HORSEPOWER` — direct
    conversion, no chain.

## Current code state

- Every existing measurement family lives in `src/main/java/com/irurueta/units/` as a `<X>Unit`/`<X>`/
  `<X>Converter`/`<X>Formatter` quadruplet, with 1:1 test mirrors in `src/test/java/com/irurueta/units/`. The
  license header is Apache 2.0, `Copyright (C) <year> Alberto Irurueta Carro (alberto@irurueta.com)` — new files
  use `2026`.
- `SpeedUnit`/`Speed`/`SpeedConverter`/`SpeedFormatter` is the template for a derived unit with both metric and
  imperial variants (exactly the shape Force/Pressure/Energy/Power need, since each has exactly one imperial unit
  among several metric ones):
  - `SpeedUnit` exposes `getUnitSystem(unit)`, `getMetricUnits()`, `getImperialUnits()`, `isMetric(unit)`,
    `isImperial(unit)` — a switch expression over the imperial constants, `default -> METRIC`.
  - `Speed extends Measurement<SpeedUnit>`: public `(Number, SpeedUnit)` constructor, package-private no-arg
    constructor, `equals(Measurement<SpeedUnit>, double tolerance)` override delegating to `super.equals` then a
    converter-mediated tolerance comparison, plus the full 9-method `add`/`subtract` overload family (static
    double/Number versions, 3-arg static, `addAndReturnNew` static/instance, mutating instance overloads).
  - `SpeedConverter`: stateless, private no-arg constructor, package-visible `static final double` factor
    constants named `<BASE_PLURAL>_PER_<OTHER>`, 7 `convert` overloads funnelling every conversion through the
    base unit via two switch expressions, plus a dedicated `<unit>To<base>`/`<base>To<unit>` method pair per
    non-base unit.
  - `SpeedFormatter extends MeasureFormatter<Speed, SpeedUnit>`: 3 constructors, `equals`/`hashCode` boilerplate,
    `getUnitSystem(String)` via `findUnit` + `SpeedUnit.getUnitSystem`, `parse` delegating to
    `internalParse(source, new Speed())`, `findUnit` as an ordered `if (source.contains(SYMBOL + " ") ||
    source.endsWith(SYMBOL))` chain, `formatAndConvert` dispatching to `formatAndConvertMetric`/
    `formatAndConvertImperial` by `UnitSystem`, and `getUnitSymbol` as a switch expression.
  - Base class `Measurement<T extends Enum<?>>` (`Measurement.java`) already provides `equals(Object)`/
    `hashCode()`/`getValue()`/`setValue()`/`getUnit()`/`setUnit()` — subclasses only add the tolerance-`equals`
    override, never re-override the identity `equals`/`hashCode`.
  - `UnknownUnitException` is thrown only from the shared `MeasureFormatter.internalParse` helper — no
    per-family exception-handling code is needed in any of the 4 new converters/formatters.
- Tests mirror `SpeedUnitTest` (enum helpers), `SpeedTest` (~20 methods: constructor, equals/hashCode,
  equals-with-tolerance, get/set, every add/subtract overload, serialize/deserialize), `SpeedConverterTest`
  (per-pair conversion tests + an exhaustive `testConvertDouble` matrix + `Speed`-object convert tests),
  `SpeedFormatterTest` (constructors, clone, equals/hashCode, every format/parse overload including locale-aware
  parsing with `Locale("es","ES")`, `findUnit`, `getUnitSymbol`, `getUnitSystem`, format-and-convert
  metric/imperial).
- `README.md` lines ~171–185 hold the "🧭 Supported measurement families" table (two columns: backtick-quoted
  class name, backtick-quoted comma-separated example units), ending with the `MagneticFluxDensity` row.
- No existing code references `Force`, `Pressure`, `Energy`, or `Power` — these are pure additions, no existing
  file needs modification besides `README.md`.

## Implementation steps

### Group 1 — Force, Pressure, Energy, Power families _(java)_

**Parallelizable: yes** — the four families share no files, no cross-references, and no ordering dependency; each
task is a fully self-contained quadruplet + tests.

- [x] Task 1. **Add the `Force` measurement family** _(java)_ — implemented (ForceUnit, Force, ForceConverter,
  ForceFormatter + 4 test classes). Group validation: all tests pass; coverage 100% line/branch on all 4 classes;
  Checkstyle flags 9 issues on ForceUnit (JavadocStyle missing period x4, array-init Indentation x5) and 1 on
  ForceFormatter (MultipleStringLiterals " "); PMD flags 1 (UselessOverridingMethod on ForceFormatter.hashCode).
  Left in place: these are exact, pre-existing patterns already present in the mirrored templates (SpeedUnit,
  SpeedFormatter) and every other existing family (Distance/Weight/AngularSpeed) — fixing them here only would
  contradict the "mirror the template" instruction and create inconsistency with the rest of the codebase.
  - [x] Task 1.1. Create `src/main/java/com/irurueta/units/ForceUnit.java`: enum with constants `NEWTON`,
    `KILONEWTON`, `POUND_FORCE`, `DYNE`, `KILOGRAM_FORCE` (each with its own Javadoc), mirroring `SpeedUnit`'s
    `getUnitSystem`/`getMetricUnits`/`getImperialUnits`/`isMetric`/`isImperial` methods with `POUND_FORCE` as the
    sole imperial constant per the Task summary's metric/imperial split.
  - [x] Task 1.2. Create `src/test/java/com/irurueta/units/ForceUnitTest.java` mirroring `SpeedUnitTest`
    (`testGetUnitSystem` including the null-throws case, `testGetMetricUnits`, `testGetImperialUnits`,
    `testIsMetric`, `testIsImperial`).
  - [x] Task 1.3. Create `src/main/java/com/irurueta/units/Force.java`: `extends Measurement<ForceUnit>`, public
    `Force(Number value, ForceUnit unit)` constructor, package-private no-arg constructor, `equals(Measurement<
    ForceUnit>, double tolerance)` override using `ForceConverter.convert`, and the full 9-method `add`/
    `subtract` overload family mirroring `Speed.java`.
  - [x] Task 1.4. Create `src/test/java/com/irurueta/units/ForceTest.java` mirroring `SpeedTest`'s full method
    set (constructor, equals, hashCode, equals-with-tolerance, get/set, every add/subtract overload,
    serialize/deserialize).
  - [x] Task 1.5. Create `src/main/java/com/irurueta/units/ForceConverter.java`: stateless, private no-arg
    constructor, the `NEWTONS_PER_*` constants from the Task summary, base unit `NEWTON`, the `convert`
    overloads (`convert(Force,Force)`, `convertAndReturnNew(Force,ForceUnit)`, `convert(Force,ForceUnit)`,
    `convert(Force,ForceUnit,Force)`, `convert(Number,ForceUnit,ForceUnit)`, `convert(double,ForceUnit,
    ForceUnit)` — 6 overloads, matching the actual established `SpeedConverter`/`DistanceConverter`/
    `WeightConverter` pattern rather than the plan text's literal "7" count, plus per-unit
    `<unit>ToNewton`/`newtonTo<Unit>` helper pairs), following `SpeedConverter`'s two-switch-expression structure.
  - [x] Task 1.6. Create `src/test/java/com/irurueta/units/ForceConverterTest.java` mirroring
    `SpeedConverterTest` (a conversion test per unit pair, an exhaustive `testConvertDouble` matrix across all
    `ForceUnit` combinations, `testConvertNumber`, `testConvertForce`, `testConvertAndUpdateForce`,
    `testConvertAndReturnNewForce`, `testConvertToOutputForceUnit`).
  - [x] Task 1.7. Create `src/main/java/com/irurueta/units/ForceFormatter.java`: `extends MeasureFormatter<Force,
    ForceUnit>`, the symbol constants from the Task summary, the standard 3 constructors, `equals`/`hashCode`
    boilerplate, `getUnitSymbol` switch expression, `findUnit` with the ordering from the Task summary,
    `getUnitSystem(String)`, `parse` delegating to `internalParse(source, new Force())`, and
    `formatAndConvertMetric`/`formatAndConvertImperial` per the Task summary's threshold design.
  - [x] Task 1.8. Create `src/test/java/com/irurueta/units/ForceFormatterTest.java` mirroring
    `SpeedFormatterTest`'s full method set (constructors, clone, equals/hashCode, every format/parse overload
    including a `Locale("es","ES")` round trip, `findUnit`, `getUnitSymbol`, `getUnitSystem`,
    `formatAndConvertMetric`/`formatAndConvertImperial`, `UnknownUnitException`/`ParseException` cases).

- [x] Task 2. **Add the `Pressure` measurement family** _(java)_ — implemented (PressureUnit, Pressure,
  PressureConverter, PressureFormatter + 4 test classes). Group validation: all tests pass; coverage 100%
  line/branch except PressureFormatter (97.87% line, 97.44% branch — well above the 80% bar; one untested branch
  in `formatAndConvertMetric`'s bar-range check, left as-is since coverage already clears the target); Checkstyle
  flags 9 issues on PressureUnit (JavadocStyle x4, Indentation x5) and 1 on PressureFormatter
  (MultipleStringLiterals); PMD flags 1 (UselessOverridingMethod on PressureFormatter.hashCode). Left in place:
  same pre-existing template pattern as Task 1 (see its note).
  - [x] Task 2.1. Create `src/main/java/com/irurueta/units/PressureUnit.java`: enum with constants `PASCAL`,
    `KILOPASCAL`, `BAR`, `ATMOSPHERE`, `PSI`, mirroring `SpeedUnit`'s helper methods with `PSI` as the sole
    imperial constant.
  - [x] Task 2.2. Create `src/test/java/com/irurueta/units/PressureUnitTest.java` mirroring `SpeedUnitTest`.
  - [x] Task 2.3. Create `src/main/java/com/irurueta/units/Pressure.java`: `extends Measurement<PressureUnit>`,
    same constructor/equals-with-tolerance/add/subtract shape as `Speed.java`, using `PressureConverter`.
  - [x] Task 2.4. Create `src/test/java/com/irurueta/units/PressureTest.java` mirroring `SpeedTest`.
  - [x] Task 2.5. Create `src/main/java/com/irurueta/units/PressureConverter.java`: base unit `PASCAL`, the
    `PASCALS_PER_*` constants from the Task summary, the `convert` overloads (6, matching the actual
    `SpeedConverter`/`DistanceConverter`/`WeightConverter` pattern rather than the plan text's literal "7"
    count) and per-unit helper method pairs, following `SpeedConverter`'s structure.
  - [x] Task 2.6. Create `src/test/java/com/irurueta/units/PressureConverterTest.java` mirroring
    `SpeedConverterTest`.
  - [x] Task 2.7. Create `src/main/java/com/irurueta/units/PressureFormatter.java`: `extends
    MeasureFormatter<Pressure, PressureUnit>`, the symbol constants from the Task summary, `findUnit` ordering
    (`KILOPASCAL` before `PASCAL`), and `formatAndConvertMetric`/`formatAndConvertImperial` per the Task
    summary's design, following `SpeedFormatter`'s structure otherwise.
  - [x] Task 2.8. Create `src/test/java/com/irurueta/units/PressureFormatterTest.java` mirroring
    `SpeedFormatterTest`.

- [x] Task 3. **Add the `Energy` measurement family** _(java)_ — implemented (EnergyUnit, Energy,
  EnergyConverter, EnergyFormatter + 4 test classes). Group validation: all tests pass; coverage 100% line/branch
  on all 4 classes; Checkstyle flags 6 issues on EnergyUnit (array-init Indentation) and 1 on EnergyFormatter
  (MultipleStringLiterals); PMD flags 1 (UselessOverridingMethod on EnergyFormatter.hashCode). Left in place:
  same pre-existing template pattern as Task 1 (see its note).
  - [x] Task 3.1. Create `src/main/java/com/irurueta/units/EnergyUnit.java`: enum with constants `JOULE`,
    `KILOJOULE`, `CALORIE`, `KILOCALORIE`, `KILOWATT_HOUR`, `BTU`, mirroring `SpeedUnit`'s helper methods with
    `BTU` as the sole imperial constant.
  - [x] Task 3.2. Create `src/test/java/com/irurueta/units/EnergyUnitTest.java` mirroring `SpeedUnitTest`.
  - [x] Task 3.3. Create `src/main/java/com/irurueta/units/Energy.java`: `extends Measurement<EnergyUnit>`, same
    shape as `Speed.java`, using `EnergyConverter`.
  - [x] Task 3.4. Create `src/test/java/com/irurueta/units/EnergyTest.java` mirroring `SpeedTest`.
  - [x] Task 3.5. Create `src/main/java/com/irurueta/units/EnergyConverter.java`: base unit `JOULE`, the
    `JOULES_PER_*` constants from the Task summary, the `convert` overloads (6, matching the actual
    `SpeedConverter` pattern rather than the plan text's literal "7" count) and per-unit helper method pairs.
  - [x] Task 3.6. Create `src/test/java/com/irurueta/units/EnergyConverterTest.java` mirroring
    `SpeedConverterTest`.
  - [x] Task 3.7. Create `src/main/java/com/irurueta/units/EnergyFormatter.java`: `extends
    MeasureFormatter<Energy, EnergyUnit>`, the symbol constants from the Task summary, `findUnit` ordering
    (`KILOJOULE` before `JOULE`, `KILOCALORIE` before `CALORIE`), and `formatAndConvertMetric`/
    `formatAndConvertImperial` per the Task summary's design.
  - [x] Task 3.8. Create `src/test/java/com/irurueta/units/EnergyFormatterTest.java` mirroring
    `SpeedFormatterTest`.

- [x] Task 4. **Add the `Power` measurement family** _(java)_ — implemented (PowerUnit, Power, PowerConverter,
  PowerFormatter + 4 test classes). Group validation: all tests pass; coverage 100% line/branch on all 4 classes;
  Checkstyle flags 4 issues on PowerUnit (array-init Indentation) and 1 on PowerFormatter
  (MultipleStringLiterals); PMD flags 1 (UselessOverridingMethod on PowerFormatter.hashCode). Left in place: same
  pre-existing template pattern as Task 1 (see its note).
  - [x] Task 4.1. Create `src/main/java/com/irurueta/units/PowerUnit.java`: enum with constants `WATT`,
    `KILOWATT`, `MEGAWATT`, `HORSEPOWER`, mirroring `SpeedUnit`'s helper methods with `HORSEPOWER` as the sole
    imperial constant.
  - [x] Task 4.2. Create `src/test/java/com/irurueta/units/PowerUnitTest.java` mirroring `SpeedUnitTest`.
  - [x] Task 4.3. Create `src/main/java/com/irurueta/units/Power.java`: `extends Measurement<PowerUnit>`, same
    shape as `Speed.java`, using `PowerConverter`.
  - [x] Task 4.4. Create `src/test/java/com/irurueta/units/PowerTest.java` mirroring `SpeedTest`.
  - [x] Task 4.5. Create `src/main/java/com/irurueta/units/PowerConverter.java`: base unit `WATT`, the
    `WATTS_PER_*` constants from the Task summary, the `convert` overloads (6, matching the actual
    `SpeedConverter` pattern rather than the plan text's literal "7" count) and per-unit helper method pairs.
  - [x] Task 4.6. Create `src/test/java/com/irurueta/units/PowerConverterTest.java` mirroring
    `SpeedConverterTest`.
  - [x] Task 4.7. Create `src/main/java/com/irurueta/units/PowerFormatter.java`: `extends MeasureFormatter<Power,
    PowerUnit>`, the symbol constants from the Task summary, `findUnit` ordering (`KILOWATT` and `MEGAWATT`
    before `WATT`), and `formatAndConvertMetric`/`formatAndConvertImperial` per the Task summary's design.
  - [x] Task 4.8. Create `src/test/java/com/irurueta/units/PowerFormatterTest.java` mirroring
    `SpeedFormatterTest`.

Group validation (run once for the whole group by `java-code-one-task-group`, not per task): full test suite,
≥80% JaCoCo coverage on the 16 new classes, and a Checkstyle/PMD/SpotBugs regression check against the
pre-group baseline.

**Result:** License headers — all 32 new files already compliant (Apache 2.0, 2026). Javadoc — all 16 classes
already fully documented; `mvn javadoc:jar` build succeeded; `package-info.java` description extended to mention
force/pressure/energy/power. Scoped tests — 320/320 passed. Coverage — 100% line/branch on 15 of 16 classes;
PressureFormatter at 97.87%/97.44% (one untested branch, still clears the 80% bar). Full suite — 1373/1373
passed, BUILD SUCCESS. Code-quality regression — 36 new findings (32 Checkstyle, 4 PMD, 0 SpotBugs), all of them
exact reproductions of style patterns already present in every existing measurement family's equivalent files
(SpeedUnit/DistanceUnit/WeightUnit for the enum Indentation/JavadocStyle findings; SpeedFormatter/
DistanceFormatter/WeightFormatter/AngularSpeedFormatter for the Formatter MultipleStringLiterals/PMD
UselessOverridingMethod findings) — left in place as unavoidable given the tasks' explicit instruction to mirror
those templates.

### Group 2 — README update _(no language tag — docs only)_

**Parallelizable: yes** (single task; depends on Group 1 having landed so the class names it documents actually
exist).

- [x] Task 5. Add four rows to the "🧭 Supported measurement families" table in `README.md` (immediately after
  the existing `MagneticFluxDensity` row), matching the existing two-column backtick format:
  ```
  | `Force` | `NEWTON`, `KILONEWTON`, `POUND_FORCE`, `DYNE` |
  | `Pressure` | `PASCAL`, `BAR`, `ATMOSPHERE`, `PSI` |
  | `Energy` | `JOULE`, `KILOJOULE`, `KILOWATT_HOUR`, `BTU` |
  | `Power` | `WATT`, `KILOWATT`, `HORSEPOWER` |
  ```
  — Added the four rows to `README.md` immediately after the `MagneticFluxDensity` row. Docs-only change; no
  tests/coverage/code-quality applicable.
