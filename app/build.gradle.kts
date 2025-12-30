import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.riva.gonative"
    compileSdk {
        version = release(36)
    }

    ndkVersion = "29.0.14206865"

    defaultConfig {
        applicationId = "com.riva.gonative"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

val goProjectDir = "${rootProject.projectDir}/shared-go"
val goPath = System.getenv("GOPATH") ?: "${System.getenv("HOME")}/go"
val goBin = "$goPath/bin"
val gomobile = "$goBin/gomobile"
val goPackageName = "rivacore"
file("${projectDir}/libs").mkdirs()

fun createGoBuildTask(name: String, isRelease: Boolean) = tasks.register<Exec>(name) {
    inputs.dir("$goProjectDir/$goPackageName")
    outputs.file("${projectDir}/libs/$goPackageName.aar")

    workingDir(goProjectDir)

    environment("ANDROID_HOME", android.sdkDirectory.absolutePath)
    environment("ANDROID_NDK_HOME", android.ndkDirectory.absolutePath)
    environment("PATH", "${System.getenv("PATH")}:$goBin")

    val args = mutableListOf(
        gomobile, "bind",
        "-target=android/arm64,android/arm",
        "-javapkg", "com.riva.core",
        "-androidapi=${android.defaultConfig.minSdk}",
        "-o", "${projectDir}/libs/$goPackageName.aar",
    )

    if (isRelease) {
        args.add(3, "-ldflags=-s -w")
    }

    args.add("./$goPackageName")
    commandLine(args)
}

val buildGoDebug = createGoBuildTask("buildGoLibraryDebug", false)
val buildGoRelease = createGoBuildTask("buildGoLibraryRelease", true)

tasks.named("preBuild") {
    dependsOn(
        if (gradle.startParameter.taskNames.any { it.contains("Release", ignoreCase = true) })
            buildGoRelease
        else
            buildGoDebug
    )
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(fileTree(mapOf(
        "dir" to "libs",
        "include" to listOf("*.aar")
    )))
}