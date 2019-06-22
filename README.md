# updatechangelog-gradle-plugin

Gradle plugin for automating the changelog update process.

This plugin creates a task *updateChangelog* that can be hooked for example into the
[Gradle release plugin](https://github.com/researchgate/gradle-release) and update a
changelog file based on the released version and the release date.

## How to use it

1. Create a changelog file

    ```bash
    echo "# Changelog" > CHANGELOG.md
    ```

1. Add a new bullet point below the header:

    ```markdown
    # Changelog
      * Initial commit
    ```

1. Add the Gradle plugin
    
    1. Using the [plugins DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block):
    
    In your *setting.gradle.kts*    
    
    ```kotlin
    pluginManagement {
        repositories {
            maven {
                url = "https://dl.bintray.com/tomtom-international/gradle-plugins"
            }
        }
    }
    ```    
    
    In *build.gradle.kts*
    
    ```kotlin
    plugins {
        id("com.tomtom.gradle.updatechangelog") version "<version>"
    }
    ```
    
    1. Using [legacy plugin application](https://docs.gradle.org/current/userguide/plugins.html#sec:old_plugin_application):
    
    ```groovy
    buildscript {
        repositories {
            maven {
              url "https://dl.bintray.com/tomtom-international/gradle-plugins"
            }
        }

        dependencies {
            classpath 'com.tomtom.gradle:updatechangelog-plugin:<version>'
        }
    }

    apply: com.tomtom.gradle.updatechangelog
    ```

1. Configure it in *build.gradle*

    ```groovy
    changelog {
      versionPlaceholder = '# Changelog'
      changelogFilename = 'CHANGELOG.md'
    }
    ```

1. Run the *updateChangelog* task

    This step is just needed to validate that the update task is working as expected. Once you run
    it you should revert the changes it did in your changelog file.

    ```bash
    ./gradlew updateChangelog
    ```
    This command will update the *CHANGELOG.md* file with the version provided by the project version
    property and the current date.

    ```markdown
    # Changelog

    **1.0.0** (2018-10-23):
      * Initial commit
    ```

1. In most cases you will hook this plugin under the Gradle release plugin so that the changelog
   gets updated whenever a new release is done.

    ```gradle
    preTagCommit.dependsOn updateChangelog
    ```

## How to compile and run tests

Run a clean build:

```bash
./gradlew clean build
```

This command will clean your workspace, build the code and tests and run the unit and functional tests.

To run only tests use the following command:

```bash
./gradlew check
```
