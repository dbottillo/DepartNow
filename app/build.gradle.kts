plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("common-dagger-precompiled")
}

android {
    compileSdk = Config.Android.compileSdk
    buildToolsVersion = Config.Android.buildTools

    defaultConfig {
        applicationId = Config.Android.applicationId
        minSdk = Config.Android.minSDk
        targetSdk = Config.Android.targetSdk
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            matchingFallbacks.add("release")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    lintOptions {
        xmlReport = false
        lintConfig = file("$rootDir/config/lint/lint.xml")
    }
}

dependencies {
    implementation(project(":domain:domain_ui"))
    implementation(project(":feature_home:home_ui"))
    implementation(project(":feature_home:home_data"))
    implementation(project(":feature_about:about_ui"))
    core()
    ui()
    di()
    network()
    debug()

    test()

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
