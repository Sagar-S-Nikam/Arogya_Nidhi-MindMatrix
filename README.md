<<<<<<< HEAD
# Arogya-Nidhi · Android App

A premium healthcare eligibility checker that helps rural families discover which government health schemes they qualify for. Built natively in **Kotlin** with **MVVM architecture**.

---

## 📱 Features

- **Splash screen** with logo pop animation
- **3-slide onboarding** with ViewPager2 + dots indicator
- **Signup & Login** via Firebase Authentication + "Continue as Guest"
- **Home dashboard** with greeting, stats, quick actions, and bottom nav
- **9-question eligibility quiz** with stepper UI and decision-tree logic (Kotlin if-else)
- **Result screen** showing matched schemes, "why you qualify" reasons, and save/download
- **Document checklist** per scheme with tickable boxes (persisted in Room)
- **Schemes browser** with search + category filter (RecyclerView)
- **Saved Results** history (Room Database)
- **Profile** with edit, dark mode toggle, logout
- **Dark mode** support
- **Material Design 3** with cyan/turquoise gradient theme

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Kotlin |
| UI | XML layouts + Material Design 3 |
| Architecture | MVVM (Repository → ViewModel → Activity/Fragment) |
| Local DB | Room |
| Auth | Firebase Authentication |
| Session | SharedPreferences |
| Navigation | Activities + Fragments + BottomNav |
| Animations | XML anim resources + Lottie (placeholder) |
| Concurrency | Kotlin Coroutines |
| Image loading | Glide |

---

## 📁 Project Structure

```
app/src/main/
├── java/com/arogyanidhi/app/
│   ├── activities/          (8 activities)
│   ├── fragments/           (4 main fragments: Home/Schemes/Saved/Profile)
│   ├── adapters/            (6 RecyclerView adapters)
│   ├── models/              (data classes)
│   ├── db/                  (Room: entities, DAOs, database)
│   ├── repository/          (ArogyaRepository, AuthRepository)
│   ├── viewmodels/          (4 ViewModels)
│   ├── utils/               (PrefsManager, Validator, EligibilityEngine, SchemeRepository, QuestionRepository)
│   └── ArogyaApp.kt         (Application class)
└── res/
    ├── layout/              (23 XML layouts)
    ├── drawable/            (48 drawables — gradients, icons, backgrounds)
    ├── values/, values-night/  (themes, colors, strings)
    ├── menu/                (bottom nav menu)
    ├── anim/                (fade, slide, pop animations)
    ├── color/               (bottom nav color selector)
    └── mipmap-anydpi-v26/   (adaptive launcher icon)
```

---

## 🚀 Setup Instructions

### Step 1: Open in Android Studio

1. Download / unzip the project folder
2. Open **Android Studio** (Hedgehog 2023.1.1 or newer recommended)
3. **File → Open** → select the `ArogyaNidhi` folder
4. Wait for Gradle sync to finish (5–10 minutes first time)

### Step 2: Set up Firebase (5 minutes)

The project ships with a **placeholder `google-services.json`**. You MUST replace it for Firebase Auth to work:

1. Go to https://console.firebase.google.com
2. Click **Add project** → name it `Arogya-Nidhi` (or anything)
3. Once created, in the project overview click the **Android icon** to add an Android app
4. Enter package name **exactly**: `com.arogyanidhi.app`
5. Skip the SHA-1 (only needed for Google Sign-In, not Email/Password)
6. Click **Register app** → **Download `google-services.json`**
7. Replace the placeholder file at `app/google-services.json` with the downloaded one
8. In Firebase console → **Authentication** → **Get Started** → enable **Email/Password** provider

### Step 3: Run on emulator or device

1. Open **Device Manager** in Android Studio → create a virtual device (Pixel 6, API 33+) OR connect a physical Android phone via USB with USB debugging enabled
2. Click the green ▶ **Run** button (top toolbar)
3. The app builds and installs — first launch shows splash → onboarding → login

**Tip:** if you don't want to set up Firebase right now, just use **"Continue as Guest"** on the login screen — it works fully offline.

---

## 📦 Building the APK

### Debug APK (for testing on your own devices)

```bash
./gradlew assembleDebug
```

APK location: `app/build/outputs/apk/debug/app-debug.apk`

You can install this on any Android phone:
- Copy the APK to phone via USB / Drive / WhatsApp
- Open it on the phone → "Install" (you may need to enable "Install from unknown sources" in Settings)

### Release APK (for Play Store)

**Step A: Generate a signing keystore** (do this ONCE — never lose this file!)

In Android Studio: **Build → Generate Signed Bundle / APK → APK → Create new...**

Fill in:
- Keystore path: `~/arogya-keystore.jks` (somewhere safe!)
- Password: (your strong password)
- Alias: `arogya`
- Validity: **25 years** (Play Store requirement)
- First/Last name, Org, City, etc.

**⚠️ Back up the keystore file AND password somewhere safe (password manager, email to yourself, USB drive). If you lose it, you can never update the app on Play Store again.**

**Step B: Build the signed release**

Same dialog → choose the keystore → **release** build variant → uncheck V1, keep V2 signature → **Finish**.

Output: `app/release/app-release.apk`

**OR** for Play Store, build an Android App Bundle (smaller, recommended):

```bash
./gradlew bundleRelease
```

Output: `app/build/outputs/bundle/release/app-release.aab`

---

## 🎮 Publishing to Google Play Store

