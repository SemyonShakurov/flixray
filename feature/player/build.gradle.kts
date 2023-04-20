plugins {
    id("jintly.android.feature")
    id("jintly.android.library.compose")
}

android {
    namespace = "ru.jintly.feature.player"
}

dependencies {
    implementation(libs.exoPlayer)
    implementation(libs.exoPlayer.hls)
    implementation(libs.exoPlayer.ui)
}
