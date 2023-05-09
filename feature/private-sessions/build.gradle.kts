plugins {
    id("jintly.android.feature")
    id("jintly.android.library.compose")
}

android {
    namespace = "ru.jintly.feature.privatesessions"
}

dependencies {
    implementation(project(":core:design-system"))
}
