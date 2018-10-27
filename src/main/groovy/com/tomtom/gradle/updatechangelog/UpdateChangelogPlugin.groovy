package com.tomtom.gradle.updatechangelog

import com.tomtom.gradle.updatechangelog.model.UpdateChangelogExtension
import org.gradle.api.Project
import org.gradle.api.Plugin


class UpdateChangelogPlugin implements Plugin<Project> {

  void apply(Project project) {
    def extension = project.extensions.create("changelog", UpdateChangelogExtension, project)

    project.task("updateChangelog") {
      group = "Release"
      description = "Replace `${extension.versionPlaceholder}` tag in projects ${extension.changelogFilename} file with" +
                    " current date and version. Also generate a new `${extension.versionPlaceholder}` for future."
      doLast {
        def date = new Date().format("yyyy-MM-dd")
        def versionHeader = "${extension.versionPlaceholder}\n\n**${project.version}** (${date}):"
        ant.replace(
          file: extension.changelogFilename,
          token: extension.versionPlaceholder,
          value: versionHeader,
          failOnNoReplacements: true  // Fail if token missing in changelog file.
        )
      }
    }
  }
}
