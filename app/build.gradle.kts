import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.versions)
    alias(libs.plugins.compose.compiler)
}

android {
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        applicationId = "com.dbottillo.departnow"
        minSdk = 28
        targetSdk = 34
        versionCode = 9
        versionName = "0.1.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
        viewBinding = false
    }

    buildTypes {
        val transportAppKey = gradleLocalProperties(rootDir, providers).getProperty("transportapi_app_key")
        val transportAppId = gradleLocalProperties(rootDir, providers).getProperty("transportapi_app_id")
        getByName("debug") {
            buildConfigField("String", "TRANSPORT_APP_KEY", transportAppKey)
            buildConfigField("String", "TRANSPORT_APP_ID", transportAppId)
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "TRANSPORT_APP_KEY", transportAppKey)
            buildConfigField("String", "TRANSPORT_APP_ID", transportAppId)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    lint {
        xmlReport = false
        checkDependencies = true
        lintConfig = file("$rootDir/config/lint/lint.xml")
    }

    namespace = "com.dbottillo.departnow"
}

dependencies {
    implementation(libs.bundles.ui)
    implementation(libs.bundles.network)
    kapt(libs.moshi.codegen)
    implementation(libs.bundles.old.ui)
    implementation(libs.bundles.work.manager)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.datastore)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.hilt.compiler)
}

kapt {
    useBuildCache = true
    correctErrorTypes = true
}

task("devTest") {
    dependsOn("testDebugUnitTest")
}

task("stagingTest") {
    dependsOn("testDebugUnitTest")
}

task("prodTest") {
    dependsOn("testReleaseUnitTest")
}
