import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("jintly.android.feature")
    id("jintly.android.library.compose")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "ru.jintly.feature.player"
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering {
                    option("lite")
                }
                val kotlin by registering {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:network"))

    implementation(libs.exoPlayer)
    implementation(libs.exoPlayer.hls)
    implementation(libs.exoPlayer.ui)
    implementation(libs.protobuf.kotlin.lite)
    implementation(libs.datastore)
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
}
