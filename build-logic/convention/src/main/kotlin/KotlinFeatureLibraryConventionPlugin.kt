
import com.dbottillo.replacename.addCoreDependencies
import com.dbottillo.replacename.configureJavaAndKotlinVersions
import com.dbottillo.replacename.configureKapt
import com.dbottillo.replacename.configureTest
import com.dbottillo.replacename.registerTestTasks
import org.gradle.api.Plugin
import org.gradle.api.Project

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
            configureTest()
            registerTestTasks()
        }
    }
}
