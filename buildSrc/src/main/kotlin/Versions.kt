@file:Suppress("StringLiteralDuplication")

object Versions {
    const val gradle = "4.0.1"
    const val androidGradlePlugin = "7.4.0" // update also build.gradle.kts in buildSrc

    const val kotlin = "1.8.0" // update also build.gradle.kts in buildSrc
    const val coroutines = "1.6.4"

    object AndroidX {
        const val core = "1.9.0"
        const val cardview = "1.0.0"
        const val recyclerview = "1.2.1"
        const val compat = "1.6.0"
        const val preference = "1.2.0"
        const val navigation = "2.5.3"
        const val lifecycle = "2.5.1"
        const val lifecycleExtensions = "2.2.0"
    }

    const val material = "1.8.0"
    const val dagger = "2.44.2" // update also build.gradle.kts in buildSrc
    const val leakCanary = "2.10"

    const val constraint_layout = "2.1.4"

    object Retrofit {
        const val core = "2.9.0"

        // CHECK FIX IN gradle.properties
        const val moshi = "2.9.0" // CHECK FIX IN gradle.properties
        // CHECK FIX IN gradle.properties
    }
    const val moshi = "1.14.0"

    object OkHttp {
        const val core = "4.10.0"
        const val logging = "4.10.0"
    }

    object Testing {
        const val espresso = "3.5.1"
        const val support_runner = "1.5.2" // https://github.com/android/android-test/releases
        const val support_rules = "1.5.0" // https://github.com/android/android-test/releases
        const val core_ktx = "1.5.0" // https://github.com/android/android-test/releases
        const val junit_ktx = "1.1.5" // https://github.com/android/android-test/releases
        const val okhttp3_idling_resource = "1.0.0"
    }

    // testing
    const val mockito = "5.0.0"
    const val mockito_kotlin = "4.1.0"
    const val mockito_android = "3.8.0"
    const val junit = "5.9.2"
    const val robolectric = "4.3.1"
    const val truth = "1.1.3"
    const val lint = "30.4.0"
}
