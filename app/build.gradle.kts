plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.androidKotlin)
    alias(libs.plugins.serialization)
    alias(libs.plugins.androidHilt)
    kotlin("kapt")
}
android {
    namespace = "com.ddanddan.ddanddan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ddanddan.ddanddan"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompilerVersion.get()
    }

    buildFeatures {
        compose = true
    }

    hilt {
        enableAggregatingTask = false
    }
}

dependencies {
    implementation(libs.materialDesign)
    implementation(libs.play.services.wearable)
    implementation(libs.androidx.material3.android)
    androidTestImplementation(libs.jUnit)
    androidTestImplementation(libs.espresso)

    // Compose UI dependencies
    implementation(libs.bundles.composeUI)

    // Wear Compose dependencies
    implementation(libs.bundles.wearCompose)

    // Health Services
    implementation(libs.androidx.health.services)

    // Used to bridge between Futures and coroutines
    implementation(libs.guava)
    implementation(libs.concurrent.futures)

    // WorkManager dependencies
    implementation(libs.androidx.work)
    implementation(libs.androidx.work.ktx)

    // Permissions
    implementation(libs.accompanist.permissions)

    implementation(libs.bundles.androidx)
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.androidxJetpack)

    // Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.timber)
}

fun DependencyHandlerScope.kapt(provider: Provider<*>) {
    "kapt"(provider)
}
