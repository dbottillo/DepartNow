plugins {
    id("replacename.android.library")
    id("replacename.android.library.compose")
}

dependencies {
    api(project(":core:core_data"))
}
android {
    namespace = "com.dbottillo.replacename.core"
}
