plugins {
    id("jintly.android.feature")
    id("jintly.android.library.compose")
}

android {
    namespace = "ru.jintly.feature.auth"
}

dependencies {
    implementation(project(":core:design-system"))

    implementation(libs.androidx.compose.icons)
}
