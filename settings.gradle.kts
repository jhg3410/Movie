enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://chaquo.com/maven-test") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Movie"
include(":app")
include(":core:ui")
include(":core:designsystem")
include(":core:model")
include(":core:network")
include(":core:data")
include(":feature:popular")
include(":feature:detail")
include(":feature:home")
include(":lib:videoplayer")
