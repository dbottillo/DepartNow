plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("common-android-test-precompiled")
}

android {
    compileSdkVersion(Config.Android.compileSdk)
    buildToolsVersion(Config.Android.buildTools)

    defaultConfig {
        minSdkVersion(Config.Android.minSDk)
        targetSdkVersion(Config.Android.targetSdk)
        versionCode(Config.Android.versionCode)
        versionName(Config.Android.versionName)
        // this is still required to avoid generating PNGs even if min sdk is 21
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures {
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        exclude("LICENSE.txt")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/LICENSE")
        exclude("META-INF/NOTICE")
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/services/javax.annotation.processing.Processor")
        exclude("META-INF/rxjava.properties")
        exclude("META-INF/io.netty.versions.properties")
    }

    variantFilter {
        ignore = buildType.name == "debug"
    }

    lintOptions {
        xmlReport = false
        lintConfig = file("$rootDir/config/lint/lint.xml")
    }
}

kapt {
    useBuildCache = true
    correctErrorTypes = true
}

dependencies {
    core()
    di()
    ui()
    lifecycle()

    test()
}