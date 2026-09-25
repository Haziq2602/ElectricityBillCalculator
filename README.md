# ⚡ Mumbai Electricity Bill Calculator

> Calculates real Mumbai residential power bills across Adani, Tata Power, BEST, and MSEDCL using official MERC tariffs. Features automatic BPL subsidy detection (<35 units), tiered slab-based energy rates, per-unit wheeling fees, and fixed monthly charges to provide an accurate, transparent billing breakdown ready for Android integration.

---

## 📌 Overview

The **Mumbai Electricity Bill Calculator** is a Java application designed to accurately calculate monthly residential electricity bills based on real-world tariff orders approved by the **Maharashtra Electricity Regulatory Commission (MERC)**. 

Unlike basic calculators that use arbitrary formulas, this utility models real-life multi-tier progressive slabs, distributor-specific infrastructure charges, and state regulatory rules.

---

## ✨ Key Features

- **🏢 Multi-Provider Support:** Seamlessly handles the 4 major electricity distribution companies (DISCOMs) in the Mumbai Metropolitan Region:
  - **Adani Electricity Mumbai Limited (AEML)**
  - **Tata Power (TPC-D)**
  - **BEST Undertaking**
  - **MSEDCL (Mahavitaran)**
- **🎯 Automatic BPL Subsidy Detection:** Automatically detects consumption under 35 units/month and applies subsidized **Below Poverty Line (BPL)** tariff rates.
- **📊 Real Slab & Tariff Breakdown:**
  - **Base Energy Charges:** Progressive tiered rates across consumption slabs (`0–100`, `101–300`, `301–500`, `>500` kWh).
  - **Wheeling Charges:** Dedicated per-unit distribution network delivery charges.
  - **Fixed Monthly Charges:** Mandatory recurring service and infrastructure charges.
- **📱 Android-Ready Architecture:** Designed with modular logic to smoothly transition from a Java CLI prototype into an Android application (`MainActivity.java` + XML Views).

---

## 🏛️ Supported Electricity Providers (Residential LT-I)

| Provider | Coverage Area | Slabs Handled | BPL Rates Supported |
|---|---|---|---|
| **Adani Electricity** | Mumbai Suburbs | 0-100, 101-300, 301-500, >500 | ✅ Yes (< 35 units) |
| **Tata Power** | Mumbai City & Suburbs | 0-100, 101-300, 301-500, >500 | ✅ Yes (< 35 units) |
| **BEST** | Mumbai Island City | 0-100, 101-300, 301-500, >500 | ✅ Yes (< 35 units) |
| **MSEDCL** | Mulund, Bhandup, Thane, Navi Mumbai | 0-100, 101-300, 301-500, >500 | ✅ Yes (< 35 units) |

---

## 🚀 How to Run (CLI Prototype)

### Prerequisites
- **Java Development Kit (JDK 17 or 21+)** installed and configured on your system.

### Compilation & Execution
Navigate to the source directory and run:

```bash
# Navigate to the java source folder
cd app/src/main/java/com/example/electricitybillcalculator

# Compile the Java file
javac ElectricityBillCalculator.java

# Run the program
java ElectricityBillCalculator
```

---

## 💻 Sample Output

```text
Enter electricity units consumed: 36
Enter electricity provider (Adani / Tata / BEST / MSEDCL): Tata
Provider   : Tata
Units      : 36
BPL Status : false
Total Bill : ₹246.24
```

---

## 🗺️ Project Roadmap

- [x] **Stage 1 (Complete):** Core calculation logic and console CLI prototype with MERC tariffs.
- [ ] **Stage 2:** Refactor tariff calculations into an independent `ElectricityCalculator` service class.
- [ ] **Stage 3:** Build modern Android UI with XML layouts (`EditText`, `Spinner`, `Button`, `TextView`, Cards).
- [ ] **Stage 4:** Add interactive bill breakdown dialog and generate installable Android `.apk`.

---

## 🛠️ Tech Stack

- **Language:** Java (JDK 21)
- **Target Platforms:** Java Console (CLI) / Android (Java + Android SDK + XML)
- **Build Tool:** Gradle