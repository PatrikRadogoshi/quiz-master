# Quiz Master — Android starter project

A lightweight, offline-first Android quiz starter built with Kotlin and native Android UI. It supports Albanian and English, category selection, 10-question rounds, a 15-second timer, scoring, and results.

## Fastest way to get an APK without building on your PC

This repository includes a GitHub Actions workflow (`.github/workflows/build-apk.yml`) that builds the APK on GitHub's cloud runners.

1. Sign in or create a GitHub account at https://github.com.
2. Create a **new private repository** named `quiz-master`.
3. Upload the contents of this ZIP (not the ZIP file itself) to the repository's main branch. Keep the `.github/workflows/build-apk.yml` file and its folders.
4. Open the repository's **Actions** tab and select **Build Quiz Master APK**.
5. Open the completed workflow run and download the **Quiz-Master-APK** artifact. GitHub may ask you to sign in.
6. Extract the artifact ZIP; it contains `app-debug.apk`. Transfer it to your Android phone and install it. If prompted, allow installation from that source.

The workflow runs on GitHub's computers, so Android SDK/Gradle do not need to be installed on your weak PC.

## Local build (optional)

Requires JDK 17 and Android SDK / Android Studio:

```bash
gradle assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`.

## Important scope notes

- This starter is a functional **offline solo quiz**. It does not yet include online rooms, user accounts, cloud leaderboards, or App Store packaging.
- Online multiplayer needs a backend (for example, Supabase) and security/rules; it is not enabled just by this starter.
- iOS needs a macOS/Xcode build and Apple signing. This Android project alone cannot produce an iOS app.
- The debug APK is for testing, not the final signed Play Store release.
