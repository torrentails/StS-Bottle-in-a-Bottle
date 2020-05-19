package BottleInABottle.relics;

import BottleInABottle.BottleInABottle;
import BottleInABottle.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static BottleInABottle.BottleInABottle.makeRelicOutlinePath;
import static BottleInABottle.BottleInABottle.makeRelicPath;


public class GoldArmour extends CustomRelic {
    public static final String ID = BottleInABottle.makeID(GoldArmour.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(GoldArmour.class.getSimpleName()));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(GoldArmour.class.getSimpleName()));

    public static final float MULTIPLIER = 3.0f;


    public GoldArmour() {
        // TODO: RelicTier.SPECIAL maybe? Give it with an event?
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }


    @Override
    public void onEquip() {
        int maxHP = AbstractDungeon.player.maxHealth;
        AbstractDungeon.player.gainGold((int) ((float)maxHP * MULTIPLIER));
        AbstractDungeon.player.decreaseMaxHealth(maxHP - 1);

        this.description = DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
        this.flash();
    }


    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }


    // @Override
    // public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
    //     return 0;
    // }
}
