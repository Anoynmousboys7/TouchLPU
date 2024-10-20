plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.myapplicationlpu"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplicationlpu"
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

    packagingOptions{
        exclude("META-INF/NOTICE.md")
        exclude("META-INF/LICENSE.md")
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.intuit.sdp:sdp-android:1.1.1");
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.17");
    implementation("com.intuit.ssp:ssp-android:1.1.1");
    implementation ("com.github.dhaval2404:imagepicker:2.1");
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0");
    implementation("com.squareup.retrofit2:retrofit:2.0.2");
    implementation("com.squareup.retrofit2:converter-gson:2.0.2");
    implementation("com.sun.mail:jakarta.mail:2.0.1");
    implementation ("com.sun.activation:jakarta.activation:2.0.1");
}
