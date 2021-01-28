plugins {
    id("common-android-precompiled")
}

dependencies {
    api(project(":core:core_data"))

    core()
    di()
    ui()
    lifecycle()

    test()
}
