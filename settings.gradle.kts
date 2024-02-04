rootProject.name = "TokoHijauComposeKMM"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":composeApp")
include(":apis:product")
include(":features:home")
include(":libraries:core")
include(":features:productDetail")
include(":libraries:libraries:components")
include(":libraries:components")
include(":features:favorite")
include(":apis:authentication")
include(":features:authentication")
include(":features:cart")
