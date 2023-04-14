plugins {
    id("replacename.android.feature")
    id("replacename.android.library.compose")
    id("replacename.android.hilt")
}

android {
    namespace = "com.dbottillo.replacename.home"
}

dependencies {
    implementation(project(":feature_home:home_data"))
}
