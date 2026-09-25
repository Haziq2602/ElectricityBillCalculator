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

## 📊 Considered Tariff Rates & Slab Breakdown

The rates implemented in this application are derived from the official tariff schedules approved by the **Maharashtra Electricity Regulatory Commission (MERC)** for **LT-I Residential** consumers.

### 1. Standard Residential Tariff Structure (by Consumption Slabs)

| Provider | Consumption Slab | Base Energy Charge (₹/unit) | Wheeling Charge (₹/unit) | Fixed Charge (₹/month) |
|---|---|---|---|---|
| **Adani Electricity** | `0 – 100 units` | ₹3.15 | ₹2.60 | ₹90.00 |
| | `101 – 300 units` | ₹5.40 | ₹2.60 | ₹135.00 |
| | `301 – 500 units` | ₹7.10 | ₹2.60 | ₹135.00 |
| | `Above 500 units` | ₹8.15 | ₹2.60 | ₹160.00 |
| **Tata Power** | `0 – 100 units` | ₹1.90 | ₹2.44 | ₹90.00 |
| | `101 – 300 units` | ₹4.70 | ₹2.44 | ₹135.00 |
| | `301 – 500 units` | ₹9.24 | ₹2.44 | ₹160.00 |
| | `Above 500 units` | ₹10.24 | ₹2.44 | ₹160.00 |
| **BEST Undertaking** | `0 – 100 units` | ₹2.02 | ₹1.68 | ₹90.00 |
| | `101 – 300 units` | ₹5.35 | ₹1.68 | ₹135.00 |
| | `301 – 500 units` | ₹10.04 | ₹1.68 | ₹135.00 |
| | `Above 500 units` | ₹11.25 | ₹1.68 | ₹160.00 |
| **MSEDCL (Mahavitaran)**| `0 – 100 units` | ₹4.32 | ₹1.38 | ₹90.00 |
| | `101 – 300 units` | ₹9.40 | ₹1.38 | ₹135.00 |
| | `301 – 500 units` | ₹12.51 | ₹1.38 | ₹135.00 |
| | `Above 500 units` | ₹13.97 | ₹1.38 | ₹160.00 |

---

### 2. Below Poverty Line (BPL) Subsidized Tariffs (< 35 Units/Month)

For low-consumption households consuming **less than 35 units per month**, the application automatically applies subsidized BPL rates:

| Provider | Base Energy Charge (₹/unit) | Wheeling Charge (₹/unit) | Fixed Charge (₹/month) |
|---|---|---|---|
| **Adani Electricity** | ₹2.20 | ₹2.60 | ₹12.00 |
| **Tata Power** | ₹1.56 | ₹2.44 | ₹90.00 |
| **BEST Undertaking** | ₹1.60 | ₹1.68 | ₹10.00 |
| **MSEDCL (Mahavitaran)** | ₹1.48 | ₹1.38 | ₹35.00 |

---

## 🔗 Official Sources & Regulatory Citations

All tariff data, wheeling charges, and slab structures are based on public regulatory filings approved by the state electricity regulator:

1. **Regulatory Authority:**
   - **MERC (Maharashtra Electricity Regulatory Commission):** [https://merc.gov.in](https://merc.gov.in) — *Multi-Year Tariff (MYT) Orders & Mid-Term Review Orders for Mumbai DISCOMs*.
2. **Distribution Utilities (DISCOMs):**
   - **Adani Electricity Mumbai Limited (AEML):** [Adani Electricity Official Tariff Schedule](https://www.adanielectricity.com)
   - **Tata Power (Mumbai Distribution):** [Tata Power Regulatory & Tariff Orders](https://www.tatapower.com)
   - **BEST Undertaking:** [BEST Electric Supply Tariff Details](https://www.bestundertaking.net)
   - **MSEDCL (Maharashtra State Electricity Distribution Co. Ltd.):** [Mahavitaran Official Tariff Portal](https://www.mahadiscom.in)

---

## 🏛️ Supported Electricity Providers & Coverage

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