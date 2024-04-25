plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}


android {
    namespace = "com.vigyat.fitnessappprototype"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vigyat.fitnessappprototype"
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
    buildFeatures{
        dataBinding = true
    }


}

dependencies {


    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.work:work-runtime:2.9.0")
    implementation("com.google.android.gms:play-services-fitness:21.1.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation ("com.google.android.gms:play-services-auth:21.1.0")
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-auth")
    implementation("androidx.activity:activity:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    implementation("com.google.android.gms:play-services-location:21.2.0")

    implementation("com.airbnb.android:lottie:6.4.0")

    implementation("androidx.core:core-splashscreen:1.0.1")

        // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.7.0")
        // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata:2.7.0")



}