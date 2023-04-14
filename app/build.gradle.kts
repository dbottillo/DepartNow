import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("replacename.android.application")
    id("replacename.android.application.compose")
    id("replacename.android.hilt")
}

android {
    defaultConfig {
        applicationId = "com.dbottillo.replacename"
        versionCode = 1
        versionName = "0.0.1"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val transportAppKey = gradleLocalProperties(rootDir).getProperty("transportapi_app_key")
        val transportAppId = gradleLocalProperties(rootDir).getProperty("transportapi_app_id")
        getByName("debug") {
            buildConfigField("String", "TRANSPORT_APP_KEY", transportAppKey)
            buildConfigField("String", "TRANSPORT_APP_ID", transportAppId)
            matchingFallbacks.add("release")
        }
        getByName("release") {
            buildConfigField("String", "TRANSPORT_APP_KEY", transportAppKey)
            buildConfigField("String", "TRANSPORT_APP_ID", transportAppId)
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    lintOptions {
        xmlReport = false
        lintConfig = file("$rootDir/config/lint/lint.xml")
    }

    buildFeatures {
        buildConfig = true
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
        viewBinding = true
    }

    packagingOptions {
        resources.excludes.add("LICENSE.txt")
        resources.excludes.add("LICENSE.txt")
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/LICENSE-notice.md")
        resources.excludes.add("META-INF/NOTICE")
        resources.excludes.add("META-INF/INDEX.LIST")
        resources.excludes.add("META-INF/services/javax.annotation.processing.Processor")
        resources.excludes.add("META-INF/io.netty.versions.properties")
    }
    namespace = "com.dbottillo.replacename"
}

dependencies {
    implementation(project(":domain:domain_ui"))
    implementation(project(":feature_home:home_ui"))
    implementation(project(":feature_home:home_data"))
    implementation(project(":feature_about:about_ui"))
    implementation(project(":feature_departures:departures_ui"))

    implementation(libs.bundles.network)
    debugImplementation(libs.bundles.debug)

    /*implementation fileTree(dir: "libs", include: ["*.jar"])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation "androidx.appcompat:appcompat:1.0.2"
    implementation "androidx.core:core-ktx:1.0.2"
    implementation "com.google.android.material:material:1.0.0"
    implementation "androidx.constraintlayout:constraintlayout:1.1.3"
    implementation "androidx.navigation:navigation-fragment-ktx:2.0.0"
    implementation "androidx.navigation:navigation-ui-ktx:2.0.0"
    testImplementation "junit:junit:4.12"
    androidTestImplementation "androidx.test.ext:junit:1.1.1"
    androidTestImplementation "androidx.test.espresso:espresso-core:3.2.0"*/
}
