# Axion SDK

Shared framework and UI libraries used by AxionOS.

The repository is primarily built through Soong as part of the Android
platform build. Selected modules also provide standalone Gradle build support
for application development outside of a full AOSP source tree.

## Gradle support

Currently supported:

- `ax_compose`

The Gradle build does **not** replace the existing `Android.bp` files.
It provides an additional development path for standalone Android projects.

### Requirements
Current standalone `ax_compose` setup:
- Android SDK / `compileSdk`: 37
- Application `minSdk`: 36
- Application `targetSdk`: 36
- Material 3: `1.5.0-alpha23`
- Compose BOM: `2026.09.00`
- Gradle wrapper included in this repository

`compileSdk = 37` is required by the current Material 3 / Compose dependency stack. Applications can still target Android 16 with `targetSdk = 36`.

## Building ax_compose

```sh
./gradlew :ax_compose:assembleRelease
```

## Publish `ax_compose` to Maven Local
for local application development

```sh
./gradlew :ax_compose:publishToMavenLocal
```

This publishes `org.axionos:ax-compose:0.0.1` to the local Maven repository 
 under `~/.m2/repository/org/axionos/ax-compose/`

*A consuming gradle project must include `mavenLocal()`:*

```kts
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}
```

*The library can then be consumed normally*
```kts
dependencies {
    implementation("org.axionos:ax-compose:0.0.1")
}
```

## Included Compose APIs
The current standalone Gradle build includes the platform-independent UI
parts of `ax_compose`:
- theme
- scaffold
- navigation
- sheet
- color

Other Axion SDK APIs are intentionally excluded for now where they depend on platform-only or framework-internal APIs.

## Example Usage
```kt
setContent {
    AxionTheme {
        AxionScaffold(
            title = "Hello World!",
            onBackClick = { finish() },
        ) { contentPadding ->
            // App content
        }
    }
}
```

Stock Material 3 / Material 3 Expressive components can also be used inside AxionTheme.

## Soong and Gradle
The two build paths have different purposes:

**Standalone development**

```text
    Gradle
    → fast local builds
    → ./gradlew installDebug
```

**AxionOS platform build**

```text
    Soong / Android.bp
    → system integration
    → platform signing / privileged integration where required
```

An Axion application can therefore keep both Gradle files for local development and
Android.bp for integration into AxionAOSP.

## Axion Gradle plugin
The separate `axion-gradle` project provides the `org.axionos.application` convention plugin
for standalone Axion application development.

It configures the common Android, Compose, Material 3 Expressive, and `ax-compose` setup so individual applications do not have to duplicate it.
