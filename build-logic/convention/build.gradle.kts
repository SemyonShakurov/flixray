plugins {
    `kotlin-dsl`
}

group = "com.jintly.app.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "jintly.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}
