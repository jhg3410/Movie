@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.jik.core.designsystem"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    // modules
    implementation(projects.coreUi)

    implementation(libs.androidx.ktx)
    implementation(libs.androidx.navigation)

    // compose
    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.preview)

    // material
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons)

    // coroutines
    implementation(libs.coroutines)

    // coil
    implementation(libs.compose.coil)

    // test
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)

    // android test
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso)
}