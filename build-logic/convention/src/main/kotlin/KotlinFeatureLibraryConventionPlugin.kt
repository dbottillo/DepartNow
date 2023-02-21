
import com.dbottillo.replacename.addCoreDependencies
import com.dbottillo.replacename.configureJavaAndKotlinVersions
import com.dbottillo.replacename.configureKapt
import com.dbottillo.replacename.configureTest
import com.dbottillo.replacename.registerTestTasks
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class KotlinFeatureLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("java-library")
                apply("kotlin")
                apply("com.android.lint")
                apply("org.jetbrains.kotlin.kapt")
            }

            configureJavaAndKotlinVersions()
            configureKapt()
            addCoreDependencies()
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", project(":core:core_data"))
                add("implementation", project(":domain:domain_data"))

                add("testImplementation", libs.findBundle("test").get())
                add("testRuntimeOnly", libs.findLibrary("junit.engine").get())
            }
            configureTest()
            registerTestTasks()
        }
    }
}
