@file:Suppress("DSL_SCOPE_VIOLATION")

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
}

task("installGitHook", Copy::class) {
    from(File(rootProject.rootDir, "tools/pre-commit"))
    into { File(rootProject.rootDir, ".git/hooks") }
    fileMode = 0b111101101
}
