import org.gradle.kotlin.dsl.coreLibraryDesugaring
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    //serlization plugin
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0"
    //hilt
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
    id("com.google.dagger.hilt.android")
}
    val secretProps = Properties()
    val secretFile = File(rootDir, "secret.properties")

    if (secretFile.exists() && secretFile.isFile) {
        secretFile.inputStream().use { stream ->
            secretProps.load(stream)
        }
    } else {
        println("⚠️ secret.properties file not found!")
    }

    val apiKey: String = secretProps.getProperty("API_KEY") ?: ""
android {
    namespace = "com.example.news"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.news"
        minSdk = 31
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig=true
        resValues=true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //    Material 2:
    implementation("androidx.compose.material:material:1.9.2")
//    Material 3:
    implementation("androidx.compose.material3:material3:1.4.0")
    //    Extended icon
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

//    Viewmodel
    implementation("androidx.activity:activity-compose:1.7.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

//For Retrofit:
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // gson converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //for image
    implementation("io.coil-kt:coil-compose:2.6.0")

    //livedata
    implementation("androidx.compose.runtime:runtime-livedata:1.9.2")

    //nevigation
    implementation("androidx.navigation:navigation-compose:2.8.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    //hilt
    implementation("com.google.dagger:hilt-android:2.57.1")
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")
    // Hilt with Jetpack Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    // ViewModel + Compose (optional but common)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    //Desugaring
    //for date,time api
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.1.5")

    //lottie animation
    implementation("com.airbnb.android:lottie-compose:6.4.0")

    //status bar
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    //news app
    implementation("com.github.KwabenBerko:News-API-Java:1.0.2")

}