plugins {
    id("kotlin")
    kotlin("kapt")
    id("common-precompiled")
}

kapt {
    useBuildCache = true
}

dependencies {
    implementation(libs.bundles.core)
    network()

    test()
    lintChecks(project(":lint-rules"))
}
