plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    jcenter()
}

kotlinDslPluginOptions.experimentalWarning.set(false)

dependencies {

    val kotlin =  "1.5.10"
    val androidGradlePlugin =  "4.2.1"
    val dagger =  "2.36"

    implementation("com.android.tools.build:gradle:$androidGradlePlugin")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("com.google.dagger:hilt-android-gradle-plugin:$dagger")

    /* Depend on the default Gradle API's since we want to build a custom plugin */
    implementation(gradleApi())
    implementation(localGroovy())
}
