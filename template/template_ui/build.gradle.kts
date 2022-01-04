plugins {
    id("common-android-precompiled")
    id("common-dagger-precompiled")
}

dependencies {
    implementation(project(":core:core_ui"))
    implementation(project(":domain:domain_ui"))
    implementation(project(":feature_{FEATURE_NAME}:{FEATURE_NAME}_data"))
}
