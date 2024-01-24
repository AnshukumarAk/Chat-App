plugins {
    id("com.android.application")
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
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.hbb20:ccp:2.6.0")
//    implementation ("com.github.aabhasr1:OtpView:v1.1.2")
    implementation ("com.github.mukeshsolanki.android-otpview-pinview:otpview:3.2.0")
    implementation ("com.github.GoodieBag:Pinview:v1.5")

    implementation ("com.jsibbold:zoomage:1.3.1")
    implementation ("com.github.joielechong:countrycodepicker:2.4.2")
    implementation ("com.github.ybq:Android-SpinKit:1.4.0")
//    implementation ("com.hanks:passcodeview:0.1.2")
//    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    implementation ("com.github.3llomi:CircularStatusView:V1.0.3")

    //noinspection GradleDependency
    implementation ("androidx.camera:camera-core:1.0.0-alpha03")
    //noinspection GradleDependency
    implementation ("androidx.camera:camera-camera2:1.0.0-alpha03")

    implementation ("com.github.bumptech.glide:glide:4.16.0")


}