### One-time setup ($25, ~30 minutes)

1. **Create a developer account** at https://play.google.com/console
   - One-time **$25 USD** registration fee
   - Need a Google account + credit/debit card + government ID for verification

2. **Verify your identity** — Google will ask for proof of identity (passport, Aadhaar, etc.). Takes 1-3 days.

3. **Set up a payment profile** (only needed if you charge for the app)

### Publishing your app (~1-2 hours for first submission)

1. **Play Console → Create app**
   - App name: `Arogya-Nidhi`
   - Default language: English
   - Free / Paid: Free
   - Declarations: tick all the boxes

2. **App content** (left sidebar) — fill all these:
   - **Privacy Policy URL** (required — see below for how to get one free)
   - **App access** — say "All functionality is available without special access"
   - **Ads** — declare whether app contains ads (you don't)
   - **Content rating** — fill the questionnaire (mostly "No"s)
   - **Target audience** — age 18+
   - **Data safety** — declare you collect: name, email, phone (via Firebase Auth)

3. **Store listing** (left sidebar):
   - **Short description** (80 chars): "Find government health schemes you qualify for in 2 minutes"
   - **Full description** (4000 chars max): describe features, target audience (rural families), schemes covered
   - **Screenshots**: 2-8 phone screenshots (1080×1920 recommended). Take from emulator using Logcat → take screenshot
   - **App icon**: 512×512 PNG (export from `mipmap-anydpi-v26/ic_launcher.xml` using Android Studio: right-click → Convert to PNG)
   - **Feature graphic**: 1024×500 PNG (use Canva — search "Play Store feature graphic")
   - **Category**: Medical
   - **Email + phone**: yours

4. **Release → Production → Create new release**
   - Upload the `.aab` file from `app/build/outputs/bundle/release/`
   - Release name: "1.0.0"
   - Release notes: "Initial release of Arogya-Nidhi"
   - **Review release → Start rollout to Production**

5. **Wait for review** — first submission takes **1-7 days**. You'll get an email when approved.

### Free Privacy Policy (required!)

The fastest way: https://app-privacy-policy-generator.firebaseapp.com or https://www.termsfeed.com/privacy-policy-generator/

Generate a basic policy, host it on a free service like:
- **GitHub Pages** (create a public repo with one HTML file)
- **Firebase Hosting** (free tier)
- **Notion** (public page)

Paste the URL in the Play Console Privacy Policy field.

---

## 🧪 Test the app right now (without Firebase)

The app works offline-first with "Continue as Guest":

1. Build & install the debug APK
2. Splash → Onboarding (tap Next 3 times) → Login screen
3. **Tap "Continue as Guest"** (skips Firebase entirely)
4. Take the eligibility quiz → see your scheme matches → save → check Profile, Schemes, Saved tabs

The quiz logic, Room DB, all the schemes & documents work without Firebase. Firebase is only needed for the real signup/login.

---

## 🎨 Customization Tips

- **Change theme color**: edit `res/values/colors.xml` (`cyan_primary`, `cyan_mid`, `cyan_deep`)
- **Add more schemes**: append to `utils/SchemeRepository.kt` and update `utils/EligibilityEngine.kt` with the new rule
- **Add more questions**: append to `utils/QuestionRepository.kt` and update `models/Question.kt` (`QuizAnswers` data class)
- **Custom font**: download Inter font files from https://fonts.google.com/specimen/Inter, drop `inter_regular.ttf`, `inter_medium.ttf`, `inter_semibold.ttf`, `inter_bold.ttf` into `res/font/`, then add `<item name="android:fontFamily">@font/inter</item>` back in `res/values/themes.xml`

---

## 📝 Known Limitations & TODOs

The shipped code is production-ready for the core flow but a few things are intentionally simplified:

- **Phone-OTP login** is not wired (requires extra Firebase Phone Auth setup). Email/password works fully. The login screen will show a Toast if you enter a phone number.
- **Document upload** shows a "coming soon" toast — to enable, integrate Firebase Storage or local file picker
- **PDF Download** of result summary shows a toast — to enable, use iText or PdfDocument API
- **Lottie animations** — placeholder icons used. To add real Lottie files, drop JSON into `res/raw/` and reference via `LottieAnimationView` in the layouts
- **Profile editing** — placeholder; the data layer is in place, just needs an Edit Profile activity

---

## 🆘 Troubleshooting

**Gradle sync fails:**
- Make sure you have **JDK 17** installed (Android Studio → Settings → Build Tools → Gradle → Gradle JDK)
- File → Invalidate Caches & Restart

**Firebase build error: `File google-services.json is missing`:**
- Make sure you replaced the placeholder file with the real one from your Firebase project

**Room compiler error:**
- Run **Build → Clean Project** then **Build → Rebuild Project**

**App crashes on launch:**
- Check Logcat for the stack trace
- 90% of cases: invalid `google-services.json`. Re-download from Firebase.

---

## 📞 Support

Questions or stuck? Open an issue or contact the developer.

**Built with ❤️ for rural healthcare access in India.**
=======
# Arogya_Nidhi-MindMatrix
Arogya Nidhi is an Android app that helps users find eligible government health schemes based on income, occupation, and BPL status. It provides scheme details, required document checklists, and nearby empaneled hospitals through a simple and user-friendly interface.
>>>>>>> 5048af4b68d5e3c9d4f2781eb5b09e26ed82e5cf
