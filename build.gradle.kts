import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.*

val appName = "GSApp Push Maker"
val appVersion = "1.0.0"

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.jetbrainsCompose)
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(libs.firebase.admin)
    implementation(libs.kodein.compose)
    implementation(libs.ktor)
    implementation(libs.ktor.cio)
    implementation(libs.skrapeit)
    api(libs.precompose.viewModel)
}

compose.desktop {
    application {
        mainClass = "de.xorg.gsapp.admin.MainKt"

        buildTypes.release.proguard {
            version = "7.4.1"
            configurationFiles.from(project.file("proguard-rules.pro"))
        }

        nativeDistributions {
            modules("java.naming")

            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = appName
            packageVersion = appVersion

            macOS {
                iconFile.set(project.file("icon/icon_normal.icns"))
            }
            windows {
                iconFile.set(project.file("icon/icon_normal.ico"))
            }
            linux {
                iconFile.set(project.file("icon/icon_normal.png"))
            }
        }
    }
}

tasks {
    withType<org.gradle.jvm.tasks.Jar> {
        exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
    }
}

val innoSetup by tasks.registering(Exec::class) {
    // Überprüfen, ob das Betriebssystem Windows ist
    onlyIf {
        System.getProperty("os.name").lowercase(Locale.getDefault()).contains("windows")
    }

    // Compile application to binary first
    dependsOn("createReleaseDistributable")

    workingDir = projectDir

    // Write version to InnoSetup-Script
    val verFile = File("setup/version.iss")
    verFile.writeText("#define GSAppVersion \"${appVersion}\"")

    // Pfad zum Inno Setup Compiler ISCC.exe
    val isccPath = "C:\\Program Files (x86)\\Inno Setup 6\\ISCC.exe"

    // Pfad zur Inno Setup-Skriptdatei

    val scriptPath = File(projectDir, "setup/setup.iss").absolutePath

    // Befehlszeile konfigurieren
    commandLine(isccPath, scriptPath)

    doFirst {
        println("Executing InnoSetup compiler: $commandLine")
    }
}