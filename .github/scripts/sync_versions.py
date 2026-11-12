#!/usr/bin/env python3
"""Rewrites version references after a minor/major release, for the Sync workflow.

Reads RELEASE_VERSION and NEXT_SNAPSHOT from the environment and updates pom.xml, README.md,
docs/antora.yml, and docs/modules/ROOT/pages/installation.adoc. Only invoked by sync.yml for a X.Y.0
release - patch releases (X.Y.Z, Z > 0) are cut as hotfix branches from master while develop is already
ahead on the next minor snapshot, so they skip this script entirely and only merge the hotfix back.

CHANGELOG.md is intentionally left untouched: its release section and fresh "[Unreleased]" heading are
written before the tag is published, and arrive on develop via the merge that precedes this script, not by
bumping a version string.
"""
import os
import re
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parents[2]
ARTIFACT_ID = "irurueta-units"


def update_pom(next_snapshot):
    path = REPO_ROOT / "pom.xml"
    text = path.read_text()
    updated, count = re.subn(
        rf"(<artifactId>{re.escape(ARTIFACT_ID)}</artifactId>\s*\n\s*<version>)[^<]*(</version>)",
        rf"\g<1>{next_snapshot}\g<2>",
        text,
        count=1,
    )
    if count != 1:
        raise SystemExit(f"pom.xml: could not find the <artifactId>{ARTIFACT_ID}</artifactId> <version> element to bump")
    path.write_text(updated)


def update_readme(release_version, next_snapshot):
    path = REPO_ROOT / "README.md"
    text = path.read_text()

    # "- Current development version: `X.Y.Z-SNAPSHOT`" bullet under Project status.
    text, count = re.subn(
        r"(?m)^(- Current development version: `)[^`]*(`)$",
        rf"\g<1>{next_snapshot}\g<2>",
        text,
        count=1,
    )
    if count != 1:
        raise SystemExit("README.md: could not find the 'Current development version' bullet to bump")

    # Maven XML snippet under "For a released dependency" -> release version;
    # the one under "For local development against the current repository snapshot" -> next snapshot.
    lines = text.splitlines(keepends=True)
    pending = None
    for i, line in enumerate(lines):
        stripped = line.strip()
        if stripped.startswith("For a released dependency"):
            pending = release_version
        elif stripped.startswith("For local development against the current repository snapshot"):
            pending = next_snapshot
        elif pending and f"<artifactId>{ARTIFACT_ID}</artifactId>" not in line and "<version>" in line:
            lines[i] = re.sub(r"(<version>)[^<]*(</version>)", rf"\g<1>{pending}\g<2>", line)
            pending = None
    text = "".join(lines)

    # Gradle snippet: implementation("com.irurueta:irurueta-units:X.Y.Z") -> release version.
    text = re.sub(
        rf'(implementation\("com\.irurueta:{re.escape(ARTIFACT_ID)}:)[^"]*(")',
        rf"\g<1>{release_version}\g<2>",
        text,
        count=1,
    )

    path.write_text(text)


def update_antora_component_version(release_version):
    path = REPO_ROOT / "docs" / "antora.yml"
    if not path.exists():
        return
    text = path.read_text()
    updated, count = re.subn(r"(?m)^version:.*$", f"version: {release_version}", text, count=1)
    if count == 1:
        path.write_text(updated)


def update_antora_installation_page(release_version, next_snapshot):
    path = REPO_ROOT / "docs" / "modules" / "ROOT" / "pages" / "installation.adoc"
    if not path.exists():
        return
    text = path.read_text()

    # Maven <version> snippet -> release version.
    text, _ = re.subn(
        r"(<artifactId>" + re.escape(ARTIFACT_ID) + r"</artifactId>\s*\n\s*<version>)[^<]*(</version>)",
        rf"\g<1>{release_version}\g<2>",
        text,
        count=1,
    )

    # Gradle implementation string -> release version.
    text, _ = re.subn(
        rf'(implementation\("com\.irurueta:{re.escape(ARTIFACT_ID)}:)[^"]*(")',
        rf"\g<1>{release_version}\g<2>",
        text,
        count=1,
    )

    # "for example `X.Y.Z-SNAPSHOT`" prose -> next snapshot.
    text, _ = re.subn(
        r"(for example `)[^`]*(`)",
        rf"\g<1>{next_snapshot}\g<2>",
        text,
        count=1,
    )

    path.write_text(text)


def main():
    release_version = os.environ["RELEASE_VERSION"]
    next_snapshot = os.environ["NEXT_SNAPSHOT"]

    update_pom(next_snapshot)
    update_readme(release_version, next_snapshot)
    update_antora_component_version(release_version)
    update_antora_installation_page(release_version, next_snapshot)


if __name__ == "__main__":
    main()
