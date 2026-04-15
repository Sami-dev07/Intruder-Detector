# Intruder Detector (Android)

Android app project that captures **intruder selfies** (via a foreground camera service) and can record **location** when device unlock/password events occur (uses Device Admin events). The project also includes Firebase (Analytics/Crashlytics), ads, and in-app billing.

## Modules

- `app`: Main Android application (`applicationId`: `com.intruderselfie.geolocation.hiddenpic.thirdeye`)
- `library`: Embedded library module (`com.anjlab.android.iab.v3`) used for in-app billing

## Requirements

- **Android Studio** (recommended) or command-line Gradle
- **JDK 17** (the app is configured for Java 17)
- Android SDK installed for:
  - `compileSdk`: 33
  - `minSdk`: 24
  - `targetSdk`: 33

## Setup

1. Open the repo in Android Studio.
2. Let Gradle sync finish.
3. (If you’re using Firebase) add `google-services.json`:
   - Place it at `app/google-services.json`

## Build and run

### Android Studio

- Select the `app` run configuration
- Choose a device/emulator
- Run


## App capabilities

- **Device Admin events**: listens for password success/failure events via a `DeviceAdminReceiver`
- **Foreground camera service**: captures images in the background (foreground service type: `camera`)
- **Location**: uses Google Play Services Location and other location helpers
- **Ads**: Google Mobile Ads + Facebook mediation (debug build uses test AdMob IDs)
- **Billing**: Google Play Billing v6

## Permissions used

The app requests permissions including (not exhaustive):

- Camera: `android.permission.CAMERA`
- Location: `android.permission.ACCESS_FINE_LOCATION`, `android.permission.ACCESS_COARSE_LOCATION`
- Storage (legacy): `READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE`
- Foreground service: `android.permission.FOREGROUND_SERVICE`
- Notifications: `android.permission.POST_NOTIFICATIONS`
- Billing: `com.android.vending.BILLING`

