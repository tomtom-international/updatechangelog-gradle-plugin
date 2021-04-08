# Changelog

**1.0.9** (2021-04-08):

* Use *updatechangelog* plugin from Gradle Plugin Portal instead Bintray.
* Revert changes in build.gradle introduced in 1.0.8 release which was done manually and not via CI.
* Reformat CHANGELOG.md

**1.0.8** (2021-04-07):

* Publish plugin to official Gradle Plugin Portal instead Bintray which is shutdown by JFrog.
* Update *tt-ci* GitHub API token for committing (gradle release plugin).

**1.0.7** (2019-07-11):

* Update license.
* Allow clients to apply plugin with [plugins DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block).
* Use Gradle 5.4.1 to build the plugin.
* Use plugin itself to update Changelog file.

**1.0.0** (2018-10-30):

* Initial commit
