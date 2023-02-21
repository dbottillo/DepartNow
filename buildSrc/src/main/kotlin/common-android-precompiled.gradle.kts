plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("common-android-test-precompiled")
}

android {
    compileSdk = Config.Android.compileSdk

    defaultConfig {
        minSdk = Config.Android.minSDk
        targetSdk = Config.Android.targetSdk
        // this is still required to avoid generating PNGs even if min sdk is 21
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
        viewBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    packagingOptions {
        resources.excludes.add("LICENSE.txt")
        resources.excludes.add("LICENSE.txt")
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("META-INF/LICENSE")
        resources.excludes.add("META-INF/NOTICE")
        resources.excludes.add("META-INF/INDEX.LIST")
        resources.excludes.add("META-INF/services/javax.annotation.processing.Processor")
        resources.excludes.add("META-INF/rxjava.properties")
        resources.excludes.add("META-INF/io.netty.versions.properties")
    }

    androidComponents.beforeVariants { variantBuilder ->
        variantBuilder.enable = variantBuilder.buildType == "release"
    }

    lint {
        xmlReport = false
        lintConfig = file("$rootDir/config/lint/lint.xml")
        checkDependencies = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.compilerExtensionVersion
    }
}

kapt {
    useBuildCache = true
    correctErrorTypes = true
}

dependencies {
    core()
    ui()
    lifecycle()

    test()
    uiTest()
}
