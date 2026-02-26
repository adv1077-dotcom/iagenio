plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dev.flutter.flutter-gradle-plugin")
}

import java.util.Properties
import java.io.FileInputStream

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("key.properties")
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "com.example.iscacchi"
    compileSdk = flutter.compileSdkVersion
    ndkVersion = flutter.ndkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    signingConfigs {
        create("release") {
            val envStoreFile = System.getenv("CM_KEYSTORE_PATH")
            val envStorePassword = System.getenv("CM_KEYSTORE_PASSWORD")
            val envKeyAlias = System.getenv("CM_KEY_ALIAS")
            val envKeyPassword = System.getenv("CM_KEY_PASSWORD")

            keyAlias = envKeyAlias ?: (keystoreProperties["keyAlias"] as? String ?: "my-key-alias")
            keyPassword = envKeyPassword ?: (keystoreProperties["keyPassword"] as? String ?: "")
            storePassword = envStorePassword ?: (keystoreProperties["storePassword"] as? String ?: "")
            val storePath = envStoreFile ?: (keystoreProperties["storeFile"] as? String ?: "/tmp/keystore/android_release_keystore.jks")
            storeFile = file(storePath)
        }
    }

    defaultConfig {
        applicationId = "com.example.iscacchi"
        minSdk = flutter.minSdkVersion
        targetSdk = flutter.targetSdkVersion
        versionCode = flutter.versionCode
        versionName = flutter.versionName
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

flutter {
    source = "../.."
}
