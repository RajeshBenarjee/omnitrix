# ⌚ Omnitrix Wear OS App
### A Ben 10–inspired smartwatch interface with a radial alien selector, sci-fi animations, and functional utility shortcuts.

---

## 🧬 Introduction

**Omnitrix Wear OS** is a lightweight **Ben 10 inspired smartwatch interface** built using **Jetpack Compose for Wear OS**.  
The app recreates the **Omnitrix experience** with animated energy rings, a rotating alien selector, and haptic feedback while also functioning as a **practical smartwatch utility hub**.

Designed specifically for **circular Wear OS devices**, the interface uses **Canvas drawing, vector graphics, and polar coordinate math** to maintain sharp visuals across different resolutions while keeping the entire app **under 1MB**.

Key concepts used in this project include:

- **State Machine Navigation**
- **Radial UI Design**
- **Canvas Animations**
- **Gesture-based Controls**
- **Haptic Feedback Integration**
- **Wear OS System Utilities**

---

## 🎥 Visual Diagram / Demo

### Omni Interface Outputs

| Output | Preview |
|------|------|
| `omni0` | ![](Output/omni0.png)<br>Idle Mode – Digital clock with animated energy rings |
| `omni1` | ![](Output/omni1.png)<br>Radial Alien Selector with gesture control |
| `omni2` | ![](Output/omni2.png)<br>Transformation animation and alien utility activation |

---

## 📱 User Instructions

### 1️⃣ Launching the App
1. Open the **Omnitrix App** on your Wear OS watch.
2. The app starts in **IDLE mode**, displaying:
   - Digital clock
   - Rotating energy rings
   - Pulsing Omnitrix glow.

---

### 2️⃣ Opening the Alien Selector
1. Tap the **center Omnitrix dial**.
2. The **Radial Alien Selector** appears with **8 alien icons** arranged in a circular layout.

---

### 3️⃣ Selecting an Alien
1. **Swipe or drag** around the dial.
2. The dial rotates using **gesture detection**.
3. Each alien selection triggers a **short haptic tick vibration**.

The active alien always aligns to the **12 o’clock position**.

---

### 4️⃣ Transforming
1. Tap the **center transform button**.
2. A **white flash animation** plays.
3. A **strong vibration pulse** confirms the transformation.

---

### 5️⃣ Alien Utility Actions

| Alien | Function |
|------|------|
| 🔥 Heatblast | Activates flashlight (max brightness) |
| ⚡ XLR8 | Opens timer/alarm |
| 👻 Ghostfreak | Toggles Do Not Disturb |
| ⚙️ Upgrade | Opens watch settings |
| 💎 Diamondhead | Simulates heart rate scan |

---

### 6️⃣ Background Safety

The app remembers your state using **rememberSaveable**, meaning:

- Returning from notifications keeps your **current alien**
- Active timers remain intact
- App resumes without resetting the interface

---

## ⚠️ Known Issues / Future Plans

### Known Issues
- Some watches may require **manual permission for vibration or DND access**
- Heart rate simulation currently uses a **visual animation only**
- Very small watches (<380px) may slightly compress UI elements

---

### Future Improvements

Planned updates include:

- 🔋 **Energy depletion timer UI**
- 👽 **More aliens with additional utilities**
- 🎵 **Transformation sound effects**
- 📊 **Real health sensor integration**
- 🌌 **Improved particle-based Omnitrix animations**
- 🎮 **Customization for alien wheel size and sensitivity**

---

## 🛠 Tech Stack

- **Kotlin**
- **Jetpack Compose for Wear OS**
- **Canvas Drawing API**
- **Gesture Detection**
- **Wear OS Scaffold**
- **Haptic Feedback API**

---

## 📦 Build Highlights

- ⚡ App Size: **<1MB**
- 🎨 Vector-based graphics
- 🔄 Polar coordinate radial UI
- ⌚ Optimized for **390px–454px circular displays**
- 🔋 OLED battery friendly theme

---
