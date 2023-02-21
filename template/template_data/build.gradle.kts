plugins {
    id("replacename.kotlin.feature.library")
}

dependencies {
    implementation(project(":core:core_data"))
    implementation(project(":domain:domain_data"))

    core()

    test()
    lintChecks(project(":lint-rules"))
}
