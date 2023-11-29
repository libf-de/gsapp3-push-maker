[![official project](http://jb.gg/badges/official.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
# GSApp Push Maker

This is a simple desktop application to create push notifications for [GSApp3](https://github.com/libf-de/GSApp3-MP)
using Firebase Cloud Messaging.

To use this program, a service account and credentials in JSON-format are needed. If you don't want to install the
program, put the json in the same folder as the executable and name it `auth.json`.

> **This program was created using the [Compose](https://github.com/JetBrains/compose-multiplatform) Desktop template.**

## Before you start

Install the following tools:

* JDK 11 or later
* IntelliJ IDEA Community Edition or IntelliJ IDEA Ultimate 2020.3 or later (other editors may also work, but we'll use IntelliJ IDEA in this tutorial)
* The [Compose Multiplatform IDE support plugin](https://plugins.jetbrains.com/plugin/16541-compose-multiplatform-ide-support)
* [Inno Setup](https://jrsoftware.org/isinfo.php) if you want to compile the setup program. 

## Run the application
If you want to debug the application, put the `auth.json` in the project root 
(in the same folder as `settings.gradle.kts`).

### In the editor

You can run and debug the application by clicking **Run** in the gutter near the `main()` function declaration:

<img alt="Application running" src="readme_images/app-run.png" height="500" />

### Using Gradle tasks

1. In IntelliJ IDEA, open `build.gradle.kts`. After the necessary dependencies from the Maven repositories are downloaded, your project will be ready.
2. In the [Gradle tool window](https://www.jetbrains.com/help/idea/jetgradle-tool-window.html), select `sample/Tasks/compose desktop/run`:

<img alt="New project" src="readme_images/open.png" height="500" />

The first run may take some time.

### On the command line

You can also run Gradle tasks in the terminal:

* `./gradlew run` to run the application
* `./gradlew createDistributable` to store native distribution into `build/compose/binaries`
* `./gradlew packageUberJarForCurrentOS` to create a runnable JAR into `build/compose/jars`
* `./gradlew innoSetup` to create the Setup into `setup/setup.exe`