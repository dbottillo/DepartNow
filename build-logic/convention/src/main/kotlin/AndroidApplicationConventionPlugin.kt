import com.android.build.api.dsl.ApplicationExtension
import com.dbottillo.replacename.configureJavaAndKotlinVersions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import com.dbottillo.replacename.configureKotlinAndroid
import com.dbottillo.replacename.registerTestTasks
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-kapt")
            }

            configureJavaAndKotlinVersions()
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
            }
            extensions.getByType<KaptExtension>().apply {
                useBuildCache = true
                correctErrorTypes = true
            }
            registerTestTasks()

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", libs.findBundle("test").get())
            }
        }
    }
}
