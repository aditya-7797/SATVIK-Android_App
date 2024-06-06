plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.satvik"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.satvik"
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
    buildFeatures {
        viewBinding = true
    }
}

// Project-level build.gradle




// App-level build.gradle



dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("com.android.support:support-annotations:28.0.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation ("com.google.firebase:firebase-auth:18.0.0")
    implementation ("com.google.android.gms:play-services-gcm:17.0.0")
    implementation("com.google.firebase:firebase-firestore:24.11.0")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.google.firebase:firebase-auth:21.0.1")
    implementation ("com.google.firebase:firebase-firestore:23.0.3")
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore:23.0.3")

    implementation ("com.google.firebase:firebase-storage:20.3.0")
    implementation ("com.google.firebase:firebase-firestore:24.11.1")
    implementation ("com.google.firebase:firebase-storage:20.3.0")
    implementation ("com.google.firebase:firebase-auth:21.0.1")
    implementation ("com.google.firebase:firebase-database:20.3.1")
    implementation ("com.google.firebase:firebase-analytics:21.6.2")
    implementation ("com.google.firebase:firebase-core:20.0.0")
    implementation ("com.google.firebase:firebase-functions:20.4.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.google.firebase:firebase-storage:20.0.0")
    implementation ("com.google.firebase:firebase-firestore:23.0.3")
    implementation ("com.google.firebase:firebase-firestore:24.11.1")
    implementation ("androidx.appcompat:appcompat:1.2.0")
    // Firestore
    implementation ("com.google.firebase:firebase-firestore:24.3.0")
// RecyclerView
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
// Picasso (for image loading)
    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("androidx.recyclerview:recyclerview:1.2.1")

    // Firestore
    implementation ("com.google.firebase:firebase-firestore:24.0.2")
    implementation ("com.google.firebase:firebase-storage:20.0.1")

    // Glide for image loading
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")







}