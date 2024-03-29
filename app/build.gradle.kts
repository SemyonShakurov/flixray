plugins {
    id("jintly.android.application")
    id("jintly.android.application.compose")
    id("jintly.android.hilt")
}

android {

    defaultConfig {
        applicationId = "com.jintly.app"
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    namespace = "com.jintly.app"
}

dependencies {
    implementation(project(":feature:public-sessions"))
    implementation(project(":feature:player"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:private-sessions"))
    implementation(project(":feature:profile"))

    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":core:design-system"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.coil)
}
