import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.jik.core.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {

    // modules
    implementation(projects.core.model)

    implementation(libs.androidx.ktx)

    // moshi
    implementation(libs.moshi)
    ksp(libs.moshi.codegen)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)

    // logging
    implementation(libs.logging.interceptor)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // test
    testImplementation(libs.junit)

    // android test
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso)
}