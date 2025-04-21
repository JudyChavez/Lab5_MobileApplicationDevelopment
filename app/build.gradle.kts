plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    id("org.jetbrains.kotlin.plugin.compose")
}
/*
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    id("org.jetbrains.kotlin.plugin.compose")
}*/



android {
    namespace = "com.example.notesapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.notesapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        namespace = "com.example.notesapp"
    }

    dependencies {
        // Import the Compose BOM
        implementation(platform("androidx.compose:compose-bom:2024.12.01"))
        implementation("androidx.activity:activity-compose:1.9.3")
        implementation("androidx.compose.material3:material3")
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-tooling")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
        implementation("androidx.navigation:navigation-compose:2.8.5")

        implementation("androidx.compose.material:material-icons-extended") //ExpandMore/ExpandeLess icons.

        //Room
        implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
        ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
        implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")

        // Testing
        androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
        androidTestImplementation("androidx.test.ext:junit:1.2.1")
        testImplementation(kotlin("test"))
    }
}

/*
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)


    //Room
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")


    //testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

 */

