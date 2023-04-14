plugins {
    id("replacename.android.feature")
    id("replacename.android.library.compose")
}

android {
    namespace = "com.dbottillo.replacename.about"
}

dependencies {
    implementation(project(":feature_about:about_data"))
}
