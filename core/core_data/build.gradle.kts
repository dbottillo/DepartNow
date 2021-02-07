plugins {
    id("kotlin")
    kotlin("kapt")
    id("common-precompiled")
}

kapt {
    useBuildCache = true
}

dependencies {
    di()

    test()
    lintChecks(project(":lint-rules"))
}
