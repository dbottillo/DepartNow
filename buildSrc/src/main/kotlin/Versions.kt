object Versions {
    const val gradle = "4.0.1"
    const val gradlePlugin = "0.38.0"
    const val androidGradlePlugin = "4.1.3" // update also build.gradle.kts in buildSrc

    const val kotlin = "1.4.32"  // update also build.gradle.kts in buildSrc
    const val coroutines = "1.4.2"

    object AndroidX {
        const val core = "1.3.2"
        const val cardview = "1.0.0"
        const val recyclerview = "1.1.0"
        const val compat = "1.2.0"
        const val preference = "1.1.1"
        const val navigation = "2.3.4"
        const val lifecycle = "2.3.1"
        const val lifecycleExtensions = "2.2.0"
    }

    const val material = "1.3.0"
    const val dagger = "2.28-alpha" // update also build.gradle.kts in buildSrc

    const val hilt = "1.0.0-alpha01"
    const val constraint_layout = "2.0.4"

    object Retrofit {
        const val core = "2.9.0"
        const val moshi = "2.9.0"
    }
    const val moshi = "1.11.0"

    object OkHttp {
        const val core = "4.9.1"
        const val logging = "4.9.1"
    }


    // testing
    const val espresso = "3.2.0"
    const val mockito = "3.8.0"
    const val mockito_kotlin = "1.6.0"
    const val mockito_android = "3.8.0"
    const val junit = "5.7.1"
    const val hamcrest = "2.2"
    const val robolectric = "4.3.1"
    const val truth = "1.1.2"
    const val lint = "27.1.3"

    const val ktlint = "0.40.0"
}
