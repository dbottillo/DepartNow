plugins {
    id("java-library")
    id("com.android.lint")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

dependencies{
    core()
    implementation("javax.inject:javax.inject:1")
}

task("devTest"){
    dependsOn("test")
}

task("stagingTest"){
    dependsOn("test")
}

task("prodTest"){
    dependsOn("test")
}