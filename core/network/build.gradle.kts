plugins {
    id("jintly.android.library")
    id("jintly.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "ru.jintly.core.network"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.coil)
    implementation(libs.coil.svg)
}
