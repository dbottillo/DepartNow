plugins {
    id("kotlin")
    kotlin("kapt")
    id("common-precompiled")
}

kapt {
    useBuildCache = true
}

dependencies {
    core()
    di()
    network()

    test()
    lintChecks(project(":lint-rules"))
}
