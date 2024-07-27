import org.jetbrains.kotlin.gradle.plugin.kotlinToolingVersion

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
//val mysqlConnectorJavaVersion by extra(mysqlConnectorJavaVersion)

android {
    namespace = "com.example.learnquest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.learnquest"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    //Added this again to sort out an issue between java and kotlin being different versions causing errors
    kotlinOptions{
        jvmTarget = "1.8"
    }

    buildFeatures{
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    //maybe upgrade to latest, this is for database. to import appropriate connector library
    implementation("mysql:mysql-connector-java:5.1.47")

    //vico chart libraries
    implementation("com.patrykandpatrick.vico:compose:2.0.0-alpha.25")

    // For `compose`. Creates a `ChartStyle` based on an M2 Material Theme.
    implementation("com.patrykandpatrick.vico:compose-m2:2.0.0-alpha.25")

    // For `compose`. Creates a `ChartStyle` based on an M3 Material Theme.
    implementation("com.patrykandpatrick.vico:compose-m3:2.0.0-alpha.25")

    // Houses the core logic for charts and other elements. Included in all other modules.
    implementation("com.patrykandpatrick.vico:core:2.0.0-alpha.25")

    // For the view system.
    implementation("com.patrykandpatrick.vico:views:2.0.0-alpha.25")
}