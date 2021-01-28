plugins {
    id("java-library")
    id("com.android.lint")
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