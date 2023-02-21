plugins {
    id("replacename.kotlin.library")
}

dependencies {
    implementation(libs.bundles.network)
    kapt(libs.moshi.codegen)
}
