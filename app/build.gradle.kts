plugins {
    alias(gallery.plugins.android.application)
    alias(gallery.plugins.kotlin)
    alias(gallery.plugins.ksp)
}

android {
    namespace = "com.shady.mygalleryapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.shady.mygalleryapp"
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
        sourceCompatibility(gallery.versions.java.get())
        targetCompatibility(gallery.versions.java.get())
    }
    kotlinOptions {
        jvmTarget = gallery.versions.java.get()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = gallery.versions.androidx.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/**"
        }
    }
}

dependencies {

    implementation(gallery.bundles.kotlin)
    implementation(gallery.bundles.androidx)
    implementation(gallery.bundles.androidx.compose)
    implementation(gallery.bundles.glide)
    implementation(gallery.okhttp)
    ksp(gallery.glide.ksp)
    debugImplementation(gallery.bundles.androidx.compose.debug)
    testImplementation(gallery.junit)
    androidTestImplementation(gallery.bundles.androidx.test)
    androidTestImplementation(gallery.androidx.compose.ui.test.junit)
}