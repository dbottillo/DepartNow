plugins {
    id("java-library")
    id("com.android.lint")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

dependencies{
    core()
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