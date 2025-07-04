src/main/java/
└── regenenhancer/
    ├── RegenEnhancerMod.java
    ├── client/
    │   ├── ClientModelRegistry.java
    │   ├── gui/
    │   │   └── GuiRegenEnhancer.java
    │   └── proxy/
    │       └── ClientProxy.java
    ├── common/
    │   ├── events/
    │   │   ├── CauldronEventHandler.java
    │   │   ├── PlayerRegenHandler.java
    │   │   └── SleepEventHandler.java
    │   ├── gui/
    │   │   └── GuiHandler.java
    │   ├── items/
    │   │   ├── ItemBandage.java
    │   │   └── ItemHerbalSoup.java
    │   ├── potion/
    │   │   ├── ModPotions.java
    │   │   └── PotionRegenBoost.java
    │   ├── recipe/
    │   │   ├── RecipeCraftBandage.java
    │   │   ├── RecipeHerbalSoup.java
    │   │   └── RecipeRefillBandage.java
    │   ├── registry/
    │   │   ├── ModItemRegistry.java
    │   │   ├── ModPotionRegistry.java
    │   │   └── ModRecipeRegistry.java
    │   ├── util/
    │   │   └── SleepTracker.java
    ├── network/
    │   └── PacketBrewParticles.java
    └── server/
        └── proxy/
            └── CommonProxy.java
# Regen Enhancer

Regen Enhancer is a Minecraft Forge mod that reimagines early-game health regeneration by introducing a custom brewing system using cauldrons, fire, and food. Designed for survival players who enjoy immersive mechanics and meaningful progression, this mod adds strategic depth to healing without disrupting the vanilla experience.

---

## 🔧 Features

- 🧪 **Custom Regeneration Potions**  
  Brew regeneration potions using cauldrons placed over fire and non-meat food items.

- ⏳ **Time-Based Brewing**  
  Potions take 3 seconds (60 ticks) to brew, with visual particle effects and sound cues when complete.

- 🧙 **Swirling Particle Effects**  
  Magical swirl effects appear when potions are collected, enhancing feedback and immersion.

- 💧 **Vanilla Compatibility**  
  Water bottle collection from cauldrons is preserved—custom behavior only activates when a brew is ready.

- 🔮 **Future-Ready Visuals**  
  Brewing readiness is exposed for future tinting or GUI indicators.

---

## 📦 Installation

1. Install [Minecraft Forge](https://files.minecraftforge.net/) for version 1.12.2.
2. Download the latest release of Regen Enhancer.
3. Place the `.jar` file into your `mods` folder.
4. Launch Minecraft and enjoy!

---

## 🧪 Brewing Instructions

1. Place a **cauldron** over a **fire** block.
2. Fill the cauldron with **water**.
3. Right-click the cauldron with a **non-meat food item** (e.g., bread, apple).
4. Wait 3 seconds. You'll see green swirl particles during brewing.
5. When brewing completes, a sound plays and a ring of magic particles appears.
6. Right-click the cauldron with a **glass bottle** to collect your custom regeneration potion.

---

## ⚙️ Mod Compatibility

- Compatible with most vanilla and Forge-based mods.
- Designed to be lightweight and non-intrusive.
- Does not override or replace vanilla brewing or potion mechanics.

---

## 📚 Credits

- Developed by: IAmFmGod
- Programming & Design: Custom Forge event handling, particle systems, and potion logic
- Special thanks to the Minecraft modding community for documentation and inspiration

---

## 📜 License

This mod is provided as-is for personal and public use. Redistribution is permitted with credit. Commercial use is not allowed without permission.

---

## 💬 Feedback & Support

Found a bug or have a feature request?  
Open an issue or contact the developer directly.

---

Enjoy a more immersive and rewarding healing experience with Regen Enhancer!