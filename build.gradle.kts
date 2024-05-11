plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("androidx.room") version "2.6.1" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false

    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0" apply false

    kotlin("kapt") version "1.9.0"
    //id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}