plugins {
    alias(gallery.plugins.android.application)
    alias(gallery.plugins.kotlin)
    alias(gallery.plugins.kotlin.ksp)
    alias(gallery.plugins.hilt)
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
            isMinifyEnabled = true
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
        buildConfig = true
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
    implementation(gallery.bundles.androidx.core)
    implementation(gallery.bundles.androidx.annotation)
    implementation(gallery.bundles.androidx.collection)
    implementation(gallery.bundles.androidx.concurrent)
    implementation(gallery.bundles.androidx.navigation)
    implementation(gallery.bundles.androidx.compose)
    implementation(gallery.bundles.androidx.activity)
    implementation(gallery.bundles.androidx.lifecycle)
    implementation(gallery.bundles.androidx.window)
    implementation(gallery.bundles.hilt)
    implementation(gallery.bundles.coil)
    implementation(gallery.bundles.androidx.work)
    implementation(gallery.androidx.startup)
    ksp(gallery.bundles.hilt.compiler)
    debugImplementation(gallery.bundles.androidx.compose.tooling)
    implementation(gallery.bundles.androidx.arch.core)
}