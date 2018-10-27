package com.tomtom.gradle.updatechangelog.model

import org.gradle.api.Project

/**
 * Configuration options for the {@link com.tomtom.gradle.updatechangelog.UpdateChangelogPlugin}.
 * <p>
 * Below is a full configuration example.
 * <pre>
 * apply plugin: 'com.tomtom.gradle.updatechangelog'
 *
 * changelog {
 *   changelogFilename = "MY_CHANGELOG.md"
 *   versionPlaceHolder = "# My changelog"
 * }
 * </pre>
 */
class UpdateChangelogExtension {
    String changelogFilename = "CHANGELOG.md"
    String versionPlaceholder = "# Changelog"
    UpdateChangelogExtension(Project project) {}
}
