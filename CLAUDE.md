# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A Java library (`com.irurueta:irurueta-units`) for representing, converting, formatting, and parsing physical
measurement units (Distance, Speed, Temperature, Time, Volume, Weight, Surface, Acceleration, Angle,
AngularSpeed, AngularAcceleration, Frequency, MagneticFluxDensity). No runtime third-party dependencies;
Java 21 target; Maven build.

## Commands

```bash
mvn test                                   # run all unit tests
mvn test -Dtest=DistanceConverterTest      # run a single test class
mvn test -Dtest=DistanceConverterTest#testConvertDistance   # run a single test method
mvn package                                # build the JAR (also runs JaCoCo coverage)
mvn site                                   # generate reports (checkstyle, spotbugs, PMD, javadoc, jxr)
mvn checkstyle:check                       # run checkstyle against checkstyle.xml
mvn com.github.spotbugs:spotbugs-maven-plugin:check   # run SpotBugs
```

Docs (Antora, under `docs/`) build separately with Node:

```bash
cd docs && npx antora antora-playbook.yml
```

There is no separate lint step outside of Checkstyle/SpotBugs/PMD, all wired through the Maven `site`
lifecycle and configured in `pom.xml` and `checkstyle.xml`.

## Architecture

All production code lives in a single package: `com.irurueta.units`. Each measurement family (Distance,
Speed, Temperature, Volume, Weight, Time, Surface, Acceleration, Angle, AngularSpeed,
AngularAcceleration, Frequency, MagneticFluxDensity) is implemented as a self-contained quadruplet of
classes following the exact same shape. When adding or modifying a measurement family, mirror an existing
one (e.g. Distance) rather than inventing a new pattern:

- **`<X>Unit`** — an enum of the recognized units for that family (e.g. `DistanceUnit.METER`,
  `DistanceUnit.MILE`). Exposes static helpers like `getUnitSystem(unit)`, `getMetricUnits()`,
  `getImperialUnits()`, `isMetric(unit)`, `isImperial(unit)`.
- **`<X>`** — the measurement value object, extending the generic abstract base `Measurement<T extends
  Enum<?>>` (`Measurement.java`). Holds a `Number value` + `<X>Unit unit`, with `equals`/`hashCode` and a
  tolerance-based `equals(other, tolerance)`.
- **`<X>Converter`** — a stateless utility class (private constructor, all-static methods) that converts
  raw numeric values or `<X>` objects between units. Internally normalizes to one base/reference unit (e.g.
  meters for distance) via `static final double` conversion-factor constants, then converts from that base
  unit to the target. Typical overloads: `convert(value, inputUnit, outputUnit)`,
  `convert(<X> input, <X> output)`, `convertAndReturnNew(input, outputUnit)`.
- **`<X>Formatter`** — extends the generic abstract base `MeasureFormatter<M extends Measurement<U>, U
  extends Enum<?>>` (`MeasureFormatter.java`). Wraps a locale-aware `java.text.NumberFormat` plus a
  `MessageFormat` pattern (`DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN = "{0} {1}"`) to format/parse strings like
  `"12.5 ft"`. Subclasses implement `getUnitSymbol(unit)`, `findUnit(source)`, `getUnitSystem(source)`, and
  `parse(source)`.

Cross-cutting pieces:

- **`UnitSystem`** — `METRIC` / `IMPERIAL` enum, referenced by every `<X>Unit` and `<X>Formatter`.
- **`UnitLocale`** — maps a `java.util.Locale` to a default `UnitSystem` (used by formatters when no
  explicit unit system is given).
- **`UnitsException` / `UnknownUnitException`** — exception hierarchy for parse/format failures.
- **`BuildInfo`** — reads `build-info.properties`, which is generated at Maven `validate` phase by the
  `groovy-maven-plugin` block in `pom.xml` (build timestamp, version, VCS commit/branch). This generated
  resource file is not checked in as source content — it's rewritten on every build.

Tests mirror this 1:1: `src/test/java/com/irurueta/units/<X>Test.java`,
`<X>ConverterTest.java`, `<X>FormatterTest.java`, `<X>UnitTest.java`.

## Conventions enforced by Checkstyle (`checkstyle.xml`)

- Javadoc required on packages, types, methods, and variables (`JavadocPackage`, `JavadocType`,
  `JavadocMethod`, `JavadocVariable`).
- `MagicNumber` is checked — conversion factors and similar constants should be named `static final`
  fields (see `<X>Converter` classes), not inlined literals.
- Utility/converter classes with only static members must hide their constructor
  (`HideUtilityClassConstructor`) — follow the existing `private <X>Converter() {}` pattern.
- Strict import, whitespace, indentation, and declaration-order rules are enforced
  (`ImportOrder`, `UnusedImports`, `Indentation`, `DeclarationOrder`, etc.) — run `mvn checkstyle:check`
  after non-trivial edits.
- License header (Apache 2.0, `Copyright (C) <year> Alberto Irurueta Carro`) is present at the top of every
  source file — keep it when creating new files.
