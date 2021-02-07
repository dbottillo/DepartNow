plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
    jcenter()
}

dependencies {

    val kotlin =  "1.4.20"
    val androidGradlePlugin =  "4.1.2"
    val dagger =  "2.28-alpha"

    implementation("com.android.tools.build:gradle:$androidGradlePlugin")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("com.google.dagger:hilt-android-gradle-plugin:$dagger")

    /* Depend on the default Gradle API's since we want to build a custom plugin */
    implementation(gradleApi())
    implementation(localGroovy())
}
