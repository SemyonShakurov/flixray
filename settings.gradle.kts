pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "jintly"
include(":app")

include(":core:data")
include(":core:network")
include(":core:design-system")

include(":feature:player")
include(":feature:public-sessions")
include(":feature:auth")
include(":feature:private-sessions")
include(":feature:profile")
