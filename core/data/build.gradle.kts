plugins {
    id("jintly.android.library")
    id("jintly.android.hilt")
}

android {
    namespace = "ru.jintly.core.data"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.datastore)
}
