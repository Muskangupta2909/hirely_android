plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)

    // Hilt
    id("com.google.dagger.hilt.android")

    // KAPT for Hilt
    //id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.hirely"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.hirely"

        minSdk = 24
        targetSdk = 37

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    // -------------------------
    // Compose
    // -------------------------

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.lifecycle.viewmodel.compose)


    // -------------------------
    // DataStore
    // -------------------------

    implementation(libs.androidx.datastore.preferences)


    // -------------------------
    // Splash Screen
    // -------------------------

    implementation(libs.androidx.core.splashscreen)


    // -------------------------
    // Room Database
    // -------------------------

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // Room uses KSP
    ksp("androidx.room:room-compiler:2.8.4")


    // -------------------------
    // Hilt Dependency Injection
    // -------------------------

    implementation("com.google.dagger:hilt-android:2.59.2")
    ksp("com.google.dagger:hilt-android-compiler:2.59.2")

    // Hilt + Jetpack Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")


    // -------------------------
    // Unit Testing
    // -------------------------

    testImplementation(libs.junit)


    // -------------------------
    // Android Testing
    // -------------------------

    androidTestImplementation(
        libs.androidx.compose.ui.test.junit4
    )

    androidTestImplementation(
        libs.androidx.espresso.core
    )

    androidTestImplementation(
        libs.androidx.junit
    )


    // -------------------------
    // Debug
    // -------------------------

    debugImplementation(
        libs.androidx.compose.ui.test.manifest
    )

    debugImplementation(
        libs.androidx.compose.ui.tooling
    )
}