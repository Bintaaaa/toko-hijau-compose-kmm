plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "product"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.libraries.core)
            implementation(libs.kotlin.coroutine)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.bijan.apis.product"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
