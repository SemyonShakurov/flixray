plugins {
    id("jintly.android.feature")
    id("jintly.android.library.compose")
}

android {
    namespace = "ru.jintly.feature.auth"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:network"))

    implementation(libs.androidx.compose.icons)
    implementation(libs.retrofit.core)
    implementation(libs.coil.compose)
}
