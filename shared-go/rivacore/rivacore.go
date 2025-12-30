package rivacore

const version = "1.0.0"

// Version returns SDK version.
func Version() string {
    return version
}

// Initialize sets up the SDK. Call once on app start.
func Initialize(configPath string) error {
    // setup logic
    return nil
}