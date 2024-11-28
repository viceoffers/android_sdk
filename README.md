
# ViceOffers Android SDK

The ViceOffers Android SDK allows you to track installs and custom events in your Android applications. It's designed to be simple, stable, and easy to integrate.

## Table of Contents

- [Installation](#installation)
  - [Gradle](#gradle)
  - [Manual Installation](#manual-installation)
- [Usage](#usage)
  - [Initialization](#initialization)
  - [Track Install](#track-install)
  - [Track Custom Events](#track-custom-events)
- [Example](#example)
- [API Reference](#api-reference)
- [License](#license)

## Installation

### Gradle

**Step 1:** Add JitPack to your root `build.gradle` at the end of repositories:

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2:** Add the dependency to your app `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.viceoffers:android_sdk:1.0.0'
}
```

> **Note:** Replace the Maven coordinates after publishing your SDK.

### Manual Installation

1. Copy the `android-sdk` directory into your project's root.
2. In your project's `settings.gradle`, include the module:

```gradle
include ':android-sdk'
```

3. In your app's `build.gradle`, add the dependency:

```gradle
dependencies {
    implementation project(':android-sdk')
}
```

## Usage

### Initialization

Initialize the SDK in your `Application` class or `MainActivity`:

```kotlin
import com.viceoffers.tracking.ViceTracking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViceTracking.initialize(this, "YOUR_API_KEY")
        // ... your code ...
    }
}
```

> Replace `"YOUR_API_KEY"` with the API key provided by ViceOffers.

### Track Install

To track an app install, call `trackInstall()` after initialization:

```kotlin
ViceTracking.trackInstall()
```

### Track Custom Events

To track a custom event with optional parameters:

```kotlin
ViceTracking.trackEvent("event_name", mapOf("key1" to "value1", "key2" to 123))
```

**Example:**

```kotlin
ViceTracking.trackEvent("purchase", mapOf("item_id" to "SKU12345", "amount" to 9.99))
```

## Example

```kotlin
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.viceoffers.tracking.ViceTracking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViceTracking.initialize(this, "YOUR_API_KEY")

        // Track install
        ViceTracking.trackInstall()

        // Track a custom event
        ViceTracking.trackEvent("level_up", mapOf("level" to 5))
    }
}
```

## API Reference

### ViceTracking.initialize(context: Context, apiKey: String)
Initializes the SDK with your API key.

- **`context`**: Application context.
- **`apiKey`**: Your unique API key provided by ViceOffers.

### ViceTracking.trackInstall()
Tracks an app install event.

### ViceTracking.trackEvent(eventName: String, parameters: Map<String, Any>? = null)
Tracks a custom event.

- **`eventName`**: Name of the event (e.g., `"purchase"`, `"level_up"`).
- **`parameters`**: Optional map of parameters related to the event.

## License

This SDK is released under the MIT License. See [LICENSE](LICENSE) for details.

---

## Full Code Example Inside `android-sdk/example` Folder

The `example` folder contains the complete code demonstrating how to use the ViceOffers Android SDK.

### Directory Structure

```plaintext
android-sdk/
  ├── src/
  ├── example/
  ├── build.gradle
  ├── README.md
```

For questions or support, contact us at [support@viceoffers.com](mailto:support@viceoffers.com).
