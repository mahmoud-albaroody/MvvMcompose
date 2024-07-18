plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.bitaqaty.reseller"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bitaqaty.reseller"
        minSdk = 23
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes+="META-INF/gradle/incremental.annotation.processors"
        }
    }



}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")
    implementation ("com.google.android.material:material:1.5.0")
    implementation("androidx.compose.material:material-icons-extended")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)

    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")
    // compose navigation
    implementation ("androidx.navigation:navigation-compose:2.7.6")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")
    // multidex
    implementation ("androidx.multidex:multidex:2.0.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0-alpha01")
    implementation ("androidx.fragment:fragment-compose:1.7.0-alpha10")

    // hilt
    implementation ("com.google.dagger:hilt-android:2.48.1")

    kapt ("com.google.dagger:hilt-android-compiler:2.48.1")
    // retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // pagination
    implementation ("androidx.paging:paging-compose:3.2.1")

    // timber log
    implementation ("com.jakewharton.timber:timber:5.0.1")
    implementation ("io.coil-kt:coil-compose:1.3.1")
 //   implementation ("com.google.accompanist:accompanist-bottomsheet:0.21.0")

    // coil with animation

    implementation ("com.github.skydoves:landscapist-coil:2.2.11")
    implementation ("com.github.skydoves:landscapist-glide:2.2.11") // fresco or coil

    implementation ("com.github.skydoves:landscapist-placeholder:2.2.11")
    implementation ("com.github.skydoves:landscapist-animation:2.2.11")
    implementation ("com.github.skydoves:landscapist-transformation:2.2.11")

    implementation ("com.google.code.gson:gson:2.10.1")
    // splash screen
    implementation ("androidx.core:core-splashscreen:1.0.1")


    implementation ("com.github.SmartToolFactory:Compose-AnimatedList:0.6.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //coil
    implementation ("io.coil-kt:coil-compose:2.2.2")
    //bottom_sheet
    implementation("androidx.compose.material:material:1.6.8")
}