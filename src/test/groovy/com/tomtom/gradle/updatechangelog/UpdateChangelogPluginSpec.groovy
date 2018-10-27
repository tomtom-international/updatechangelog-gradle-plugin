package com.tomtom.gradle.updatechangelog

import groovy.lang.MissingPropertyException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification


class UpdateChangelogPluginSpec extends Specification {

  def "updateChangelog task should fail if plugin is not applied."(){
    given: "a gradle project without applied updatechangelog plugin"
      Project project = ProjectBuilder.builder().build()
    when: "executing the updateChangelog task"
      project.updateChangelog.execute()
    then:
      thrown MissingPropertyException
  }

  def "updateChangelog plugin should exist if plugin is applied."() {
    given: "a gradle project "
      Project project = ProjectBuilder.builder().build()
    when: "applying the plugin"
      project.plugins.apply('com.tomtom.gradle.updatechangelog')
    then: "we expect the plugin be available in the plugin manager"
      project.getPluginManager().hasPlugin("com.tomtom.gradle.updatechangelog") == true
  }

  def "updateChangelog task should exist if plugin is applied."() {
    given: "gradle project"
      Project project = ProjectBuilder.builder().build()
    when:
      project.plugins.apply('com.tomtom.gradle.updatechangelog')
    then:
      project.tasks.updateChangelog
      notThrown MissingPropertyException
  }
}
