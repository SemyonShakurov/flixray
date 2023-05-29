plugins {
    id("jintly.android.feature")
    id("jintly.android.library.compose")
}

android {
    namespace = "ru.jintly.feature.privatesessions"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:network"))

    implementation(libs.datastore)
    implementation(libs.retrofit.core)
}
