

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.moviesapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.moviesapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String","API_BASE_URL","\"https://api.themoviedb.org/3/movie\"")
        }
        release {
            buildConfigField("String","API_BASE_URL","\"https://api.themoviedb.org/3/movie\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    ndkVersion = "26.2.11394342"
    externalNativeBuild {
        ndkBuild {
            path ("src/main/jni/Android.mk")  //path of Android.mk file
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}
// Allow references to generated code
kapt{
    correctErrorTypes = true
}
dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.4")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    //for  testing
    testImplementation("junit:junit:4.13.2")
   // testImplementation("org.mockito:mockito-core:3.12.4")
    // AndroidX Test - Core library
    testImplementation("androidx.test:core:1.5.0")

    // AndroidX Test - LiveData testing
    testImplementation ("androidx.arch.core:core-testing:2.2.0")

    // AndroidX Test - JUnit rules
   // testImplementation ("androidx.test.ext:junit:1.1.5")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation ("org.mockito:mockito-core:3.12.4")
    testImplementation("org.robolectric:robolectric:4.11.1")
//    // For Robolectric tests.
//    testImplementation("com.google.dagger:hilt-android-testing:2.44")
//    // ...with Kotlin.
//    kaptTest("com.google.dagger:hilt-android-compiler:2.44")
//    // For instrumented tests.
//    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
//    // ...with Kotlin.
//    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")
//
    testImplementation("org.powermock:powermock-module-junit4:2.0.9")
    testImplementation("org.powermock:powermock-api-mockito2:2.0.9")

    ///
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("com.github.Ajitshaw002:NetworkManager:1.1")
    implementation("com.google.code.gson:gson:2.10.1")

    val nav_version = "2.7.6"

    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    val lifecycle_version = "2.7.0"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")

    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

}