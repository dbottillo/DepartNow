plugins {
    id("replacename.android.feature")
    id("replacename.android.library.compose")
    id("replacename.android.hilt")
}

android {
    namespace = "com.dbottillo.replacename.departures"
}

dependencies {
    implementation(project(":feature_departures:departures_data"))
}
