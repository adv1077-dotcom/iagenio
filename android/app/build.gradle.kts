import java.util.Properties
import java.io.FileInputStream

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("key.properties")
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dev.flutter.flutter-gradle-plugin")
}

android {
    namespace = "com.example.iagenio"
    compileSdk = flutter.compileSdkVersion
    ndkVersion = flutter.ndkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    defaultConfig {
        applicationId = "com.example.iagenio"
        minSdk = flutter.minSdkVersion
        targetSdk = flutter.targetSdkVersion
        versionCode = flutter.versionCode
        versionName = flutter.versionName
    }

   signingConfigs {
    create("release") {
        val envStoreFile = System.getenv("CM_KEYSTORE_PATH")
        val envStorePassword = System.getenv("CM_KEYSTORE_PASSWORD")
        val envKeyAlias = System.getenv("CM_KEY_ALIAS")
        val envKeyPassword = System.getenv("CM_KEY_PASSWORD")

        keyAlias = envKeyAlias ?: (keystoreProperties["keyAlias"] as String)
        keyPassword = envKeyPassword ?: (keystoreProperties["keyPassword"] as String)
        storePassword = envStorePassword ?: (keystoreProperties["storePassword"] as String)

        val storePath = envStoreFile ?: (keystoreProperties["storeFile"] as String)
        storeFile = file(storePath)
    }
}

    buildTypes {
    release {
        signingConfig = signingConfigs.getByName("release")
    }
}

        }
    }
}

flutter {
    source = "../.."
}
