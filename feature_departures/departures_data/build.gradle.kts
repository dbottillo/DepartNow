plugins {
    id("replacename.kotlin.feature.library")
}

dependencies {
    implementation(libs.bundles.network)
    kapt(libs.moshi.codegen)
}
