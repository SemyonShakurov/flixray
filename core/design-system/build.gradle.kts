plugins {
    id("jintly.android.library")
    id("jintly.android.library.compose")
}

android {
    namespace = "ru.jintly.core.designsystem"
}

dependencies {
    implementation(libs.systemUiController)
}
