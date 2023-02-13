
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin")
                apply("java-library")
                apply("com.android.lint")
                apply("org.jetbrains.kotlin.kapt")
            }

            dependencies {
                add("implementation", "javax.inject:javax.inject:1")
            }

            val java = extensions.getByType<JavaPluginExtension>()
            java.apply {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }

           /* val kotlinCompile = extensions.getByType<KotlinCompile>()
            kotlinCompile.apply {
                kotlinOptions {
                    jvmTarget = "11"
                }
            }

            extensions.getByType<Test>().apply {
                useJUnitPlatform()
            }*/

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "implementation"(libs.findLibrary("coroutines.core").get())
            }

            this.tasks.register("devTest") {
                dependsOn("test")
            }
        }
    }
}
