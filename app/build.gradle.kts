import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.gradle.internal.impldep.bsh.commands.dir
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date


plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.bitaqaty.reseller"
    compileSdk = 34
    signingConfigs {
//        debug {
//            storeFile file('/Applications/NewGooo/bitaqaty-business-android/app/src/main/java/com/bitaqaty/reseller/utilities/OneCard.dat')
//            storePassword 'OneCard$160314'
//            keyAlias 'onecard'
//            keyPassword 'OneCard$160314'
//        }
        create("release") {
            storeFile = file(
                "/home/mahmoudel-barody/AndroidStudioProjects/sure/app/src/main/java/com/bitaqaty/reseller/utilities/OneCard.dat"
            )
            storePassword = "OneCard$160314"
            keyAlias = "onecard"
            keyPassword = "OneCard$160314"
        }
    }

    defaultConfig {
        applicationId = "com.bitaqaty.reseller"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        javaCompileOptions {
//            annotationProcessorOptions {
//                arguments += [
//                    "room.schemaLocation"  : "$projectDir/schemas".toString(),
//                "room.incremental"     : "true",
//                "room.expandProjection": "true"]
//            }
//        }
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
            signingConfig = signingConfigs["release"]
        }
    }

   // android.buildFeatures.dataBinding = true

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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }


    bundle {
        language {
            // Specifies that the app bundle should not support
            // configuration APKs for language resources. These
            // resources are instead packaged with each base and
            // dynamic feature APK.
            enableSplit = false
        }
    }
    flavorDimensions+=  "default"

    productFlavors {
        create("Production") {
            setSetting()
            android.defaultConfig.versionCode
        }
        create("sureStaging") {
            //   versionName "4.1.4.11"
            setSetting()
            android.defaultConfig.versionCode
        }

        create("sureProduction") {
            //  versionName "4.1.4.10"
            setSetting()
            android.defaultConfig.versionCode
        }

        create("Staging") {
            //    versionName "4.1.4.9"
            setSetting()
            isDefault = true
            applicationId = "com.bitaqaty.resellerStaging"
            android.defaultConfig.versionCode
        }
    }

    val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

    val date = Date()

    applicationVariants.all { variant ->

        variant.outputs.all {
            val flavor = variant.name
            val versionName = variant.versionName
            val versionCode = variant.versionCode
            (this as BaseVariantOutputImpl).outputFileName =
                "BitaqatyApp_${flavor} versionCode_${versionCode}_ version_${versionName}_ date - ${
                    dateFormat.format(date)
                }.apk"
        }
        true
    }
}

dependencies {
    implementation(files("libs/bluetoothsdk.jar"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    implementation(files("libs/fraudforce-lib-release-5.1.0.aar"))
    implementation("io.nearpay:nearpay-sdk:2.1.87")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.appcompat:appcompat:1.6.1")
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)

    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")
    // compose navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    // multidex
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0-alpha01")
    implementation("androidx.fragment:fragment-compose:1.7.0-alpha10")

    // hilt
    implementation("com.google.dagger:hilt-android:2.48.1")

    kapt("com.google.dagger:hilt-android-compiler:2.48.1")
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // pagination
    implementation("androidx.paging:paging-compose:3.2.1")

//    // timber log
//    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("io.coil-kt:coil-compose:1.3.1")
    //   implementation ("com.google.accompanist:accompanist-bottomsheet:0.21.0")

    // coil with animation

    implementation("com.github.skydoves:landscapist-coil:2.2.11")
    implementation("com.github.skydoves:landscapist-glide:2.2.11") // fresco or coil

    implementation("com.github.skydoves:landscapist-placeholder:2.2.11")
    implementation("com.github.skydoves:landscapist-animation:2.2.11")
    implementation("com.github.skydoves:landscapist-transformation:2.2.11")

    implementation("com.google.code.gson:gson:2.10.1")
    // splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")


    implementation("com.github.SmartToolFactory:Compose-AnimatedList:0.6.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //coil
    implementation("io.coil-kt:coil-compose:2.2.2")
    //bottom_sheet
    implementation("androidx.compose.material:material:1.6.8")
    //sunmi
    implementation("com.sunmi:printerlibrary:1.0.18")
}

fun ApplicationProductFlavor.setSetting() {
    var BASE_URL = ""
    var DOMAIN = ""
    var IS_PARTNER = false
    var IS_SURE = false
    var IS_CASH = false
    var IS_STAGE = false
    if (name.contains("Production")) {
        BASE_URL = "https://api.bitaqatybusiness.com/bitaqaty-business/"
        DOMAIN = "bitaqatybusiness.com"
        IS_STAGE = false
        // DOMAIN = "198.18.207.22:443"

    } else if (name.contains("Staging")) {
        BASE_URL = "https://api.bitaqatybusiness-v2.ocstaging.net/bitaqaty-business/"
        // BASE_URL = "https://86.51.176.10:443/bitaqaty-business/"
        DOMAIN = "bitaqatybusiness-v2.ocstaging.net"
        IS_STAGE = true
    }


    IS_SURE = name.contains("sure")

    IS_CASH = name.contains("cashIn")

    IS_PARTNER = IS_CASH || IS_SURE

    buildConfigField("String", "BASE_URL", "\"${BASE_URL}\"")

    //variant.resValue("String", "DOMAIN", "\"${DOMAIN}\"")

    buildConfigField("String", "DOMAIN", "\"${DOMAIN}\"")

    buildConfigField("Boolean", "IS_PARTNER", "${IS_PARTNER}")

    buildConfigField("Boolean", "IS_SURE", "${IS_SURE}")

    buildConfigField("Boolean", "IS_CASH", "${IS_CASH}")


    buildConfigField("Boolean", "IS_STAGE", "${IS_STAGE}")
}