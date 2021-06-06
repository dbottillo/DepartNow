pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.android.library") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
            if (requested.id.id == "com.android.application") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "ReplaceName"

include(":app")
include(":core:core_data", ":domain:domain_data", ":core:core_ui", ":domain:domain_ui", ":lint-rules")
include(":feature_about:about_data", ":feature_about:about_ui")
include(":feature_home:home_data", ":feature_home:home_ui")
