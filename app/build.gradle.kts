plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "eu.tutorials.myattendanceapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "eu.tutorials.myattendanceapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
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

dependencies {

    implementation("com.google.zxing:core:3.5.1")
    implementation("androidx.compose.ui:ui:1.0.0")
    implementation("androidx.compose.material:material:1.0.0")
    implementation("androidx.compose.ui:ui-tooling:1.0.0")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    //GPS locator - to get address from provide latitude and longitude
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.0")


    implementation("com.google.android.gms:play-services-location:21.3.0")
    //implementation("com.google.accompanist:accompanist-permissions:0.20.0")


    val navversion = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$navversion")


    // Activity Compose
    implementation("androidx.activity:activity-compose:1.9.1")

    // CameraX
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.camera:camera-view:1.3.4")

    // Zxing
    implementation("com.google.zxing:core:3.5.1")
    implementation("androidx.core:core-ktx:1.13.1")



    /****************/
    val accompanistVersion = "0.30.0"
    implementation("com.google.accompanist:accompanist-permissions:$accompanistVersion")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")


    val roomversion = "2.6.1"

    implementation("androidx.room:room-runtime:$roomversion")
    annotationProcessor("androidx.room:room-compiler:$roomversion")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomversion")

    implementation("com.opencsv:opencsv:5.5.2")

}