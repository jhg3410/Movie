import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.jik.core.network"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            type = "String",
            name = "TMDB_API_KEY",
            value = gradleLocalProperties(rootDir).getProperty("TMDB_API_KEY")
        )
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
}

dependencies {

    implementation(projects.coreModel)
    implementation(libs.androidx.ktx)

    // moshi
    implementation(libs.moshi)

    // retrofit
    implementation(libs.retrofit)

    // logging
    implementation(libs.logging.interceptor)

    // hilt
    implementation(libs.hilt.android)

    // test
    testImplementation(libs.junit)

    // android test
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso)
}