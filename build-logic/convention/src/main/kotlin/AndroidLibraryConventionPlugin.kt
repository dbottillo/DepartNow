
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.getByType
import com.dbottillo.replacename.configureKotlinAndroid
import com.dbottillo.replacename.registerTestTasks
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-kapt")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                defaultConfig.vectorDrawables.useSupportLibrary = true
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                beforeVariants {
                    it.enable = it.buildType == "release"
                }
            }
            extensions.getByType<KaptExtension>().apply {
                useBuildCache = true
                correctErrorTypes = true
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("androidTestImplementation", kotlin("test"))
                add("testImplementation", kotlin("test"))
                // add("implementation", libs.bund)
                "implementation"(libs.findBundle("ui").get())
            }

            registerTestTasks()
        }
    }
}
