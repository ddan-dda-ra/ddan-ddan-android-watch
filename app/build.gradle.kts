import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

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
        versionCode = 2
        versionName = "1.0"

        buildConfigField(
            "String",
            "BASE_URL",
            gradleLocalProperties(rootDir).getProperty("base.url"),
        )
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("ddanddan_debug.keystore")
            storePassword = gradleLocalProperties(rootDir).getProperty("storePassword")
            keyAlias = gradleLocalProperties(rootDir).getProperty("keyAlias")
            keyPassword = gradleLocalProperties(rootDir).getProperty("keyPassword")
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

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompilerVersion.get()
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    hilt {
        enableAggregatingTask = false
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
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
