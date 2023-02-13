plugins {
    id("java-library")
    id("com.android.lint")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

/*dependencies {
    core()
    implementation("javax.inject:javax.inject:1")
}*/

task("devTest") {
    dependsOn("test")
}

task("stagingTest") {
    dependsOn("test")
}

task("prodTest") {
    dependsOn("test")
}
