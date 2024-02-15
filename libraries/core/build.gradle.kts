plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.realm)
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
            baseName = "core"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            api(libs.kotlin.coroutine)
            api(libs.ktor.client.core)
            api(libs.ktor.client.contentNegotiation)
            api(libs.ktor.client.serialization)
            api(libs.ktor.client.logging)
            api(libs.realm)
            api(libs.realmKotlinSync)
            api(libs.multiplatformSettings)
        }

        androidMain.dependencies {
            api(libs.android.viewModel)
            api(libs.android.viewModel.compose)
            api(libs.ktor.client.okhttp)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.bijan.libraries.core"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
