plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.facebook.react")
}

react {
    // This app module lives at "<root>/app" (no separate "android/" folder wrapping it, since
    // RN was embedded into the pre-existing native project), so root is one level up, not two.
    root = file("../")
    autolinkLibrariesWithApp()
}

android {
    namespace = "com.ciandt.championships"
    compileSdk = 37
    buildToolsVersion = "37.0.0"
    ndkVersion = "27.1.12297006"

    defaultConfig {
        applicationId = "com.ciandt.championships"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        resValues = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.androidx.ui.tooling)

    // React Native (lab-01-solution: brownfield embedding, New Architecture)
    implementation("com.facebook.react:react-android")
    implementation("com.facebook.react:hermes-android")
}
