import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    var ktorVersion = "2.3.4"
    val precomposeVersion = "1.5.1"

    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("it.skrape:skrapeit:1.2.2")
    implementation("org.kodein.di:kodein-di-framework-compose:7.19.0")
    implementation("com.google.firebase:firebase-admin:9.2.0")

    api("moe.tlaster:precompose-viewmodel:$precomposeVersion")
    // Include the Test API
    testImplementation(compose.desktop.uiTestJUnit4)
}

compose.desktop {
    application {
        mainClass = "de.xorg.gsapp.admin.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "GSApp Push Maker"
            packageVersion = "1.0.0"

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
