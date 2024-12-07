plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.lb7"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lb7"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    // Если используете Version Catalog
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.github.bumptech.glide:glide:4.11.0")

    implementation ("com.squareup.okhttp3:okhttp:4.10.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    // Если нужно добавить дополнительные зависимости вручную
    implementation("com.squareup.picasso:picasso:2.71828") // Для загрузки постеров
    implementation("com.google.code.gson:gson:2.8.9") // Для работы с JSON
    implementation("androidx.recyclerview:recyclerview:1.3.0") // Для списка фильмов
    implementation("androidx.appcompat:appcompat:1.6.1") // Базовая библиотека UI
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // ConstraintLayout
}
