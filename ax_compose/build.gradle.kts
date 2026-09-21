plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
    id("maven-publish")
}

android {
    namespace = "com.android.axion.compose"
    compileSdk = 37

    defaultConfig {
        minSdk = 36
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")

            kotlin.directories.addAll(
                listOf(
                    "src/com/android/axion/compose/theme",
                    "src/com/android/axion/compose/scaffold",
                    "src/com/android/axion/compose/navigation",
                    "src/com/android/axion/compose/sheet",
                    "src/com/android/axion/compose/color",
                )
            )
            res.directories.add("res")
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2026.09.00"))

    implementation("androidx.compose.material3:material3:1.5.0-alpha23")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.ui:ui")

    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    implementation("androidx.core:core-ktx:1.18.0")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "org.axionos"
            artifactId = "ax-compose"
            version = "0.0.1"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
