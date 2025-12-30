# GoNative

A demonstration project showing seamless Go integration with Android using gomobile. The Gradle build scripts handle Go compilation automatically—no manual steps required.

## Features

- **Automatic Go Compilation**: Gradle triggers gomobile builds as part of the standard Android build process
- **Debug/Release Builds**: Release builds automatically strip Go binaries for smaller APKs
- **Multi-Architecture**: Targets both `arm64` and `arm` architectures
- **Clean Separation**: Go code lives in `shared-go/`, Android code in `app/`

## Prerequisites

- Android Studio with SDK and NDK 29.0.14206865
- Go 1.21+ with gomobile installed:
  ```bash
  go install golang.org/x/mobile/cmd/gomobile@latest
  gomobile init
  ```

## Project Structure

```
├── app/                    # Android application (Kotlin + Compose)
│   └── libs/               # Generated .aar files (gitignored)
├── shared-go/              # Go module
│   ├── rivacore/           # Exported package (public API)
│   └── internal/           # Private Go code
```

## How It Works

The `app/build.gradle.kts` defines custom Gradle tasks that:

1. Invoke `gomobile bind` before Android compilation
2. Output `rivacore.aar` to `app/libs/`
3. Include the AAR as a dependency automatically

```kotlin
// Simplified flow
preBuild.dependsOn(buildGoLibrary)
// gomobile bind → rivacore.aar → Android imports com.riva.core.rivacore.Rivacore
```

## Usage

```bash
# Build debug APK (compiles Go automatically)
./gradlew assembleDebug

# Build release APK (stripped Go binary)
./gradlew assembleRelease
```

### Adding Go Functions

1. Add exported functions (capitalized) to `shared-go/rivacore/rivacore.go`
2. Rebuild the Android project
3. Access from Kotlin via `com.riva.core.rivacore.Rivacore`

```go
// shared-go/rivacore/rivacore.go
func MyFunction() string {
    return "Hello from Go!"
}
```

```kotlin
// MainActivity.kt
val result = Rivacore.myFunction()
```

## License

MIT
