# ⚡ Mumbai Electricity Bill Calculator (Android App)

> A modern, minimalist Android application that calculates real Mumbai residential electricity bills across Adani, Tata Power, BEST, and MSEDCL using official MERC tariffs. Features automatic BPL subsidy detection, tiered slab-based rates, wheeling charges, and transparent bill breakdown.

---

## 📌 Overview

The **Mumbai Electricity Bill Calculator** is a native Android application built using **Java** and **Material Design Components**. It models real-world residential electricity tariffs approved by the **Maharashtra Electricity Regulatory Commission (MERC)**.

The app replaces arbitrary billing estimates with actual state regulatory calculations—factoring in multi-tier progressive slabs, distribution provider-specific wheeling fees, and tiered monthly fixed charges.

---

## ✨ Features

- **🏢 Multi-Provider Coverage:** Full residential tariff support for all 4 major Mumbai DISCOMs:
  - **Adani Electricity Mumbai Limited (AEML)**
  - **Tata Power (TPC-D)**
  - **BEST Undertaking**
  - **MSEDCL (Mahavitaran)**
- **🎯 Automated BPL Subsidy Detection:** Automatically detects consumption under 35 units/month and applies subsidized **Below Poverty Line (BPL)** rates with visual status tags.
- **📊 Real Bill Breakdown:**
  - **Base Energy Charges:** Tiered progressive consumption slabs (`0–100`, `101–300`, `301–500`, `>500` kWh).
  - **Wheeling Charges:** Dedicated per-unit grid transmission fees.
  - **Fixed Monthly Charges:** Connection and infrastructure maintenance charges based on consumption brackets.
- **🎨 Sleek & Minimalist UI:**
  - Modern edge-to-edge layout with no intrusive top ActionBar.
  - Material card containers with clean elevation and subtle borders.
  - Instant calculations with animated breakdown display and one-tap reset.

---

## 🏛️ Supported Electricity Providers (Residential LT-I)

| Provider | Operating Region | Slabs Supported | BPL Subsidy (<35 units) |
|---|---|---|---|
| **Adani Electricity** | Mumbai Suburbs | 0-100, 101-300, 301-500, >500 | ✅ Supported |
| **Tata Power** | Mumbai City & Suburbs | 0-100, 101-300, 301-500, >500 | ✅ Supported |
| **BEST** | Mumbai Island City | 0-100, 101-300, 301-500, >500 | ✅ Supported |
| **MSEDCL** | Mulund, Bhandup, Thane, Navi Mumbai | 0-100, 101-300, 301-500, >500 | ✅ Supported |

---

## 🚀 How to Build and Run

### Prerequisites
- **Android Studio** (Koala / Ladybug or newer)
- **JDK 17 or 21**
- Android SDK 35 (minSdk 24)

### Running on Emulator / Physical Device
```powershell
# Build debug APK
.\gradlew assembleDebug

# Install directly to a connected Android device or emulator
.\gradlew installDebug
```

---

## 🛠️ Tech Stack & Architecture

- **Language:** Java
- **Target Platform:** Android (minSdk 24, targetSdk 35)
- **UI Framework:** Android XML + Google Material Components (`MaterialCardView`, `TextInputLayout`, `MaterialButton`)
- **Theme:** Edge-to-edge `Theme.MaterialComponents.DayNight.NoActionBar` with tailored status bar palette
- **Build System:** Gradle (Kotlin DSL)