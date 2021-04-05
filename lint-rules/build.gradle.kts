plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    compileOnly("com.android.tools.lint:lint-api:${Versions.lint}")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
    testImplementation("com.android.tools.lint:lint:${Versions.lint}")
    testImplementation("com.android.tools.lint:lint-tests:${Versions.lint}")
    testImplementation("com.google.truth:truth:${Versions.truth}")
}

tasks.withType<Jar> {
    manifest {
        attributes["Lint-Registry-v2"] = "com.dbottillo.lintrules.IssueRegistry"
    }
}
