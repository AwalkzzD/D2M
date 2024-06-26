plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.d2m"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.d2m"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.5.6")
    val navVersion = "2.7.7"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    api("androidx.navigation:navigation-fragment-ktx:$navVersion")
    api("androidx.navigation:navigation-ui-ktx:$navVersion")
    api("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")
//    Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    GsonConverter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    Picasso
    implementation("com.squareup.picasso:picasso:2.8")
//    ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

// https://mvnrepository.com/artifact/androidx.recyclerview/recyclerview
    runtimeOnly("androidx.recyclerview:recyclerview:1.1.0")

// https://mvnrepository.com/artifact/androidx.recyclerview/recyclerview-selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0-alpha06")

}