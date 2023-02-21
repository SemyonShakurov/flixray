plugins {
    id("jintly.android.library")
    id("jintly.android.hilt")
}

android {
    namespace = "com.jintly.library.core.data"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}
