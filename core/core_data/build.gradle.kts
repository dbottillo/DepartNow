plugins {
    id("java-library")
    id("kotlin")
    kotlin("kapt")
    id("com.android.lint")
    id("common-precompiled")
}

dependencies {
    dagger()

    test()
    lintChecks(project(":lint-rules"))
}