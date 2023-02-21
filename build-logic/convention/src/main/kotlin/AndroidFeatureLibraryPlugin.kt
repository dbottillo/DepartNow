import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.getByType
import com.dbottillo.replacename.configureKotlinAndroid
import org.gradle.api.artifacts.VersionCatalogsExtension

class AndroidFeatureLibraryPlugin : Plugin<Project> {
    @Suppress("StringLiteralDuplication", "MagicNumber")
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("replacename.android.library")
                apply("replacename.android.hilt")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", project(":core:core_ui"))
                add("implementation", project(":domain:domain_ui"))
                add("androidTestImplementation", kotlin("test"))
                add("testImplementation", kotlin("test"))

                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                "implementation"(libs.findBundle("ui").get())
            }
        }
    }
}
