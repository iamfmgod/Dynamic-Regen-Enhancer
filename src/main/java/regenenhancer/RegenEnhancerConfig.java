package regenenhancer;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

@Config(modid = RegenEnhancerMod.MODID)
public class RegenEnhancerConfig {
    @Name("Bandage Uses")
    @Comment("How many times a bandage can be used before breaking.")
    @RangeInt(min = 1, max = 64)
    public static int bandageUses = 16;

    @Name("Bandage Regeneration Duration (ticks)")
    @Comment("How long the bandage regeneration effect lasts (in ticks). 20 ticks = 1 second.")
    @RangeInt(min = 1, max = 6000)
    public static int bandageRegenDuration = 60;

    @Name("Bandage Regeneration Amplifier")
    @Comment("Amplifier for the bandage regeneration effect. 0 = Regeneration I, 1 = Regeneration II, etc.")
    @RangeInt(min = 0, max = 10)
    public static int bandageRegenAmplifier = 0;

    @Name("Sleep Food Cost Per Heart")
    @Comment("How much food (hunger points) is consumed per heart healed when sleeping. 1 food = 2 hunger points.")
    @RangeInt(min = 0, max = 20)
    public static int sleepFoodCostPerHeart = 2;

    @Name("Enable Full Heal On Sleep")
    @Comment("If true, sleeping fully heals the player. If false, no healing is applied.")
    public static boolean enableFullHealOnSleep = true;

    @Name("Herbal Soup Heal Amount")
    @Comment("How much health (in half-hearts) herbal soup restores. 4 = 2 hearts.")
    @RangeInt(min = 1, max = 40)
    public static int herbalSoupHealAmount = 4;

    @Name("Herbal Soup Max Stack Size")
    @Comment("Maximum stack size for herbal soup.")
    @RangeInt(min = 1, max = 64)
    public static int herbalSoupMaxStack = 8;

    @Name("Potion Regen Boost Amount Per Amp")
    @Comment("How much health (in half-hearts) is restored per tick per amplifier for the custom regen boost potion.")
    @RangeInt(min = 1, max = 20)
    public static int potionRegenBoostAmount = 1;

    @Name("Potion Regen Boost Interval Base")
    @Comment("Base tick interval for the custom regen boost potion effect. Lower = faster.")
    @RangeInt(min = 1, max = 40)
    public static int potionRegenBoostIntervalBase = 10;

    @Name("Cauldron Brew Time (ticks)")
    @Comment("How many ticks it takes to brew a potion in the cauldron. 20 ticks = 1 second.")
    @RangeInt(min = 1, max = 6000)
    public static int cauldronBrewTime = 60;

    @Name("Cauldron Brew Complete Particle Count")
    @Comment("How many particles spawn when a cauldron brew completes.")
    @RangeInt(min = 1, max = 200)
    public static int cauldronBrewParticleCount = 60;

    @Name("Bandage Use Duration (ticks)")
    @Comment("How long you must hold right-click to use a bandage (in ticks). 20 ticks = 1 second.")
    @RangeInt(min = 1, max = 100)
    public static int bandageUseDuration = 20;

    @Name("Bandage Max Stack Size")
    @Comment("Maximum stack size for bandages.")
    @RangeInt(min = 1, max = 64)
    public static int bandageMaxStack = 1;

    @Name("Disable Cauldron Potion Brewing")
    @Comment("If true, disables custom cauldron potion brewing entirely.")
    public static boolean disableCauldronBrewing = false;

    @Name("Enable Self-Damage On Cauldron Brew")
    @Comment("If true, players take damage when brewing a potion in a cauldron.")
    public static boolean cauldronBrewSelfDamage = false;

    @Name("Self-Damage Amount On Cauldron Brew")
    @Comment("Amount of damage (in half-hearts) dealt to the player when brewing a potion in a cauldron, if enabled.")
    @RangeInt(min = 1, max = 40)
    public static int cauldronBrewSelfDamageAmount = 2;

    @Name("Disable Vanilla Natural Regeneration")
    @Comment("If true, vanilla natural regeneration and its hunger drain are completely blocked. Healing only occurs via modded items.")
    public static boolean disableVanillaRegen = true;
}
