plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt.plugin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.serialization)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.loukatah"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.loukatah"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // ViewModel Kotlin extensions for easier ViewModel usage
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // Compose integration for ViewModel to use ViewModel in composable functions
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Compose integration with Activity for setting up Compose UI in Activities
    implementation(libs.androidx.activity.compose)
    // Coil image loading library for Compose to load and display images efficiently
    implementation(libs.io.coil.kt.compose)

    //Dagger Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Navigation compose
    implementation(libs.androidx.navigation.compose)


    //firebase

    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.analytics.ktx)
    implementation(libs.google.firebase.auth.ktx)
    implementation(libs.google.firebase.firestore.ktx)
    implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database:20.3.")  // Firebase Realtime Database

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.games.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Extended Icons
    implementation(libs.androidx.compose.material.icons.extended)

    implementation("androidx.compose.runtime:runtime-livedata:1.7.8")
// Supa base
    implementation ("io.github.jan-tennert.supabase:storage-kt:1.4.0")
    implementation ("io.github.jan-tennert.supabase:postgrest-kt:1.4.0")
    implementation ("io.ktor:ktor-client-okhttp:2.3.4")

    // لاختيار الصور من المعرض
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("io.coil-kt:coil-compose:2.4.0")
    implementation ("androidx.activity:activity-ktx:1.7.0")
    implementation ("androidx.fragment:fragment-ktx:1.5.7")
    implementation ("com.github.dhaval2404:imagepicker:2.1")
}
