plugins {
    id("kotlin")
    kotlin("kapt")
    id("common-precompiled")
}

kapt {
    useBuildCache = true
}

dependencies {
    implementation(project(":core:core_data"))
    implementation(project(":domain:domain_data"))

    implementation(libs.bundles.core)
    di()

    test()
    lintChecks(project(":lint-rules"))
}
