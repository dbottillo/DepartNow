plugins {
    id("replacename.kotlin.library")
}

kapt {
    useBuildCache = true
}

dependencies {
    test()
    lintChecks(project(":lint-rules"))
}
