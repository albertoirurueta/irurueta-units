# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed

- Raised the Java compiler source/target and CI JDK version from 17 to 21.
- Replaced the standalone `sonar-scanner` CLI step in the GitHub workflows with the `sonar-maven-plugin`
  (`mvn sonar:sonar`), so SonarCloud analysis runs under the same JDK 21 toolchain as the rest of the build.
  Sonar organization/project key/host settings moved from `sonar-project.properties` (removed) into
  `pom.xml` properties.

## [1.4.0] - 2026-06-30

### Changed

- Added an Antora-based documentation site under `docs/` (site config, ROOT module, `reference.adoc`) and
  updated `README.md` with links to it.
- No changes to library source or tests in this release — it is documentation/tooling only.

## [1.3.2] - 2025-09-18

### Changed

- Migrated Maven Central publishing from the Nexus staging plugin/OSSRH to the Central Publishing Maven
  Plugin.
- Updated the license URL to `https` and bumped dependency/plugin versions (JUnit Jupiter, Mockito,
  Checkstyle, PMD, SpotBugs, JXR, Surefire/Failsafe, JaCoCo).
- Minor updates to GitHub Actions workflow files.

## [1.3.1] - 2024-10-19

### Fixed

- Corrected the `junit-jupiter` dependency to be scoped as `test`, since it was previously leaking as a
  compile-scope dependency to consumers of the library.

### Changed

- Synced dependency versions.

## [1.3.0] - 2024-10-15

### Changed

- Raised the Java compiler source/target from 1.7 to 17.
- Migrated the test suite from JUnit 4 to JUnit 5.
- Adopted Java type inference (`var`) in parts of the codebase.
- Bumped numerous build plugin versions (Checkstyle, PMD, SpotBugs, JaCoCo, Surefire/Failsafe, and others).

## [1.2.0] - 2023-11-15

### Changed

- Consolidated the duplicated `clone()` implementation from every `<X>Formatter` subclass into the shared
  `MeasureFormatter` base class.
- Applied checkstyle-driven formatting and Javadoc-alignment fixes across formatter and converter classes.
- Updated test assertions across the suite to follow the `assertEquals(expected, actual)` argument
  convention.
- Updated dependency versions and added a `manual_develop.yml` GitHub Actions workflow.

## [1.1.0] - 2021-12-11

### Added

- Initial reconstructed release: a Java library for representing, converting, formatting, and parsing
  physical measurement units, covering Distance, Speed, Temperature, Volume, Weight, Time, Surface,
  Acceleration, Angle, AngularSpeed, AngularAcceleration, Frequency, and MagneticFluxDensity.
- Each unit family follows the same `<X>Unit` / `<X>` / `<X>Converter` / `<X>Formatter` shape, built on the
  shared `Measurement`, `MeasureFormatter`, `UnitSystem`, and `UnitLocale` base types.
- Project migrated from Travis CI to GitHub Actions, with Checkstyle rules and SonarCloud/code-quality
  tooling in place.

[Unreleased]: https://github.com/albertoirurueta/irurueta-units/compare/1.4.0...HEAD
[1.4.0]: https://github.com/albertoirurueta/irurueta-units/compare/1.3.2...1.4.0
[1.3.2]: https://github.com/albertoirurueta/irurueta-units/compare/1.3.1...1.3.2
[1.3.1]: https://github.com/albertoirurueta/irurueta-units/compare/1.3.0...1.3.1
[1.3.0]: https://github.com/albertoirurueta/irurueta-units/compare/1.2.0...1.3.0
[1.2.0]: https://github.com/albertoirurueta/irurueta-units/compare/1.1.0...1.2.0
[1.1.0]: https://github.com/albertoirurueta/irurueta-units/releases/tag/1.1.0
