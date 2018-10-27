package com.tomtom.gradle.updatechangelog

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS
import static org.gradle.testkit.runner.TaskOutcome.FAILED

class UpdateChangelogPluginFunctionalTest extends Specification {
  @Rule TemporaryFolder testProjectDir = new TemporaryFolder()
  File buildFile

  def setup() {
    buildFile = testProjectDir.newFile('build.gradle')
    buildFile << """
      plugins {
          id 'com.tomtom.gradle.updatechangelog'
      }
    """
  }

  def "build fails when default changelog does not exist in project"() {
    when:
      def result = GradleRunner.create()
          .withProjectDir(testProjectDir.root)
          .withArguments('updateChangelog')
          .withPluginClasspath()
          .buildAndFail()

    then:
      result.task(":updateChangelog").outcome == FAILED
  }

  def "default changelog not updated when version project property does not exist"() {
    given: "a default changelog file in project dir"
      File changelog = createEmptyDefaultChangelog()

    when: "running the updateChangelog task"
      def result = GradleRunner.create()
          .withProjectDir(testProjectDir.root)
          .withArguments('updateChangelog')
          .withPluginClasspath()
          .build()

    then:
      result.task(":updateChangelog").outcome == SUCCESS
  }

  def "default changelog updated when version project property exists"() {
    given: "a default changelog file in project dir"
      File changelog = createEmptyDefaultChangelog()

    and: "build.gradle with a version property"
      buildFile << """
      version = '1.2.3'
      """

    when: "running the updateChangelog task"
      def result = GradleRunner.create()
          .withProjectDir(testProjectDir.root)
          .withArguments('updateChangelog')
          .withPluginClasspath()
          .build()

    then:
      result.task(":updateChangelog").outcome == SUCCESS
      changelog.text == """
      # Changelog\n\n**1.2.3** (${new Date().format("yyyy-MM-dd")}):
      """
  }

  def "build fails when non-default changelog is used"() {
    given: "a non-default changelog file in project dir"
      File changelog = createEmptyNonDefaultChangelog()
    when:
      def result = GradleRunner.create()
          .withProjectDir(testProjectDir.root)
          .withArguments('updateChangelog')
          .withPluginClasspath()
          .buildAndFail()

    then:
      result.task(":updateChangelog").outcome == FAILED
  }

  def "non-default changelog updated when version project property exists"() {
    given: "a non-default changelog file in project dir"
      File changelog = createEmptyNonDefaultChangelog()

    and: "build.gradle with a version property"
      buildFile << """
      version = '1.2.4'
      """

    and: "updateChangelog plugin configured with non-default settings"
      buildFile << """
      changelog {
        changelogFilename = "MY_CHANGELOG.md"
        versionPlaceholder = "# My changelog"
      }
      """

    when: "running the updateChangelog task"
      def result = GradleRunner.create()
          .withProjectDir(testProjectDir.root)
          .withArguments('updateChangelog')
          .withPluginClasspath()
          .build()

    then:
      result.task(":updateChangelog").outcome == SUCCESS
      changelog.text == """
      # My changelog\n\n**1.2.4** (${new Date().format("yyyy-MM-dd")}):
      """
  }


  def createEmptyDefaultChangelog() {
    File changelog = testProjectDir.newFile('CHANGELOG.md')
      changelog << """
      # Changelog
      """
  }

  def createEmptyNonDefaultChangelog() {
    File changelog = testProjectDir.newFile('MY_CHANGELOG.md')
      changelog << """
      # My changelog
      """
  }
}
