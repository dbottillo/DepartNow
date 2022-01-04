task("devTest") {
    dependsOn("testReleaseUnitTest")
}

task("stagingTest") {
    dependsOn("testReleaseUnitTest")
}

task("prodTest") {
    dependsOn("testReleaseUnitTest")
}
