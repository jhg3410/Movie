@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    id(libs.plugins.hilt.plugin.get().pluginId)
}

android {
    namespace = "com.jik.movie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jik.movie"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
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
        }
    }
}

dependencies {

    // modules
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation(projects.core.data)
    implementation(projects.feature.home)
    implementation(projects.feature.popular)
    implementation(projects.feature.detail)

    implementation(libs.androidx.ktx)
    implementation(libs.androidx.runtime.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.navigation)

    // compose
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.tooling)
    implementation(libs.compose.preview)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // splash
    implementation(libs.splashscreen)

    // test
    testImplementation(libs.junit)

    // android test
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.compose.test.junit)

    // debug
    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.compose.test.manifest)
}