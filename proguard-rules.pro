-dontobfuscate
-dontoptimize

-keepclasseswithmembers public class de.xorg.gsapp.admin.MainKt {
    public static void main(java.lang.String[]);
}

-dontwarn kotlinx.coroutines.debug.*

# Classes for Ktor (on Windows)
-keep class io.ktor.client.engine.cio.CIOEngineContainer
-keep class io.ktor.client.engine.apache.ApacheEngineContainer { *; }
-keep class org.apache.commons.logging.** { *; }
-keep class org.apache.http.client.utils.** { *; }

# KotlinX coroutines
-keep class kotlinx.coroutines.swing.SwingDispatcherFactory { *; }

# Kodein DI
-keep, allowobfuscation, allowoptimization class org.kodein.type.TypeReference
-keep, allowobfuscation, allowoptimization class org.kodein.type.JVMAbstractTypeToken$Companion$WrappingTest
-keep, allowobfuscation, allowoptimization class * extends org.kodein.type.TypeReference
-keep, allowobfuscation, allowoptimization class * extends org.kodein.type.JVMAbstractTypeToken$Companion$WrappingTest

# Rules that shouldn't be needed.
#-keep class io.ktor.client.engine.apache.** { *; }
#-keep class io.ktor.client.features.** { *; }
#-keep class io.ktor.client.engine.cio.** { *; }
#-keep class de.xorg.gsapp.admin.** { *; }
#-keep class kotlin.** { *; }
#-keep class kotlinx.coroutines.** { *; }
#-keep class org.jetbrains.skia.** { *; }
#-keep class org.jetbrains.skiko.** { *; }
#-keep class org.apache.commons.logging.impl.LogFactoryImpl { *; }
#-assumenosideeffects public class androidx.compose.runtime.ComposerKt {
#    void sourceInformation(androidx.compose.runtime.Composer,java.lang.String);
#    void sourceInformationMarkerStart(androidx.compose.runtime.Composer,int,java.lang.String);
#    void sourceInformationMarkerEnd(androidx.compose.runtime.Composer);
#}

-ignorewarnings