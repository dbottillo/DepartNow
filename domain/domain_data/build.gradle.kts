plugins {
    id("replacename.kotlin.library")
}

kapt {
    useBuildCache = true
}

dependencies {
    core()
    network()

    test()
    lintChecks(project(":lint-rules"))
}
