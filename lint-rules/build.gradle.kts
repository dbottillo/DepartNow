plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    compileOnly("com.android.tools.lint:lint-api:${libs.versions.lint.get()}")
    testImplementation("com.android.tools.lint:lint:${libs.versions.lint.get()}")
    testImplementation("com.android.tools.lint:lint-tests:${libs.versions.lint.get()}")
    testImplementation("com.google.truth:truth:${libs.versions.truth.get()}")
}

tasks.withType<Jar> {
    manifest {
        attributes["Lint-Registry-v2"] = "com.dbottillo.lintrules.IssueRegistry"
    }
}
