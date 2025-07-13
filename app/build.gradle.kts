import com.google.api.ClientProto.apiVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.symbol.processing)
}

android {
    namespace = "sco.carlukesoftware.material3expressive"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "sco.carlukesoftware.material3expressive"
        minSdk = libs.versions.android.minSdk.get().toInt()
        //noinspection EditedTargetSdkVersion
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.app.versionCode.get().toInt()
        versionName = libs.versions.app.versionName.get()

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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget("21")
            freeCompilerArgs = listOf(
                "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",
                "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api"
            )
        }
    }

    buildFeatures {
        compose = true

        // Disable unused AGP features
        viewBinding = false
        dataBinding = false
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/notice.txt"
            excludes += "META-INF/ASL2.0"
        }
    }

    sourceSets["main"].java {
        srcDirs(
            "src/main/kotlin",
            "src/common/kotlin",
            "src/debug/kotlin",
            "src/release/kotlin",
            "src/staging/kotlin",
            "src/preproduction/kotlin"
        )
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.bundles.kotlinx.serialization)
    implementation(libs.bundles.androidx.navigation3)
    implementation(libs.bundles.androidx.material3)

    implementation(libs.kotlinx.datetime)

    // Koin
    implementation(libs.bundles.koin)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
