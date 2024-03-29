plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.anshu.chatapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.anshu.chatapp"
        minSdk = 24
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

    buildFeatures{
        viewBinding =true
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.emoji2:emoji2:1.4.0")
    implementation("com.google.firebase:firebase-messaging:23.4.1")
//    implementation("com.google.firebase:firebase-auth:22.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.hbb20:ccp:2.6.0")

    implementation ("com.jsibbold:zoomage:1.3.1")
    implementation ("com.github.ybq:Android-SpinKit:1.4.0")

    implementation ("com.github.3llomi:CircularStatusView:V1.0.3")

    //noinspection GradleDependency
    implementation ("androidx.camera:camera-core:1.0.0-alpha03")
    //noinspection GradleDependency
    implementation ("androidx.camera:camera-camera2:1.0.0-alpha03")

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.airbnb.android:lottie:6.0.0")

    //// For Emogy

//    //// For Image Picker Crop
//    implementation ("com.github.dhaval2404:imagepicker:2.1")

    ///// firebase
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))
    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")
    // Also add the dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:21.0.0")
// Add the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-storage:20.3.0")

    /////



    //// picaso
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation ("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

}